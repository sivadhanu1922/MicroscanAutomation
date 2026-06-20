package microscan.Tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import microscan.LoginTest.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;
import java.time.Duration;

public class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);

    @Getter
    private static WebDriver driver;
    @Getter
    private static WebDriverWait wait;

    @BeforeSuite
    public void setUp() {

        // Step 1: Setup Chrome Browser
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        log.info("Chrome browser launched and configured");

        // Step 2: Open Application
        driver.get("http://localhost:3000");
        log.info("Navigated to http://localhost:3000");

        // Step 3: Login using LoginPage (composition, not inheritance)
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("username")));
        LoginPage login = new LoginPage(driver);
        login.enterUsername("Siva");
        log.info("Username entered: Siva");

        login.enterPassword("Siva@123");
        log.info("Password entered");

        login.clickLogin();
        log.info("Login button clicked");

        // Step 4: Wait for OTP Page
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("input[placeholder='Enter 6 digit OTP']")));
        log.info("OTP page loaded");

        // Step 5: Enter OTP
        driver.findElement(
                By.cssSelector("input[placeholder='Enter 6 digit OTP']")).sendKeys("123456");
        log.info("OTP entered");

        // Step 6: Click Verify Button
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Verify']")));
        driver.findElement(
                By.xpath("//button[text()='Verify']")).click();
        log.info("Verify button clicked");

        // Step 7: Wait for Dashboard to Load
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[text()='Dashboard']")));
        log.info("LOGIN SUCCESS: Dashboard loaded, ready for tests");
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.info("BROWSER CLOSED: All tests completed");
        }
    }
}