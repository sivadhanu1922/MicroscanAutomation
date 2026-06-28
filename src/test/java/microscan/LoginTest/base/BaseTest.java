package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

public class BaseTest {

    public static WebDriver driver;

    @BeforeMethod
    public void setup() {

       //driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.setBinary("D:\\\\Selenium Learning\\\\chrome-win64\\\\chrome-win64\\\\chrome.exe");

         driver = new ChromeDriver(options);

        driver.manage().window().maximize();

        driver.get("http://localhost:3000");

    }

    @AfterMethod
    public void close() {

        driver.quit();
    }
}