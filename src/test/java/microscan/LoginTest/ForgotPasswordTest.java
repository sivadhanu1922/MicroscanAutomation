package microscan.LoginTest;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ForgotPasswordTest extends AuthenticationBaseTest {

    @BeforeMethod
    public void openForgotPasswordPage() {
        ForgotPasswordPage forgot =
                new ForgotPasswordPage(driver);

        forgot.clickForgotPasswordLink();
    }

    // VALID USERNAME TEST

    @Test(priority = 1)
    public void validUsernameTest() {

        ForgotPasswordPage forgot =
                new ForgotPasswordPage(driver);
        forgot.enterUsername("Jayasri");
        forgot.clickSubmitButton();
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement resetPage =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.id("newPassword"))
                );
        Assert.assertTrue(
                resetPage.isDisplayed(),
                "Reset Password page not displayed"
        );
        System.out.println("Valid Username Test Passed");
    }


    // EMPTY USERNAME TEST

    @Test(priority = 2)
    public void emptyUsernameTest() {
        ForgotPasswordPage forgot =
                new ForgotPasswordPage(driver);
        forgot.clickSubmitButton();
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement error =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//*[contains(text(),'Username or email is required')]")
                        )
                );
        Assert.assertEquals(
                error.getText(),
                "Username or email is required",
                "Incorrect validation message"
        );
        System.out.println("Empty Username Test Passed");
    }


    // BACK TO LOGIN TEST

    @Test(priority = 3)
    public void backToLoginTest() {

        ForgotPasswordPage forgot =
                new ForgotPasswordPage(driver);
        forgot.clickBackToLoginButton();
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loginPage =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.id("username"))
                );
        Assert.assertTrue(
                loginPage.isDisplayed(),
                "Login page not displayed"
        );
        System.out.println("Back To Login Test Passed");
    }
}