package microscan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage {

    WebDriver driver;

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
    By forgotPasswordLink =
            By.xpath("//*[contains(text(),'Forgot Password')]");
    By forgotPasswordInput =
            By.id("forgotPasswordInput");
    By submitButton =
            By.xpath("//button[contains(text(),'Submit')]");
    By backToLoginButton =
            By.xpath("//*[contains(text(),'Back to Login')]");

    // Methods
    public void clickForgotPasswordLink() {
        driver.findElement(forgotPasswordLink)
                .click();
    }

    public void enterUsername(String username) {
        driver.findElement(forgotPasswordInput)
                .sendKeys(username);
    }

    public void clickSubmitButton() {
        driver.findElement(submitButton)
                .click();
    }

    public void clickBackToLoginButton() {
        driver.findElement(backToLoginButton)
                .click();
    }
}