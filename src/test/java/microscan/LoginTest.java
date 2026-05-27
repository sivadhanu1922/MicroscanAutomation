package microscan;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends AuthenticationBaseTest {


    // Valid Username & Valid Password

    @Test(priority = 1)
    public void validLoginTest() {

        LoginPage login = new LoginPage(driver);
        login.enterUsername("Jayasri");
        login.enterPassword("Jayasri@123");
        login.clickLogin();
        WebElement otpField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("otp"))
        );
        Assert.assertTrue(
                otpField.isDisplayed(),
                "OTP page was not displayed after successful login"
        );
        System.out.println("Valid Login Test Passed");
    }


    // Verify Login Button Functionality


    @Test(priority = 2)
    public void loginButtonFunctionalityTest() {
        WebElement loginButton = driver.findElement(
                By.xpath("//button[@type='submit']")
        );
        Assert.assertTrue(
                loginButton.isDisplayed(),
                "Login button is not displayed"
        );
        Assert.assertTrue(
                loginButton.isEnabled(),
                "Login button is disabled"
        );
        System.out.println("Login Button Functionality Test Passed");
    }


    // Verify Password Masking

    @Test(priority = 3)
    public void passwordMaskingTest() {

        WebElement passwordField = driver.findElement(
                By.id("password")
        );
        String type = passwordField.getAttribute("type");
        Assert.assertEquals(
                type,
                "password",
                "Password field is not masked"
        );
        System.out.println("Password Masking Test Passed");
    }


    // Verify Successful Redirection

    @Test(priority = 4)
    public void successfulPageRedirectionTest() {

        LoginPage login = new LoginPage(driver);
        login.enterUsername("Jayasri");
        login.enterPassword("Jayasri@123");
        login.clickLogin();
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement otpField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("otp"))
        );
        Assert.assertTrue(
                otpField.isDisplayed(),
                "User was not redirected to OTP page"
        );
        System.out.println("Successful Redirection Test Passed");
    }

    //Verify Username Field Accepts Input


    @Test(priority = 5)
    public void usernameFieldInputTest() {
        WebElement username = driver.findElement(
                By.id("username")
        );
        username.sendKeys("Jayasri");
        Assert.assertEquals(
                username.getAttribute("value"),
                "Jayasri",
                "Username input failed"
        );
        System.out.println("Username Input Test Passed");
    }


    //Verify Password Field Accepts Input


    @Test(priority = 6)
    public void passwordFieldInputTest() {
        WebElement password = driver.findElement(
                By.id("password")
        );
        password.sendKeys("Jayasri@123");
        Assert.assertEquals(
                password.getAttribute("value"),
                "Jayasri@123",
                "Password input failed"
        );
        System.out.println("PTC06 - Password Input Test Passed");
    }


    // Verify Password Visibility Toggle


    @Test(priority = 7)
    public void passwordVisibilityToggleTest() {
        driver.findElement(By.id("password"))
                .sendKeys("Jayasri@123");

        WebElement eyeButton = driver.findElement(
                By.xpath("//button[@type='button']")
        );
        eyeButton.click();

        String type = driver.findElement(By.id("password"))
                .getAttribute("type");
        Assert.assertEquals(
                type,
                "text",
                "Password visibility toggle failed"
        );
        System.out.println("Password Visibility Toggle Test Passed");
    }


    //Verify Login Loading Spinner

    @Test(priority = 8)
    public void loginLoadingSpinnerTest() {

        LoginPage login = new LoginPage(driver);
        login.enterUsername("Jayasri");
        login.enterPassword("Jayasri@123");
        login.clickLogin();
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loadingText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Logging in...')]"))
        );

        Assert.assertTrue(
                loadingText.isDisplayed(),
                "Loading spinner/text not displayed"
        );
        System.out.println("Loading Spinner Test Passed");
    }

     // Negative testCases
    //Empty Username


    @Test(priority = 9)
    public void emptyUsernameTest() {

        LoginPage login = new LoginPage(driver);
        login.enterPassword("Jayasri@123");
        login.clickLogin();
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Username is required')]"))
        );

        Assert.assertEquals(
                errorMessage.getText(),
                "Username is required",
                "Incorrect username validation message"
        );

        System.out.println("Empty Username Test Passed");
    }


    //Empty Password

    @Test(priority = 10)
    public void emptyPasswordTest() {

        LoginPage login = new LoginPage(driver);
        login.enterUsername("Jayasri");
        login.clickLogin();
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Password is required')]"))
        );
        Assert.assertEquals(
                errorMessage.getText(),
                "Password is required",
                "Incorrect password validation message"
        );
        System.out.println("Empty Password Test Passed");
    }


    //Empty Username and Password


    @Test(priority = 11)
    public void emptyUsernameAndPasswordTest() {
        LoginPage login = new LoginPage(driver);
        login.clickLogin();
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement usernameError = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Username is required')]"))
        );
        WebElement passwordError = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Password is required')]"))
        );
        Assert.assertTrue(
                usernameError.isDisplayed(),
                "Username validation not displayed"
        );
        Assert.assertTrue(
                passwordError.isDisplayed(),
                "Password validation not displayed"
        );
        System.out.println("Empty Username and Password Test Passed");
    }

    // Password Less Than 6 Characters


    @Test(priority = 12)
    public void shortPasswordTest() {

        LoginPage login = new LoginPage(driver);
        login.enterUsername("Jayasri");
        login.enterPassword("123");
        login.clickLogin();
        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Password must be at least 6 characters')]"))
        );

        Assert.assertEquals(
                errorMessage.getText(),
                "Password must be at least 6 characters",
                "Incorrect password length validation"
        );
        System.out.println("Short Password Test Passed");
    }


    //Username With Spaces Only

    @Test(priority = 13)
    public void usernameWithSpacesTest() {

        LoginPage login = new LoginPage(driver);
        login.enterUsername("     ");
        login.enterPassword("Jayasri@123");
        login.clickLogin();
        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'Username is required')]"))
        );
        Assert.assertEquals(
                errorMessage.getText(),
                "Username is required",
                "Spaces-only username validation failed"
        );
        System.out.println("Username With Spaces Test Passed");
    }


    //Very Long Username


    @Test(priority = 14)
    public void longUsernameTest() {
        LoginPage login = new LoginPage(driver);
        String longUsername =
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        login.enterUsername(longUsername);
        String enteredValue = driver.findElement(By.id("username"))
                .getAttribute("value");
        Assert.assertEquals(
                enteredValue,
                longUsername,
                "Long username input failed"
        );

        System.out.println("Long Username Test Passed");
    }


    //Very Long Password


    @Test(priority = 15)
    public void longPasswordTest() {

        LoginPage login = new LoginPage(driver);
        String longPassword =
                "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
        login.enterPassword(longPassword);
        String enteredValue = driver.findElement(By.id("password"))
                .getAttribute("value");
        Assert.assertEquals(
                enteredValue,
                longPassword,
                "Long password input failed"
        );

        System.out.println("Long Password Test Passed");
    }
}