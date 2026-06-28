package tests;

import base.BaseTest;
import dataprovider.GenericDataProvider;
import pages.LoginPage;
import pages.OTPLoginPage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.TestResultLogger;


public class LogoutTest extends BaseTest {

    @BeforeMethod
    public void loginAndOpenDashboard() {

        LoginPage login =
                new LoginPage(driver);

        login.enterUsername("Jayasri");

        login.enterPassword("Jayasri@123");

        login.clickLogin();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("otp"))
        );

        OTPLoginPage otp =
                new OTPLoginPage(driver);

        otp.enterOTP("123456");

        otp.clickVerifyButton();

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("button[title='Logout']")
                )
        );
    }


    // LOGOUT TEST

    @Test
    public void logoutTest() {

        TestResultLogger.setScenario("Logout Test");
        TestResultLogger.setTestType("Positive");
        TestResultLogger.setExpected("Login page should be displayed after logout");

        WebElement logoutButton =
                driver.findElement(
                        By.cssSelector("button[title='Logout']")
                );

        logoutButton.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement loginPage =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.id("username"))
                );

        Assert.assertTrue(
                loginPage.isDisplayed(),
                "Login page not displayed after logout"
        );
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TestResultLogger.setActual("Login page is displayed after logout");
        System.out.println("Logout Test Passed");
    }
}