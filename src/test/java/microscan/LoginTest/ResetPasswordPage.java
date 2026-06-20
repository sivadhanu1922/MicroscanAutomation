package microscan.LoginTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResetPasswordPage {

    WebDriver driver;

    public ResetPasswordPage(WebDriver driver) {

        this.driver = driver;
    }

    // Locators

    By newPassword = By.id("newPassword");

    By confirmPassword = By.id("confirmPassword");

    By submitButton =
            By.xpath("//button[@type='submit']");

    // Methods

    public void enterNewPassword(String password) {

        driver.findElement(newPassword)
                .sendKeys(password);
    }

    public void enterConfirmPassword(String password) {
        driver.findElement(confirmPassword)
                .sendKeys(password);
    }

    public void clickSubmitButton() {
        driver.findElement(submitButton)
                .click();
    }
}