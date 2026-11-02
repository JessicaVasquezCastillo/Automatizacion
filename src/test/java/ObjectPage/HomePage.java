package ObjectPage;

import Control.BaseController;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.fail;

public class HomePage extends BaseController {

    @FindBy(xpath = "//h1[contains(text(),'Logged In Successfully')]")
    private WebElement txtMensajeExito;

    public void seValidaElMensaje() {
        try {
            System.out.println("EL mensaje es visible!");
        } catch (Exception e) {
            fail("Error al validar el mensaje de éxito: " + e.getMessage());
        }
    }
}