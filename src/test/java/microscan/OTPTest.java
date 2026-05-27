package microscan;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OTPTest extends AuthenticationBaseTest {


    // LOGIN BEFORE EVERY OTP TEST

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


    @Test(priority = 1)
    public void validOTPTest() {

        OTPLoginPage otpPage =
                new OTPLoginPage(driver);
        otpPage.enterOTP("123456");
        otpPage.clickVerifyButton();
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dashboard =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//*[contains(text(),'Dashboard')]"))
                );

        Assert.assertTrue(
                dashboard.isDisplayed(),
                "Dashboard not displayed after OTP verification"
        );

        System.out.println("Valid OTP Test Passed");
    }


    // EMPTY OTP TEST


    @Test(priority = 2)
    public void emptyOTPTest() {

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

        Assert.assertEquals(
                error.getText(),
                "OTP is required",
                "Incorrect OTP validation message"
        );

        System.out.println("Empty OTP Test Passed");
    }


    // SHORT OTP TEST

    @Test(priority = 3)
    public void shortOTPTest() {

        OTPLoginPage otpPage =
                new OTPLoginPage(driver);
        otpPage.enterOTP("123");
        otpPage.clickVerifyButton();
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement error =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//*[contains(text(),'Please enter 6 digit OTP')]"))
                );
        Assert.assertEquals(
                error.getText(),
                "Please enter 6 digit OTP",
                "Incorrect OTP validation"
        );

        System.out.println("Short OTP Test Passed");
    }


    // RESEND OTP BUTTON TEST


    @Test(priority = 4)
    public void resendOTPButtonTest() {
        WebElement resendButton =
                driver.findElement(
                        By.xpath("//button[contains(text(),'Resend OTP')]")
                );
        Assert.assertTrue(
                resendButton.isDisplayed(),
                "Resend OTP button not displayed"
        );
        Assert.assertTrue(
                resendButton.isEnabled(),
                "Resend OTP button is disabled"
        );

        System.out.println("Resend OTP Button Test Passed");
    }
}