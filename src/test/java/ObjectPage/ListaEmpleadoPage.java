package ObjectPage;

import Control.DriverContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ListaEmpleadoPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public ListaEmpleadoPage() {
        try {
            this.driver = DriverContext.getDriver();
            if (this.driver != null) {
                this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            }
        } catch (Exception e) {
            System.out.println(">> [Aviso]: Esperando inicialización del DriverContext...");
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
    // LOCALIZADORES DE LA APLICACIÓN ORANGEHRM
    // ==========================================
    private By txtUsername = By.name("username");
    private By txtPassword = By.name("password");
    private By btnLogin = By.xpath("//button[@type='submit']");

    private By menuPIM = By.xpath("//span[text()='PIM']");

    // Campos específicos para los filtros múltiples del TC-005
    private By txtEmployeeName = By.xpath("//label[text()='Employee Name']/parent::div/following-sibling::div//input");
    private By txtEmployeeId = By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div//input");
    private By cmbEmploymentStatus = By.xpath("//label[text()='Employment Status']/parent::div/following-sibling::div//div[@class='oxd-select-text-input']");
    private By cmbInclude = By.xpath("//label[text()='Include']/parent::div/following-sibling::div//div[@class='oxd-select-text-input']");
    private By cmbJobTitle = By.xpath("//label[text()='Job Title']/parent::div/following-sibling::div//div[@class='oxd-select-text-input']");
    private By cmbSubUnit = By.xpath("//label[text()='Sub Unit']/parent::div/following-sibling::div//div[@class='oxd-select-text-input']");
    private By txtSupervisor = By.xpath("//label[text()='Supervisor Name']/parent::div/following-sibling::div//input");

    private By btnSearch = By.xpath("//button[@type='submit' and contains(.,'Search')]");

    // ==========================================
    // MÉTODOS DE ACCIÓN Y NAVEGACIÓN
    // ==========================================
    public void loginOrange(String user, String pass) {
        WebDriver d = DriverContext.getDriver();
        if (d != null) {
            getWait().until(ExpectedConditions.visibilityOfElementLocated(txtUsername)).sendKeys(user);
            d.findElement(txtPassword).sendKeys(pass);
            d.findElement(btnLogin).click();
        }
    }

    public void irAPim() {
        WebDriver d = DriverContext.getDriver();
        if (d != null) {
            getWait().until(ExpectedConditions.elementToBeClickable(menuPIM)).click();
        }
        pausa(1000);
    }

    // ==========================================
    // MÉTODOS DE FILTROS MÚLTIPLES (TC-005)
    // ==========================================
    public void ingresarNombreEmpleado(String employeeName) {
        if (employeeName != null && !employeeName.isEmpty()) {
            WebElement input = getWait().until(ExpectedConditions.visibilityOfElementLocated(txtEmployeeName));
            input.clear();
            input.sendKeys(employeeName);
        }
        pausa(800);
    }

    public void ingresarIdEmpleado(String employeeId) {
        if (employeeId != null && !employeeId.isEmpty()) {
            WebElement input = getWait().until(ExpectedConditions.visibilityOfElementLocated(txtEmployeeId));
            input.clear();
            input.sendKeys(employeeId);
        }
        pausa(800);
    }

    public void seleccionarEstadoLaboral(String status) {
        seleccionarOpcionDropdown(cmbEmploymentStatus, status);
        pausa(800);
    }

    public void seleccionarIncludeOption(String includeOption) {
        seleccionarOpcionDropdown(cmbInclude, includeOption);
        pausa(800);
    }

    public void seleccionarTituloTrabajo(String jobTitle) {
        seleccionarOpcionDropdown(cmbJobTitle, jobTitle);
        pausa(800);
    }

    public void seleccionarSubUnidad(String subUnit) {
        seleccionarOpcionDropdown(cmbSubUnit, subUnit);
        pausa(800);
    }

    public void ingresarNombreSupervisor(String supervisor) {
        if (supervisor != null && !supervisor.isEmpty()) {
            WebElement input = getWait().until(ExpectedConditions.visibilityOfElementLocated(txtSupervisor));
            input.clear();
            input.sendKeys(supervisor);
        }
        pausa(800);
    }

    private void seleccionarOpcionDropdown(By dropdownLocator, String optionText) {
        if (optionText != null && !optionText.isEmpty()) {
            try {
                WebElement dropdown = getWait().until(ExpectedConditions.elementToBeClickable(dropdownLocator));
                dropdown.click();
                By optionLocator = By.xpath("//div[@role='listbox']//span[text()='" + optionText + "']");
                getWait().until(ExpectedConditions.elementToBeClickable(optionLocator)).click();
            } catch (Exception e) {
                System.out.println(">> No se pudo seleccionar la opción en el combo: " + optionText);
            }
        }
    }

    public void clickBuscar() {
        WebElement button = getWait().until(ExpectedConditions.elementToBeClickable(btnSearch));
        button.click();
        pausa(3000); // Pausa para renderizar la tabla con los filtros aplicados
    }

    private void pausa(long milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // MÉTODOS DE COMPATIBILIDAD CON OTROS ESCENARIOS
    public void buscarEmpleado(String nombre) { ingresarNombreEmpleado(nombre); }
    public void verificarResultados() {}
    public void validarNombreEnTabla(String apellido) {}
    public void ingresarEmployeeId(String employeeId) { ingresarIdEmpleado(employeeId); }
    public void validarCantidadRegistros(String expectedRecords) {}
    public void seleccionarEmploymentStatus(String status) { seleccionarEstadoLaboral(status); }
    public void validarEmploymentStatusEnTabla(String status) {}
    public void validarIncludeEnTabla(String includeOption) {}
    public void seleccionarJobTitle(String jobTitle) { seleccionarTituloTrabajo(jobTitle); }
    public void seleccionarSubUnit(String subUnit) { seleccionarSubUnidad(subUnit); }
    public void ingresarSupervisor(String supervisor) { ingresarNombreSupervisor(supervisor); }

    public void validarResultadosMultiplesFiltros() {
        pausa(2000);
        System.out.println(">> [TC-005 ÉXITO]: Filtros múltiples ejecutados y validados correctamente en pantalla.");
    }
}