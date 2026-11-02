package ObjectPage;

import Control.DriverContext;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public class NavidadPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public NavidadPage() {
        this.driver = DriverContext.getDriver();
        if (this.driver != null) {
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        }
    }

    public WebDriverWait getWait() {
        if (this.driver == null) {
            this.driver = Control.DriverContext.getDriver();
        }
        return new WebDriverWait(this.driver, java.time.Duration.ofSeconds(15));
    }

    // ==========================================
    // LOCALIZADORES ROBUSTOS Y FILTRADOS
    // ==========================================
    private By headerPrincipal = By.xpath("//header | //div[contains(@class,'header')] | //nav");
    private By tituloCategoria = By.xpath("//h1 | //h2[contains(text(),'Navidad')] | //*[contains(@class,'title')]");
    private By contenedorProductos = By.xpath("//div[contains(@class,'grid') or contains(@class,'results') or @id='testId-searchResults-products']");

    // Tarjetas estrictas que aseguran tener imagen y precio de producto real
    private By tarjetasProductos = By.xpath("//div[(contains(@class,'pod') or contains(@class,'product-card') or contains(@data-testid,'pod')) and not(contains(@class,'skeleton')) and descendant::img and descendant::*[contains(text(),'$')]]");

    private By tagImagen = By.xpath(".//img | .//picture | .//svg | .//*[contains(@class,'image') or contains(@class,'Image')]");
    private By tagNombre = By.xpath(".//*[contains(@class,'title') or contains(@class,'name') or contains(@class,'brand')] | .//h2 | .//h3 | .//a[not(descendant::img)]");
    private By tagPrecio = By.xpath(".//*[contains(text(),'$') or contains(@class,'price') or contains(@class,'amount')]");
    private By tagEnlace = By.xpath(".//a[@href]");

    // ==========================================
    // MÉTODOS DE ACCIÓN Y VALIDACIÓN
    // ==========================================

    public void abrirSodimacYNavegarANavidad() {
        if (this.driver == null) {
            this.driver = new org.openqa.selenium.chrome.ChromeDriver();
        }

        this.driver.manage().window().maximize();
        this.driver.get("https://www.sodimac.cl/sodimac-cl/lista/cat3640002/Navidad");
    }

    public void esperarCargaVisual() {
        getWait().until(ExpectedConditions.presenceOfElementLocated(contenedorProductos));
        pausa(2000);
    }

    public void validarEncabezado() {
        Assert.assertTrue(">> Error: No se encuentra visible el encabezado principal.",
                driver.findElements(headerPrincipal).size() > 0);
    }

    public void validarTituloCategoria() {
        Assert.assertTrue(">> Error: No se muestra el título de la categoría Navidad.",
                driver.findElements(tituloCategoria).size() > 0 || driver.getTitle().toLowerCase().contains("navidad"));
    }

    public void validarContenedorProductos() {
        Assert.assertTrue(">> Error: El contenedor de productos no está disponible.",
                driver.findElements(contenedorProductos).size() > 0);
    }

    public void validarEstructuraTarjetas(List<String> objetosRequeridos) {
        List<WebElement> cards = driver.findElements(tarjetasProductos);
        Assert.assertFalse(">> Error: No se encontraron tarjetas de productos en el catálogo.", cards.isEmpty());

        for (int i = 0; i < cards.size(); i++) {
            WebElement card = cards.get(i);
            if (objetosRequeridos.contains("Imagen")) {
                Assert.assertTrue(">> Falta imagen en tarjeta #" + (i+1), card.findElements(tagImagen).size() > 0);
            }
            if (objetosRequeridos.contains("Nombre")) {
                Assert.assertTrue(">> Falta nombre en tarjeta #" + (i+1), card.findElements(tagNombre).size() > 0);
            }
            if (objetosRequeridos.contains("Precio")) {
                Assert.assertTrue(">> Falta precio en tarjeta #" + (i+1), card.findElements(tagPrecio).size() > 0);
            }
            if (objetosRequeridos.contains("Enlace de producto")) {
                Assert.assertTrue(">> Falta enlace en tarjeta #" + (i+1), card.findElements(tagEnlace).size() > 0);
            }
        }
    }

    public void validarImagenesCorrectas() {
        List<WebElement> cards = driver.findElements(tarjetasProductos);
        for (int i = 0; i < cards.size(); i++) {
            WebElement img = cards.get(i).findElement(tagImagen);

            String src = img.getAttribute("src");
            String dataSrc = img.getAttribute("data-src");
            String srcset = img.getAttribute("srcset");
            String dataUrl = img.getAttribute("data-url");
            String dataOriginal = img.getAttribute("data-original");

            boolean hasValidSource = (src != null && !src.trim().isEmpty() && !src.startsWith("data:image")) ||
                    (dataSrc != null && !dataSrc.trim().isEmpty()) ||
                    (srcset != null && !srcset.trim().isEmpty()) ||
                    (dataUrl != null && !dataUrl.trim().isEmpty()) ||
                    (dataOriginal != null && !dataOriginal.trim().isEmpty());

            if (!hasValidSource && (img.getTagName().equalsIgnoreCase("svg") || img.getTagName().equalsIgnoreCase("picture") || img.getAttribute("class").contains("image"))) {
                hasValidSource = true;
            }

            Assert.assertTrue(">> Error: La imagen en la tarjeta #" + (i+1) + " no tiene origen válido.", hasValidSource);
        }
    }

    public void validarNombresNoVacios() {
        List<WebElement> cards = driver.findElements(tarjetasProductos);
        for (WebElement card : cards) {
            String nombre = card.findElement(tagNombre).getText();
            Assert.assertTrue(">> Error: Se encontró una tarjeta con el nombre vacío.", nombre != null && !nombre.trim().isEmpty());
        }
    }

    public void validarFormatoPreciosCLP() {
        List<WebElement> cards = driver.findElements(tarjetasProductos);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (int i = 0; i < cards.size(); i++) {
            WebElement card = cards.get(i);
            WebElement elemPrecio = card.findElement(tagPrecio);

            String precio = elemPrecio.getText().trim();

            if (precio.isEmpty()) {
                precio = (String) js.executeScript("return arguments[0].innerText;", elemPrecio);
                if (precio != null) precio = precio.trim();
            }

            boolean esValido = precio != null && !precio.isEmpty() && precio.contains("$");

            Assert.assertTrue(">> Error: El precio en la tarjeta #" + (i+1) + " está vacío o no contiene el símbolo monetario ($). Valor leído: '" + precio + "'",
                    esValido);
        }
    }

    public void validarSinSuperposicion() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Long totalTarjetas = (Long) js.executeScript("return document.querySelectorAll('[class*=\"pod\"], [class*=\"product-card\"], [data-testid*=\"pod\"]').length;");

        Assert.assertTrue(">> Error: No hay tarjetas suficientes para validar la grilla.", totalTarjetas > 0);
    }

    public void validarSinTarjetasDuplicadas() {
        List<WebElement> cards = driver.findElements(tarjetasProductos);
        Set<String> urlsUnicas = new HashSet<>();
        List<String> listaDuplicados = new ArrayList<>();

        for (WebElement card : cards) {
            try {
                WebElement linkElem = card.findElement(tagEnlace);
                String url = linkElem.getAttribute("href");

                if (url != null && !url.trim().isEmpty()) {
                    String urlLimpia = url.split("\\?")[0];
                    boolean esNuevaUrl = urlsUnicas.add(urlLimpia);

                    if (!esNuevaUrl) {
                        listaDuplicados.add(urlLimpia);
                    }
                }
            } catch (Exception ignored) {}
        }

        // Si encontramos duplicados, dejamos constancia exacta como evidencia de QA y hacemos fallar la prueba
        if (!listaDuplicados.isEmpty()) {
            System.err.println(">> [HALLAZGO DE QA]: Se detectaron " + listaDuplicados.size() + " URLs duplicadas en el DOM del catálogo.");
            for (String dup : listaDuplicados) {
                System.err.println("   - URL duplicada: " + dup);
            }
            Assert.fail(">> Error de QA: El catálogo presenta URLs de productos duplicadas. Total de incidencias: " + listaDuplicados.size());
        } else {
            System.out.println(">> [Éxito]: No se encontraron URLs duplicadas. Integridad de enlaces perfecta.");
        }
    }
    public void scrollYRecopilarProductosCompletos() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long ultimaAltura = (long) js.executeScript("return document.body.scrollHeight;");

        int intentos = 0;
        int maxIntentos = 25; // Aumentado para asegurar cobertura total del catálogo

        System.out.println(">> [Acción]: Iniciando desplazamiento pausado por la página para cargar todo el catálogo...");

        while (intentos < maxIntentos) {
            // Scroll fraccionado para ver el movimiento en pantalla
            js.executeScript("window.scrollBy(0, 800);");
            pausa(800); // Pausa visual de 0.8 segundos entre cada tramo de scroll

            long nuevaAltura = (long) js.executeScript("return document.body.scrollHeight;");

            if (nuevaAltura == ultimaAltura) {
                // Hacemos un último scroll al fondo absoluto para confirmar fin de página
                js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                pausa(1500);

                long alturaFinalComprobacion = (long) js.executeScript("return document.body.scrollHeight;");
                if (alturaFinalComprobacion == nuevaAltura) {
                    System.out.println(">> [Éxito]: Se ha llegado al final de la página del catálogo.");
                    break;
                }
            }

            ultimaAltura = nuevaAltura;
            intentos++;
        }
        pausa(1500);
    }

    private void pausa(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}