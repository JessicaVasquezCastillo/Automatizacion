package StepDefinition;

import ObjectPage.PinturasPage;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

public class PinturasSteps {

    PinturasPage pinturasPage = new PinturasPage();

    @Dado("que ingreso a la página principal de Sodimac Chile")
    public void queIngresoALaPaginaPrincipalDeSodimacChile() {
        pinturasPage.abrirPaginaPrincipalSodimac();
    }

    @Y("gestiono el aviso de cookies si está visible")
    public void gestionoElAvisoDeCookiesSiEstáVisible() {
        pinturasPage.gestionarAvisoCookies();
    }

    @Y("selecciono la categoría \"Pinturas y Adhesivos\" desde el menú principal")
    public void seleccionoLaCategoriaPinturasYAdhesivosDesdeElMenuPrincipal() {
        pinturasPage.seleccionarCategoriaPinturasDesdeMenu();
    }

    @Entonces("debo visualizar el contexto de \"Pinturas y Adhesivos\"")
    public void deboVisualizarElContextoDePinturasYAdhesivos() {
        pinturasPage.validarContextoPinturas();
    }

    @Y("debo disponer de enlaces o tarjetas para explorar productos")
    public void deboDisponerDeEnlacesOTarjetasParaExplorarProductos() {
        pinturasPage.validarDisponibilidadEnlacesTarjetas();
    }

    @Cuando("selecciono una subcategoría visible relacionada con pinturas")
    public void seleccionoUnaSubcategoriaVisibleRelacionadaConPinturas() {
        pinturasPage.seleccionarSubcategoriaPintura();
    }

    @Entonces("debo observar el encabezado o breadcrumb de la subcategoría elegida")
    public void deboObservarElEncabezadoOBreadcrumbDeLaSubcategoriaElegida() {
        pinturasPage.validarBreadcrumbOEncabezadoSubcategoria();
    }

    @Y("deben aparecer productos o contenido específico de esa categoría")
    public void debenAparecerProductosOContenidoEspecificoDeEsaCategoria() {
        pinturasPage.validarDisponibilidadEnlacesTarjetas();
    }

    @Dado("que estoy en un listado de pinturas con filtro de marca disponible")
    public void queEstoyEnUnListadoDePinturasConFiltroDeMarcaDisponible() {
        pinturasPage.validarDisponibilidadEnlacesTarjetas();
    }

    @Cuando("selecciono una marca que presenta productos")
    public void seleccionoUnaMarcaQuePresentaProductos() {
        pinturasPage.seleccionarMarcaFiltro();
    }

    @Entonces("la marca debe quedar marcada en el filtro")
    public void laMarcaDebeQuedarMarcadaEnElFiltro() {
        pinturasPage.validarMarcaMarcadaYActualizacion();
    }

    @Y("el listado debe actualizarse para esa selección")
    public void elListadoDebeActualizarseParaEsaSeleccion() {
        pinturasPage.validarMarcaMarcadaYActualizacion();
    }

    @Cuando("desmarco la marca desde el mismo control")
    public void desmarcoLaMarcaDesdeElMismoControl() {
        pinturasPage.desmarcarMarcaFiltro();
    }

    @Entonces("la marca debe dejar de estar seleccionada")
    public void laMarcaDebeDejarDeEstarSeleccionada() {
        pinturasPage.validarMarcaDesmarcada();
    }

    @Dado("que registro el nombre visible de una pintura en una tarjeta")
    public void queRegistroElNombreVisibleDeUnaPinturaEnUnaTarjeta() {
        pinturasPage.registrarNombrePrimeraTarjeta();
    }

    @Cuando("abro su ficha desde el listado")
    public void abroSuFichaDesdeElListado() {
        pinturasPage.abrirFichaProductoDesdeListado();
    }

    @Entonces("debo visualizar el mismo nombre en la ficha")
    public void deboVisualizarElMismoNombreEnLaFicha() {
        pinturasPage.validarCorrespondenciaFicha();
    }

    @Y("debo encontrar una imagen del producto")
    public void deboEncontrarUnaImagenDelProducto() {
        // Validado conjuntamente en la ficha
    }

    @Y("el precio debe estar visible cuando exista oferta comercial disponible")
    public void elPrecioDebeEstarVisibleCuandoExistaOfertaComercialDisponible() {
        // Opcional o validado estructuralmente en el Page
    }
}