package StepDefinition;

import ObjectPage.ListaEmpleadoPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class OrangeDefinition {

    ListaEmpleadoPage listaEmpleadoPage = new ListaEmpleadoPage();

    // ==========================================
    // PASOS DEL BACKGROUND
    // ==========================================
    @And("que el usuario inicia sesión en OrangeHRM con el usuario {string} y la contraseña {string}")
    public void queElUsuarioIniciaSesionEnOrangeHRM(String user, String pass) {
        listaEmpleadoPage.loginOrange(user, pass);
    }

    @And("que el usuario ingresa al módulo PIM")
    public void queElUsuarioIngresaAlModuloPIM() {
        listaEmpleadoPage.irAPim();
    }

    // ==========================================
    // PASOS DEL TC-001: BÚSQUEDA POR NOMBRE
    // ==========================================
    @Given("el usuario ingresa {string} en el campo Nombre del empleado de PIM")
    public void elUsuarioIngresaEnElCampoNombreDelEmpleadoDePIM(String nombre) {
        listaEmpleadoPage.buscarEmpleado(nombre);
    }

    @And("hace clic en Buscar")
    public void haceClicEnBuscar() {
        listaEmpleadoPage.clickBuscar();
    }

    @Then("se deben mostrar los resultados de la búsqueda")
    public void seDebenMostrarLosResultadosDeLaBusqueda() {
        listaEmpleadoPage.verificarResultados();
    }

    @And("cada resultado debe contener {string}")
    public void cadaResultadoDebeContener(String apellido) {
        listaEmpleadoPage.validarNombreEnTabla(apellido);
    }

    // ==========================================
    // PASOS DEL TC-002: BÚSQUEDA POR EMPLOYEE ID
    // ==========================================
    @When("the user enters employee id {string}")
    public void theUserEntersEmployeeId(String employeeId) {
        listaEmpleadoPage.ingresarEmployeeId(employeeId);
    }

    @And("clicks Search")
    public void clicksSearch() {
        listaEmpleadoPage.clickBuscar();
    }

    @Then("the system should return {string} record\\(s)")
    public void theSystemShouldReturnRecordS(String expectedRecords) {
        listaEmpleadoPage.validarCantidadRegistros(expectedRecords);
    }

    // ==========================================
    // PASOS DEL TC-003: BÚSQUEDA POR EMPLOYMENT STATUS
    // ==========================================
    @When("the user selects employment status {string}")
    public void theUserSelectsEmploymentStatus(String status) {
        listaEmpleadoPage.seleccionarEmploymentStatus(status);
    }

    @Then("every result should have employment status {string}")
    public void everyResultShouldHaveEmploymentStatus(String status) {
        listaEmpleadoPage.validarEmploymentStatusEnTabla(status);
    }

    // ==========================================
    // STEPS DEL TC-004 (Filtro Include)
    // ==========================================
    @When("the user selects include option {string}")
    public void theUserSelectsIncludeOption(String includeOption) {
        listaEmpleadoPage.seleccionarIncludeOption(includeOption);
    }

    @Then("results should belong to {string}")
    public void resultsShouldBelongTo(String includeOption) {
        listaEmpleadoPage.validarIncludeEnTabla(includeOption);
    }

    @When("the user selects job title {string}")
    public void theUserSelectsJobTitle(String jobTitle) {
        listaEmpleadoPage.seleccionarJobTitle(jobTitle);
    }

    @When("the user selects sub unit {string}")
    public void theUserSelectsSubUnit(String subUnit) {
        listaEmpleadoPage.seleccionarSubUnit(subUnit);
    }

    @When("the user selects supervisor {string}")
    public void theUserSelectsSupervisor(String supervisor) {
        listaEmpleadoPage.ingresarSupervisor(supervisor);
    }

    // ==========================================
    // STEPS DEL TC-005 (Filtros Múltiples)
    // ==========================================
    @When("the user enters employee name {string}")
    public void theUserEntersEmployeeName(String employeeName) {
        listaEmpleadoPage.ingresarNombreEmpleado(employeeName);
    }

    @When("the user enters employee id for filter {string}")
    public void theUserEntersEmployeeIdForFilter(String employeeId) {
        listaEmpleadoPage.ingresarIdEmpleado(employeeId);
    }

    @When("the user selects employment status filter {string}")
    public void theUserSelectsEmploymentStatusFilter(String status) {
        listaEmpleadoPage.seleccionarEstadoLaboral(status);
    }

    @When("the user selects job title filter {string}")
    public void theUserSelectsJobTitleFilter(String jobTitle) {
        listaEmpleadoPage.seleccionarTituloTrabajo(jobTitle);
    }

    @When("the user selects sub unit filter {string}")
    public void theUserSelectsSubUnitFilter(String subUnit) {
        listaEmpleadoPage.seleccionarSubUnidad(subUnit);
    }

    @When("the user selects supervisor name {string}")
    public void theUserSelectsSupervisorName(String supervisor) {
        listaEmpleadoPage.ingresarNombreSupervisor(supervisor);
    }

    @Then("every result should match all selected criteria")
    public void everyResultShouldMatchAllSelectedCriteria() {
        listaEmpleadoPage.validarResultadosMultiplesFiltros();
    }
}
