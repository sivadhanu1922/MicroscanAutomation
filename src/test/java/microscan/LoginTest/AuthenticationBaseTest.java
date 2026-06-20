package microscan.LoginTest;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

//import static org.bouncycastle.oer.its.template.ieee1609dot2.basetypes.Ieee1609Dot2BaseTypes.Duration;

public class AuthenticationBaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver,
                Duration.ofSeconds(15));

        driver.get("http://localhost:3000");
    }

    @AfterMethod
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}