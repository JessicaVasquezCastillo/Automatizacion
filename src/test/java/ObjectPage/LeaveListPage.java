package ObjectPage;

import Control.DriverContext;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import org.junit.Assert;

public class LeaveListPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public LeaveListPage() {
        try {
            this.driver = DriverContext.getDriver();
            if (this.driver != null) {
                this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            }
        } catch (Exception e) {
            System.out.println(">> [Aviso]: Esperando inicialización del DriverContext en LeaveListPage...");
        }
    }

    private WebDriverWait getWait() {
        if (wait == null) {
            this.driver = DriverContext.getDriver();
            if (this.driver != null) {
                wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            }
        }
        return wait;
    }

    // ==========================================
    // LOCALIZADORES EXACTOS DE ORANGEHRM LEAVE LIST
    // ==========================================
    private By menuLeave = By.xpath("//span[text()='Leave']");

    // Campos del formulario Leave List
    private By txtFechaInicio = By.xpath("//label[text()='From Date']/parent::div/following-sibling::div//input");
    private By txtFechaFin = By.xpath("//label[text()='To Date']/parent::div/following-sibling::div//input");
    private By cmbShowLeaveStatus = By.xpath("//label[text()='Show Leave with Status']/parent::div/following-sibling::div//div[@class='oxd-select-text-input' or @class='oxd-multiselect-text-input']");
    private By txtEmpleado = By.xpath("//label[text()='Employee Name']/parent::div/following-sibling::div//input");
    private By optionAutocompletado = By.xpath("//div[@role='listbox']//span");
    private By cmbSubUnit = By.xpath("//label[text()='Sub Unit']/parent::div/following-sibling::div//div[@class='oxd-select-text-input']");
    private By chkIncludePastEmployees = By.xpath("//label[text()='Include Past Employees']/parent::div//span[@class='oxd-switch-input']");

    private By btnSearch = By.xpath("//button[@type='submit' and contains(.,'Search')]");
    private By btnReset = By.xpath("//button[@type='reset' and contains(.,'Reset')]");

    // Localizadores para validación de resultados
    private By lblNoRecordsFound = By.xpath("//*[text()='No Records Found' or contains(.,'No Records Found')]");
    private By filasTabla = By.xpath("//div[@class='oxd-table-body']/div[@class='oxd-table-card']");
    private By celdaEmpleado = By.xpath(".//div[@role='row']/div[3]");
    private By celdaEstado = By.xpath(".//div[@role='row']/div[6]");

    // ==========================================
    // MÉTODOS DE INTERACCIÓN Y FILTROS
    // ==========================================
    public void navegarALeaveList() {
        WebDriver d = DriverContext.getDriver();
        if (d != null) {
            getWait().until(ExpectedConditions.elementToBeClickable(menuLeave)).click();
        }
        pausa(1500);
    }

    public void ingresarFechaInicial(String fecha) {
        if (fecha != null && !fecha.isEmpty()) {
            WebElement input = getWait().until(ExpectedConditions.visibilityOfElementLocated(txtFechaInicio));
            input.click();
            input.sendKeys(Keys.CONTROL + "a");
            input.sendKeys(Keys.BACK_SPACE);
            input.sendKeys(fecha);
        }
        pausa(800);
    }

    public void ingresarFechaFin(String fecha) {
        if (fecha != null && !fecha.isEmpty()) {
            WebElement input = getWait().until(ExpectedConditions.visibilityOfElementLocated(txtFechaFin));
            input.click();
            input.sendKeys(Keys.CONTROL + "a");
            input.sendKeys(Keys.BACK_SPACE);
            input.sendKeys(fecha);
        }
        pausa(800);
    }

    public void seleccionarEstado(String estado) {
        if (estado != null && !estado.isEmpty()) {
            try {
                WebElement combo = getWait().until(ExpectedConditions.elementToBeClickable(cmbShowLeaveStatus));
                combo.click();
                pausa(600);

                By op = By.xpath("//div[@role='option']//span[normalize-space()='" + estado + "']");
                WebElement opcion = getWait().until(ExpectedConditions.elementToBeClickable(op));

                try {
                    opcion.click();
                } catch (Exception ex) {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].click();", opcion);
                }

                System.out.println(">> [Éxito]: Estado seleccionado correctamente: " + estado);
            } catch (Exception e) {
                System.out.println(">> [Aviso]: No se pudo seleccionar el estado de Leave: " + estado);
            }
        }
        pausa(800);
    }

    public void ingresarNombreEmpleado(String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            WebElement input = getWait().until(ExpectedConditions.visibilityOfElementLocated(txtEmpleado));
            input.clear();
            input.sendKeys(nombre);
        }
        pausa(1000);
    }

    public void seleccionarEmpleadoAutocompletado() {
        try {
            By optionAutocompletado = By.xpath("//div[@role='listbox']//span");
            By optionNoRecords = By.xpath("//div[@role='listbox']//*[contains(text(),'No Options')]");

            // Validar si aparece 'No Options' cuando el empleado no existe
            if (driver.findElements(optionNoRecords).size() > 0) {
                System.out.println(">> [Aviso Validación]: El empleado ingresado no fue encontrado en el sistema ('No Options'). El flujo continúa.");
                driver.findElement(By.xpath("//label[text()='Leave List']")).click();
            } else {
                WebElement opt = getWait().until(ExpectedConditions.elementToBeClickable(optionAutocompletado));
                opt.click();
                System.out.println(">> [Autocompletado]: Empleado seleccionado con éxito de la lista.");
            }
        } catch (Exception e) {
            System.out.println(">> [Aviso Validación]: No se encontró coincidencia para el empleado ingresado. Continuando con el flujo de prueba...");
            try {
                driver.findElement(By.xpath("//label[text()='Leave List']")).click();
            } catch (Exception ignored) {}
        }
        pausa(800);
    }
    public void seleccionarUnidad(String unidad) {
        if (unidad != null && !unidad.isEmpty()) {
            try {
                WebElement combo = getWait().until(ExpectedConditions.elementToBeClickable(cmbSubUnit));
                combo.click();
                By op = By.xpath("//div[@role='listbox']//span[normalize-space()='" + unidad + "']");
                getWait().until(ExpectedConditions.elementToBeClickable(op)).click();
            } catch (Exception e) {
                System.out.println(">> No se pudo seleccionar la unidad: " + unidad);
            }
        }
        pausa(800);
    }

    public void configurarEmpleadosAnteriores(String anteriores) {
        if ("Yes".equalsIgnoreCase(anteriores)) {
            try {
                WebElement chk = getWait().until(ExpectedConditions.elementToBeClickable(chkIncludePastEmployees));
                chk.click();
            } catch (Exception e) {
                System.out.println(">> No se pudo activar el selector de empleados anteriores.");
            }
        }
        pausa(800);
    }

    public void hacerClickSearch() {
        WebElement btn = getWait().until(ExpectedConditions.elementToBeClickable(btnSearch));
        btn.click();
        pausa(3000);
    }

    public void hacerClickReset() {
        WebElement btn = getWait().until(ExpectedConditions.elementToBeClickable(btnReset));
        btn.click();
        pausa(2000);
    }

    // ==========================================
    // VALIDACIONES REALES (DOM & JUNIT)
    // ==========================================
    public void validarVisualizacionTabla() {
        boolean hayFilas = driver.findElements(filasTabla).size() > 0;
        boolean hayMensajeVacio = driver.findElements(lblNoRecordsFound).size() > 0;

        Assert.assertTrue(">> Error: No se visualiza ni la tabla de resultados ni el mensaje de 'No Records Found'.",
                hayFilas || hayMensajeVacio);

        if (hayMensajeVacio) {
            System.out.println(">> [Validación OK]: La búsqueda devolvió 'No Records Found' correctamente.");
        } else {
            System.out.println(">> [Validación OK]: La tabla muestra registros con éxito.");
        }
    }

    public void validarEmpleadoEnResultados(String empleadoEsperado) {
        if (driver.findElements(lblNoRecordsFound).size() == 0 && empleadoEsperado != null && !empleadoEsperado.isEmpty()) {
            List<WebElement> filas = driver.findElements(filasTabla);
            for (WebElement fila : filas) {
                String textoEmpleado = fila.findElement(celdaEmpleado).getText();
                Assert.assertTrue(">> Error: El empleado registrado no coincide.",
                        textoEmpleado.toLowerCase().contains(empleadoEsperado.toLowerCase().trim()));
            }
        }
    }

    public void validarEstadoEnResultados(String estadoEsperado) {
        if (driver.findElements(lblNoRecordsFound).size() == 0 && estadoEsperado != null && !estadoEsperado.isEmpty()) {
            List<WebElement> filas = driver.findElements(filasTabla);
            for (WebElement fila : filas) {
                String textoEstado = fila.findElement(celdaEstado).getText();
                Assert.assertTrue(">> Error: El estado registrado no coincide.",
                        textoEstado.toLowerCase().contains(estadoEsperado.toLowerCase().trim()));
            }
        }
    }

    public void validarRestablecimientoFiltros() {
        WebElement inputEmp = driver.findElement(txtEmpleado);
        String valor = inputEmp.getAttribute("value");
        Assert.assertTrue(">> Error: El campo de empleado no se limpió tras presionar Reset.",
                valor == null || valor.isEmpty());
    }

    private void pausa(long milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}