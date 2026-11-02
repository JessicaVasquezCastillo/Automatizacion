package Control;

import Constant.Navegador;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

public class DriverManager {
    private WebDriver driver;

    private final DesiredCapabilities ConfiguracionesNav = new DesiredCapabilities();

    protected void resolverDriver(Navegador nav, String url){
        String os = System.getProperty("os.name").toLowerCase();
        String osVersion = System.getProperty("os.version").toLowerCase();
        System.out.println("\nSistema Operativo :" + os+", "+osVersion);
        System.out.println("\nNavegador :" + nav);
        switch (nav){
            case Chrome:
                System.out.println("Chrome seleccionado");
                ChromeOptions configuracionChrome = new ChromeOptions();
                configuracionChrome.addArguments("--remote-allow-origins=yes");
                configuracionChrome.addArguments("--disable-gpu");
                configuracionChrome.addArguments("--window-size=1920,1080");

                if(os.contains("linux")){
                    System.out.println(System.getProperty("user.name"));
                    configuracionChrome.addArguments("--disable-dev-shm-usage");
                    configuracionChrome.addArguments("--no-sandbox");
                    configuracionChrome.addArguments("--headless=new");
                    configuracionChrome.addArguments("--ignore-ssl-errors=yes");
                } else if (os.contains("windows")) {
                    configuracionChrome.addArguments("--no-sandbox");
                }

                WebDriverManager.chromedriver().clearDriverCache().clearResolutionCache().setup();
                System.setProperty("webdriver.manager.verbose","true");
                this.driver = new ChromeDriver(configuracionChrome);
                this.driver.manage().deleteAllCookies();
                break;
            default:
                System.out.println("No es posible levantar el navegador "+nav);
                break;
        }
        if (this.driver != null) {
            this.driver.manage().window().maximize();
            this.driver.get(url);
        }
    }

    protected WebDriver getDriver(){
        if(driver == null){
            return null;
        }else{
            return driver;
        }
    }
}
