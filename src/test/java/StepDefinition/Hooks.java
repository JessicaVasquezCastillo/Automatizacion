package StepDefinition;

import Control.DriverContext;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    private Scenario scenario;
    private static final String tomarCapturaPantalla = System.getProperty("evidence", "fullEvidence");

    @Before
    public void setUp(Scenario scenario) {
        this.scenario = scenario;
    }

    @AfterStep
    public void capturarPantallaPorPaso(Scenario scenario) {
        if (DriverContext.getDriver() != null) {
            try {
                byte[] screenshot = ((TakesScreenshot) DriverContext.getDriver()).getScreenshotAs(OutputType.BYTES);

                if (scenario.isFailed()) {
                    scenario.attach(screenshot, "image/png", "Embedding [FAIL] Step ScreenShots");
                } else if ("fullEvidence".equalsIgnoreCase(tomarCapturaPantalla)) {
                    scenario.attach(screenshot, "image/png", "Embedding [SUCCESS] Step ScreenShots");
                }
            } catch (Exception e) {
                System.err.println(">> [Hooks Warning]: No se pudo capturar la pantalla del paso: " + e.getMessage());
            }
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (DriverContext.getDriver() != null) {
                DriverContext.getDriver().quit();
            }
        } catch (Exception e) {
            System.err.println("[Hooks Warning]: Error al cerrar el navegador: " + e.getMessage());
        }
    }
}