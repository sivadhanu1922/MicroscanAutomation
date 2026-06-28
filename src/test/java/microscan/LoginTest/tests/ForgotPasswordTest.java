package tests;

import base.BaseTest;
import dataprovider.GenericDataProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ForgotPasswordPage;
import utilities.TestResultLogger;

import java.time.Duration;

public class ForgotPasswordTest extends BaseTest {

    @BeforeMethod
    public void openForgotPasswordPage() {

        ForgotPasswordPage forgot =
                new ForgotPasswordPage(driver);

        forgot.clickForgotPasswordLink();
    }

    // VALID USERNAME TEST

    @Test(
            priority = 1,
            dataProvider = "excelData",
            dataProviderClass = GenericDataProvider.class
    )
    public void validUsernameTest(String username) {

        TestResultLogger.setScenario("Valid Username Test");
        TestResultLogger.setTestType("Positive");
        TestResultLogger.setExpected("Reset Password page should be displayed");

        ForgotPasswordPage forgot =
                new ForgotPasswordPage(driver);

        forgot.enterUsername(username);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        TestResultLogger.setActual("Reset Password page is displayed");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Valid Username Test Passed");
    }

    // EMPTY USERNAME TEST

    @Test(
            priority = 2,
            dataProvider = "excelData",
            dataProviderClass = GenericDataProvider.class
    )
    public void emptyUsernameTest(String username) {

        TestResultLogger.setScenario("Empty Username Test");
        TestResultLogger.setTestType("Negative");
        TestResultLogger.setExpected("Validation message should be displayed");

        ForgotPasswordPage forgot =
                new ForgotPasswordPage(driver);

        // Username will be blank from Excel
        forgot.enterUsername(username);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Username entered: " + username);

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
        TestResultLogger.setActual("Validation message is displayed");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Empty Username Test Passed");
    }

    // BACK TO LOGIN TEST

    @Test(priority = 3)
    public void backToLoginTest() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TestResultLogger.setScenario("Back To Login Test");
        TestResultLogger.setTestType("Positive");
        TestResultLogger.setExpected("Login page should be displayed");

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
        TestResultLogger.setActual("Login page is displayed");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Back To Login Test Passed");
    }
}