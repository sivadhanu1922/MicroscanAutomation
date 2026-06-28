package microscan.Pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;

public class BaseTest {
    @Getter
    protected static WebDriver driver;
    @Getter
    protected static WebDriverWait wait;

    @BeforeSuite(alwaysRun = true)
    public void setUp() {
        System.out.println("BEFORE SUITE EXECUTED");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("http://localhost:3000");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[placeholder='Enter username']")));
        driver.findElement(By.cssSelector("input[placeholder='Enter username']")).sendKeys("Navi");
        driver.findElement(By.cssSelector("input[placeholder='Enter password']")).sendKeys("Navi000@");
        driver.findElement(By.xpath("//button[text()='Login']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[placeholder='Enter 6 digit OTP']")));
        driver.findElement(By.cssSelector("input[placeholder='Enter 6 digit OTP']")).sendKeys("111111");
        driver.findElement(By.xpath("//button[text()='Verify']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Dashboard']")));
        System.out.println("LOGIN SUCCESS: Browser opened and logged in successfully");
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("BROWSER CLOSED: All tests completed");
        }
    }
}