package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"StepDefinition"},
        plugin = {"pretty", "html:target/cucumber-report.html"},
        monochrome = true,
        tags =  "@qa09" // Para ejecutar exclusivamente el escenario de QA09
        //tags = "@catalogo" // Para ejecutar exclusivamente el catálogo de Navidad
)
public class Runner {
}