package StepDefinition;

import ObjectPage.NavidadPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

public class NavidadDefinition {

    NavidadPage navidadPage = new NavidadPage();

    @Given("que el usuario abre la página Navidad de Sodimac")
    public void queElUsuarioAbreLaPaginaNavidadDeSodimac() {
        navidadPage.abrirSodimacYNavegarANavidad();
    }

    @When("finaliza la carga visual de la página")
    public void finalizaLaCargaVisualDeLaPagina() {
        navidadPage.esperarCargaVisual();
        navidadPage.scrollYRecopilarProductosCompletos();
    }

    @Then("debe visualizar el encabezado principal")
    public void debeVisualizarElEncabezadoPrincipal() {
        navidadPage.validarEncabezado();
    }

    @And("debe visualizar el título de la categoría")
    public void debeVisualizarElTituloDeLaCategoria() {
        navidadPage.validarTituloCategoria();
    }

    @And("debe visualizar el contenedor de productos")
    public void debeVisualizarElContenedorDeProductos() {
        navidadPage.validarContenedorProductos();
    }

    @And("cada tarjeta de producto visible debe mostrar")
    public void cadaTarjetaDeProductoVisibleDebeMostrar(DataTable dataTable) {
        List<String> objetos = dataTable.asList();
        navidadPage.validarEstructuraTarjetas(objetos);
    }

    @And("las imágenes deben mostrarse correctamente")
    public void lasImagenesDebenMostrarseCorrectamente() {
        navidadPage.validarImagenesCorrectas();
    }

    @And("los nombres no deben estar vacíos")
    public void losNombresNoDebenEstarVacios() {
        navidadPage.validarNombresNoVacios();
    }

    @And("los precios deben tener formato monetario CLP")
    public void losPreciosDebenTenerFormatoMonetarioCLP() {
        navidadPage.validarFormatoPreciosCLP();
    }

    @And("ningún elemento debe superponerse")
    public void ningunElementoDebeSuperponerse() {
        navidadPage.validarSinSuperposicion();
    }

    @And("no deben existir tarjetas visualmente duplicadas")
    public void noDebenExistirTarjetasVisualmenteDuplicadas() {
        navidadPage.validarSinTarjetasDuplicadas();
    }
}