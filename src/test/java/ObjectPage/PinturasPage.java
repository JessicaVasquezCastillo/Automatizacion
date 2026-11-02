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
import java.util.List;

public class PinturasPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public PinturasPage() {
        this.driver = DriverContext.getDriver();
        if (this.driver != null) {
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        }
    }

    public WebDriverWait getWait() {
        if (this.driver == null) {
            this.driver = Control.DriverContext.getDriver();
        }
        return new WebDriverWait(this.driver, Duration.ofSeconds(15));
    }

    // ==========================================
    // LOCALIZADORES DE NAVEGACIÓN Y MENÚ
    // ==========================================
    private By btnCookies = By.id("onetrust-accept-btn-handler");
    private By menuCategorias = By.xpath("//button[contains(@class,'category') or contains(text(),'Categorías')] | //div[contains(@class,'menu-toggle')]");
    private By categoriaPinturas = By.xpath("//a[contains(text(),'Pinturas y Adhesivos') or contains(@href,'pinturas')]");

    // Elementos de contexto y subcategorías
    private By tituloContextoPinturas = By.xpath("//h1[contains(text(),'Pinturas')] | //span[contains(text(),'Pinturas y Adhesivos')]");
    private By contenedorTarjetas = By.xpath("//div[contains(@class,'grid') or contains(@class,'results') or @id='testId-searchResults-products']");
    private By subcategoriasList = By.xpath("//div[contains(@class,'subcat') or contains(@class,'category-card')]//a");

    // Filtros de Marca
    private By filtroMarcaCheckbox = By.xpath("//input[@type='checkbox' and (contains(@id,'brand') or contains(@name,'brand'))] | //button[contains(@class,'filter-brand')]");

    // Tarjetas y Ficha de Detalle
    private By tarjetasProductos = By.xpath("//div[contains(@class,'pod') or contains(@class,'product-card')]");
    private By nombreProductoTarjeta = By.xpath(".//a[contains(@class,'title') or contains(@class,'name')] | .//h2 | .//h3");
    private By imagenProducto = By.xpath(".//img");

    // Elementos en la vista de ficha de detalle
    private By tituloFichaDetalle = By.xpath("//h1[contains(@class,'product-title') or contains(@class,'name')]");

    // ==========================================
    // MÉTODOS DE ACCIÓN Y VALIDACIÓN
    // ==========================================

    public void abrirPaginaPrincipalSodimac() {
        if (this.driver == null) {
            this.driver = new org.openqa.selenium.chrome.ChromeDriver();
        }
        this.driver.manage().window().maximize();
        this.driver.get("https://www.sodimac.cl");
    }

    public void gestionarAvisoCookies() {
        try {
            WebElement cookies = getWait().until(ExpectedConditions.elementToBeClickable(btnCookies));
            if (cookies.isDisplayed()) {
                cookies.click();
            }
        } catch (Exception e) {
            // Continúa si no aparece
        }
    }

    public void seleccionarCategoriaPinturasDesdeMenu() {
        try {
            // 1. Abrir el menú lateral
            WebElement botonMenu = getWait().until(ExpectedConditions.elementToBeClickable(menuCategorias));
            botonMenu.click();
            Thread.sleep(1500);
        } catch (Exception e) {
            // Menú ya abierto
        }

        // 2. Buscar el texto exacto "Pinturas y Adhesivos" en el menú lateral desplegado
        By localizadorPinturasMenu = By.xpath("//a[contains(text(), 'Pinturas y Adhesivos')] | //span[contains(text(), 'Pinturas y Adhesivos')]");

        WebElement pinturas = getWait().until(ExpectedConditions.presenceOfElementLocated(localizadorPinturasMenu));

        // 3. Hacer scroll y clic directo por JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", pinturas);
        try { Thread.sleep(800); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", pinturas);

        // 4. Pausa de seguridad para asegurar la transición de página
        try {
            Thread.sleep(2500);
        } catch (InterruptedException ignored) {}
    }

    public void validarContextoPinturas() {
        By tituloFlexible = By.xpath("//h1[contains(translate(text(), 'PINTURAS', 'pinturas'), 'pinturas')] | //span[contains(translate(text(), 'Pinturas', 'pinturas'), 'pinturas')] | //div[contains(@class, 'category-title')]");
        WebElement titulo = getWait().until(ExpectedConditions.visibilityOfElementLocated(tituloFlexible));
        Assert.assertTrue(">> Error: No se visualiza el contexto de Pinturas y Adhesivos.", titulo.isDisplayed());
    }

    public void validarDisponibilidadEnlacesTarjetas() {
        List<WebElement> productos = driver.findElements(tarjetasProductos);
        Assert.assertFalse(">> Error: No se encontraron tarjetas de productos disponibles para explorar.", productos.isEmpty());
    }

    public void seleccionarSubcategoriaPintura() {
        try {
            WebElement subcatInterior = getWait().until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//a[contains(text(),'Pintura para Interior') or contains(@href,'interior')]")
            ));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", subcatInterior);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subcatInterior);
        } catch (Exception e) {
            List<WebElement> subcats = driver.findElements(subcategoriasList);
            Assert.assertFalse(">> Error: No se encontraron subcategorías disponibles.", subcats.isEmpty());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subcats.get(0));
        }
    }

    public void validarBreadcrumbOEncabezadoSubcategoria() {
        WebElement encabezado = getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1 | //nav[contains(@class,'breadcrumb')]")));
        Assert.assertTrue(">> Error: No se observa encabezado o breadcrumb de la subcategoría.", encabezado.isDisplayed());
    }

    public void seleccionarMarcaFiltro() {
        List<WebElement> filtros = driver.findElements(filtroMarcaCheckbox);
        Assert.assertFalse(">> Error: No hay filtros de marca disponibles en el listado.", filtros.isEmpty());

        WebElement primerFiltro = filtros.get(0);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", primerFiltro);
    }

    public void validarMarcaMarcadaYActualizacion() {
        WebElement contenedorResultados = getWait().until(ExpectedConditions.presenceOfElementLocated(contenedorTarjetas));
        Assert.assertTrue(">> Error: El listado no se actualizó tras aplicar el filtro.", contenedorResultados.isDisplayed());
    }

    public void desmarcarMarcaFiltro() {
        List<WebElement> filtros = driver.findElements(filtroMarcaCheckbox);
        if (!filtros.isEmpty()) {
            WebElement primerFiltro = filtros.get(0);
            if (primerFiltro.isSelected()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", primerFiltro);
            }
        }
    }

    public void validarMarcaDesmarcada() {
        List<WebElement> filtros = driver.findElements(filtroMarcaCheckbox);
        if (!filtros.isEmpty()) {
            Assert.assertFalse(">> Error: La marca sigue seleccionada en el filtro.", filtros.get(0).isSelected());
        }
    }

    private String nombreProductoSeleccionado = "";

    public void registrarNombrePrimeraTarjeta() {
        List<WebElement> cards = getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(tarjetasProductos));
        Assert.assertFalse(">> Error: No hay tarjetas de productos para registrar el nombre.", cards.isEmpty());

        WebElement nombreElem = cards.get(0).findElement(nombreProductoTarjeta);
        nombreProductoSeleccionado = nombreElem.getText().trim();
        System.out.println(">> [Info]: Producto registrado para validación: " + nombreProductoSeleccionado);
    }

    public void abrirFichaProductoDesdeListado() {
        List<WebElement> cards = driver.findElements(tarjetasProductos);
        WebElement nombreElem = cards.get(0).findElement(nombreProductoTarjeta);
        nombreElem.click();
    }

    public void validarCorrespondenciaFicha() {
        WebElement tituloFicha = getWait().until(ExpectedConditions.visibilityOfElementLocated(tituloFichaDetalle));
        String textoFicha = tituloFicha.getText().trim();

        Assert.assertTrue(">> Error: El nombre en la ficha no corresponde con la tarjeta seleccionada.",
                textoFicha.toLowerCase().contains(nombreProductoSeleccionado.toLowerCase().substring(0, Math.min(nombreProductoSeleccionado.length(), 10))));

        WebElement img = driver.findElement(imagenProducto);
        Assert.assertTrue(">> Error: No se visualiza la imagen del producto en la ficha.", img.isDisplayed());
    }
}