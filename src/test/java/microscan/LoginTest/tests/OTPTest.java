package tests;

import base.BaseTest;
import dataprovider.GenericDataProvider;
import pages.LoginPage;
import pages.OTPLoginPage;

import java.sql.SQLOutput;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.asserts.SoftAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.TestResultLogger;

public class OTPTest extends BaseTest {


    // LOGIN BEFORE EVERY OTP TEST
   SoftAssert softassert = new SoftAssert();

    @BeforeMethod
    public void loginToOTPPage() {

        LoginPage login = new LoginPage(driver);

        login.enterUsername("Jayasri");

        login.enterPassword("Jayasri@123");

        login.clickLogin();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("otp"))
        );
    }


    // VALID OTP TEST


    @Test(
            priority = 1,
            dataProvider = "excelData",
            dataProviderClass = GenericDataProvider.class
    )
    public void validOTPTest(String otp) {



        TestResultLogger.setScenario("Valid OTP Test");
        TestResultLogger.setTestType("Positive");
        TestResultLogger.setExpected("Dashboard should be displayed after OTP verification");

        OTPLoginPage otpPage =
                new OTPLoginPage(driver);

        otpPage.enterOTP(otp);

        otpPage.clickVerifyButton();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement dashboard =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//*[contains(text(),'Dashboard')]"))
                );

        softassert.assertTrue(
                dashboard.isDisplayed(),
                "Dashboard not displayed after OTP verification"
        );
        TestResultLogger.setActual("Dashboard displayed after OTP verification");

        System.out.println("Valid OTP Test Passed");
        softassert.assertAll();
    }


    // EMPTY OTP TEST


    @Test(
            priority = 2
    )
    public void emptyOTPTest() {
        TestResultLogger.setScenario("Empty OTP Test");
        TestResultLogger.setTestType("Negative");
        TestResultLogger.setExpected("Validation message should be displayed for empty OTP");

        OTPLoginPage otpPage =
                new OTPLoginPage(driver);

        otpPage.clickVerifyButton();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement error =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//*[contains(text(),'OTP is required')]"))
                );

        softassert.assertEquals(
                error.getText(),
                "OTP is required",
                "Incorrect OTP validation message"
        );
        TestResultLogger.setActual("Validation message displayed for empty OTP");
        System.out.println("Empty OTP Test Passed");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        softassert.assertAll();
    }


    // SHORT OTP TEST

    @Test(
            priority = 3,
            dataProvider = "excelData",
            dataProviderClass = GenericDataProvider.class
    )
    public void shortOTPTest(String otp) {
        TestResultLogger.setScenario("Short OTP Test");
        TestResultLogger.setTestType("Negative");
        TestResultLogger.setExpected("Validation message should be displayed for short OTP");

        OTPLoginPage otpPage =
                new OTPLoginPage(driver);

        otpPage.enterOTP(otp);

        otpPage.clickVerifyButton();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement error =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//*[contains(text(),'Please enter 6 digit OTP')]"))
                );

        softassert.assertEquals(
                error.getText(),
                "Please enter 6 digit OTP",
                "Incorrect OTP validation"
        );
        TestResultLogger.setActual("Validation message displayed for short OTP");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Short OTP Test Passed");
        softassert.assertAll();
    }


    // RESEND OTP BUTTON TEST


    @Test(priority = 4)
    public void resendOTPButtonTest() {
        TestResultLogger.setScenario("Resend OTP Button Test");
        TestResultLogger.setTestType("Positive");
        TestResultLogger.setExpected("Resend OTP button should be displayed and enabled");

        WebElement resendButton =
                driver.findElement(
                        By.xpath("//button[contains(text(),'Resend OTP')]")
                );

        softassert.assertTrue(
                resendButton.isDisplayed(),
                "Resend OTP button not displayed"
        );

        softassert.assertTrue(
                resendButton.isEnabled(),
                "Resend OTP button is disabled"
        );
        TestResultLogger.setActual("Resend OTP button is displayed and enabled");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Resend OTP Button Test Passed");
        softassert.assertAll();
    }
}