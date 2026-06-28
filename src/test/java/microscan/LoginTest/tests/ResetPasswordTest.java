package tests;

import base.BaseTest;
import dataprovider.GenericDataProvider;
import pages.ForgotPasswordPage;
import pages.ResetPasswordPage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.TestResultLogger;

public class ResetPasswordTest extends BaseTest {

    @BeforeMethod
    public void openResetPasswordPage() {

        ForgotPasswordPage forgot =
                new ForgotPasswordPage(driver);

        forgot.clickForgotPasswordLink();

        forgot.enterUsername("jayasri");

        forgot.clickSubmitButton();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("newPassword"))
        );
    }


    // VALID PASSWORD RESET TEST

    @Test(priority = 1, dataProvider = "excelData",
    dataProviderClass = GenericDataProvider .class
    )
    public void validPasswordResetTest(String newPassword, String confirmPassword) {
        TestResultLogger.setScenario("Valid Password Reset Test");
        TestResultLogger.setTestType("Positive");
        TestResultLogger.setExpected("User should be able to reset password successfully");

        ResetPasswordPage reset =
                new ResetPasswordPage(driver);

        reset.enterNewPassword(newPassword);

        reset.enterConfirmPassword(confirmPassword);

        reset.clickSubmitButton();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

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
        TestResultLogger.setActual("Password reset successful, login page displayed");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Valid Password Reset Test Passed");
    }

    // PASSWORD MISMATCH TEST

    @Test(priority = 2, dataProvider = "excelData",
    dataProviderClass = GenericDataProvider.class)
    public void passwordMismatchTest(String newPassword, String confirmPassword) {
        TestResultLogger.setScenario("Password Mismatch Test");
        TestResultLogger.setTestType("Negative");
        TestResultLogger.setExpected("Validation message should be displayed for password mismatch");

        ResetPasswordPage reset =
                new ResetPasswordPage(driver);

        reset.enterNewPassword(newPassword);

        reset.enterConfirmPassword(confirmPassword);

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
        TestResultLogger.setActual("Validation message displayed for password mismatch");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Password Mismatch Test Passed");
    }


    // EMPTY PASSWORD TEST


    @Test(priority = 3)
    public void emptyPasswordTest() {
        TestResultLogger.setScenario("Empty Password Test");
        TestResultLogger.setTestType("Negative");
        TestResultLogger.setExpected("Validation message should be displayed for empty password");

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
        TestResultLogger.setActual("Validation message displayed for empty password");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Empty Password Test Passed");
    }

    @Test(priority = 4, dataProvider = "excelData",
            dataProviderClass = GenericDataProvider.class)
    public void emptyConfirmPasswordTest(String ConfirmPassword) {

        TestResultLogger.setScenario("Empty Confirm Password Test");
        TestResultLogger.setTestType("Negative");
        TestResultLogger.setExpected("Validation message should be displayed for empty confirm password");

        ResetPasswordPage reset = new ResetPasswordPage(driver);

        reset.enterNewPassword(ConfirmPassword);
        reset.clickSubmitButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement error = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Please confirm your password')]"))
        );

        Assert.assertEquals(
                error.getText(),
                "Please confirm your password"
        );

        TestResultLogger.setActual("Validation displayed for empty confirm password");

        System.out.println("Empty Confirm Password Test Passed");
    }

    @Test(priority = 5)
    public void bothFieldsEmptyTest() {

        TestResultLogger.setScenario("Both Fields Empty Test");
        TestResultLogger.setTestType("Negative");
        TestResultLogger.setExpected("Validation messages should be displayed");

        ResetPasswordPage reset = new ResetPasswordPage(driver);

        reset.clickSubmitButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement passwordError = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Password is required')]"))
        );

        WebElement confirmError = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Please confirm your password')]"))
        );

        Assert.assertTrue(passwordError.isDisplayed());
        Assert.assertTrue(confirmError.isDisplayed());

        TestResultLogger.setActual("Required field validations displayed");

        System.out.println("Both Fields Empty Test Passed");
    }
    @Test(priority = 6, dataProvider = "excelData",
            dataProviderClass = GenericDataProvider.class)
    public void passwordVisibilityToggleTest(String NewPassword) {

        TestResultLogger.setScenario("Password Visibility Toggle");
        TestResultLogger.setTestType("Positive");
        TestResultLogger.setExpected("Password should be visible after clicking eye icon");

        ResetPasswordPage reset = new ResetPasswordPage(driver);

        reset.enterNewPassword(NewPassword);

        WebElement passwordField = driver.findElement(By.id("newPassword"));

        Assert.assertEquals(passwordField.getAttribute("type"), "password");

        driver.findElement(By.xpath("(//*[name()='svg'])[1]")).click();

        Assert.assertEquals(passwordField.getAttribute("type"), "text");

        TestResultLogger.setActual("Password visibility toggled successfully");

        System.out.println("Password Visibility Toggle Test Passed");
    }
    @Test(priority = 7)
    public void passwordGuidelinesTest() {

        TestResultLogger.setScenario("Password Guidelines Display");
        TestResultLogger.setTestType("Positive");
        TestResultLogger.setExpected("Password guidelines should be visible");

        Assert.assertTrue(driver.getPageSource().contains("Password Creation Guidelines"));
        Assert.assertTrue(driver.getPageSource().contains("Password must be 6 to 10 characters long"));
        Assert.assertTrue(driver.getPageSource().contains("Must include at least one number"));
        Assert.assertTrue(driver.getPageSource().contains("Must include at least one special character"));
        Assert.assertTrue(driver.getPageSource().contains("Password cannot match the username"));

        TestResultLogger.setActual("Password guidelines displayed");

        System.out.println("Password Guidelines Test Passed");
    }

}