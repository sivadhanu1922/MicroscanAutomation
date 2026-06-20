package microscan.LoginTest;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ResetPasswordTest extends AuthenticationBaseTest {

    @BeforeMethod
    public void openResetPasswordPage() {

        ForgotPasswordPage forgot =
                new ForgotPasswordPage(driver);
        forgot.clickForgotPasswordLink();
        forgot.enterUsername("Jayasri");
        forgot.clickSubmitButton();
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("newPassword"))
        );
    }


    // VALID PASSWORD RESET TEST

    @Test(priority = 1)
    public void validPasswordResetTest() {

        ResetPasswordPage reset =
                new ResetPasswordPage(driver);
        reset.enterNewPassword("Jay@123");
        reset.enterConfirmPassword("Jay@123");
        reset.clickSubmitButton();

        // Wait for alert
        wait.until(ExpectedConditions.alertIsPresent());

        // Accept alert
        driver.switchTo().alert().accept();

        // Wait for login page
        WebElement loginPage =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.id("username"))
                );

        Assert.assertTrue(
                loginPage.isDisplayed(),
                "Login page not displayed after reset"
        );

        System.out.println("Valid Password Reset Test Passed");
    }

    // PASSWORD MISMATCH TEST

    @Test(priority = 2)
    public void passwordMismatchTest() {

        ResetPasswordPage reset =
                new ResetPasswordPage(driver);
        reset.enterNewPassword("Jay@123");
        reset.enterConfirmPassword("jay@456");
        reset.clickSubmitButton();
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement error =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//*[contains(text(),'Passwords do not match')]"))
                );

        Assert.assertEquals(
                error.getText(),
                "Passwords do not match",
                "Incorrect mismatch validation"
        );

        System.out.println("Password Mismatch Test Passed");
    }


    // EMPTY PASSWORD TEST

    @Test(priority = 3)
    public void emptyPasswordTest() {
        ResetPasswordPage reset =
                new ResetPasswordPage(driver);
        reset.clickSubmitButton();
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement error =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//*[contains(text(),'Password is required')]"))
                );
        Assert.assertEquals(
                error.getText(),
                "Password is required",
                "Incorrect empty password validation"
        );
        System.out.println("Empty Password Test Passed");
    }
}