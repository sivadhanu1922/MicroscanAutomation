package microscan;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LogoutTest extends AuthenticationBaseTest {

    @BeforeMethod
    public void loginAndOpenDashboard() {
        LoginPage login =
                new LoginPage(driver);
        login.enterUsername("Jayasri");
        login.enterPassword("Jayasri@123");
        login.clickLogin();
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
        WebElement logoutButton =
                driver.findElement(
                        By.cssSelector("button[title='Logout']")
                );

        logoutButton.click();
        WebElement loginPage =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.id("username"))
                );

        Assert.assertTrue(
                loginPage.isDisplayed(),
                "Login page not displayed after logout"
        );
        System.out.println("Logout Test Passed");
    }
}