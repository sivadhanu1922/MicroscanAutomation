package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.ExtentLogger;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    By username = By.id("username");
    By password = By.id("password");
    By loginButton = By.xpath("//button[@type='submit']");

    public void enterUsername(String user) {

//        ExtentLogger.info("Entering Username");
        driver.findElement(username).sendKeys(user);
    }

    public void enterPassword(String pass) {

//        ExtentLogger.info("Entering Password");
        driver.findElement(password).sendKeys(pass);
    }

    public void clickLogin() {

//        ExtentLogger.info("Clicking Login Button");
        driver.findElement(loginButton).click();
    }
}