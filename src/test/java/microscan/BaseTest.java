package microscan;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;
import java.time.Duration;

public class BaseTest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    // Static getter for driver
    public static WebDriver getDriver() {
        return driver;
    }

    // Static getter for wait
    public static WebDriverWait getWait() {
        return wait;
    }

    @BeforeClass
    public void setUp() {

        // Step 1: Setup Chrome Browser
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Step 2: Open Application
        driver.get("http://localhost:3000");

        // Step 3: Enter Username
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("input[placeholder='Enter username']")));
        driver.findElement(
                By.cssSelector("input[placeholder='Enter username']")).sendKeys("Siva");

        // Step 4: Enter Password
        driver.findElement(
                By.cssSelector("input[placeholder='Enter password']")).sendKeys("Siva@123");

        // Step 5: Click Login Button
        driver.findElement(By.xpath("//button[text()='Login']")).click();

        // Step 6: Wait for OTP Page
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("input[placeholder='Enter 6 digit OTP']")));

        // Step 7: Enter OTP
        driver.findElement(
                By.cssSelector("input[placeholder='Enter 6 digit OTP']")).sendKeys("123456");

        // Step 8: Click Verify Button
        driver.findElement(By.xpath("//button[text()='Verify']")).click();

        // Step 9: Wait for Dashboard to Load
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[text()='Dashboard']")));

        System.out.println("LOGIN SUCCESS: Browser opened and logged in successfully");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("BROWSER CLOSED: All tests completed");
        }
    }
}