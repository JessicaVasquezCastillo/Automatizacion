package StepDefinition;

import ObjectPage.HomePage;
import ObjectPage.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginDefinition {

    private final LoginPage loginPage = new LoginPage();
    private final HomePage homePage = new HomePage();

    @And("ingresar el usuario {string}")
    public void ingresarUsuario(String usuario) {
        loginPage.escribirUsersname(usuario);
    }

    @And("ingresar la pass {string}")
    public void ingresarClave(String clave) {
        loginPage.escribirPassword(clave);
    }

    @When("presiono el boton Submit")
    public void clicBotonIngresar() {
        loginPage.clickBtnSubmit();
    }

    @Then("se valida el mensaje Logged In Successfully")
    public void ValidoMensaje() {
        homePage.seValidaElMensaje();
    }
}