package tests;
import base.BaseTest;
import dataprovider.GenericDataProvider;
import listeners.ExtentTestListener;
import org.testng.annotations.Listeners;
import pages.LoginPage;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ExtentLogger;
import utilities.TestResultLogger;

@Listeners(ExtentTestListener.class)
public class LoginTest extends BaseTest {


    // Valid Username & Valid Password
    @Test(
            priority = 1,
            dataProvider = "excelData",
            dataProviderClass = GenericDataProvider.class,
            enabled = true
    )
    public void validLoginTest(String username, String password) {

        TestResultLogger.setScenario("Valid Login");

        TestResultLogger.setTestType("Positive");

        TestResultLogger.setExpected("User should be logged in successfully");

        LoginPage login = new LoginPage(driver);

        login.enterUsername(username);

        login.enterPassword(password);

        login.clickLogin();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement otpField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("otp"))
        );
        ExtentLogger.info("Verifying OTP Page");

        Assert.assertTrue(
                otpField.isDisplayed(),
                "OTP page was not displayed after successful login"
        );
        TestResultLogger.setActual("Valid Login Test Passed");
        ExtentLogger.pass("Valid Login Test Passed");

        System.out.println("Valid Login Test Passed");
    }


    // Verify Login Button Functionality


    @Test(priority = 2, enabled = true)
    public void loginButtonFunctionalityTest() {


        TestResultLogger.setScenario("Login Button Functionality");
        TestResultLogger.setTestType("Positive");

        TestResultLogger.setExpected("Login button should be displayed and enabled");
        WebElement loginButton = driver.findElement(
                By.xpath("//button[@type='submit']")
        );

        Assert.assertTrue(
                loginButton.isDisplayed(),
                "Login button is not displayed"
        );
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Assert.assertTrue(
                loginButton.isEnabled(),
                "Login button is disabled"
        );
        TestResultLogger.setActual("Login button is displayed and enabled");
        System.out.println("Login Button Functionality Test Passed");
    }


    // Verify Password Masking


    @Test(priority = 3, enabled = true)
    public void passwordMaskingTest() {
        TestResultLogger.setScenario("Password Masking");
        TestResultLogger.setTestType("Positive");

        TestResultLogger.setExpected("Password should be masked");
        WebElement passwordField = driver.findElement(
                By.id("password")
        );
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String type = passwordField.getAttribute("type");

        Assert.assertEquals(
                type,
                "password",
                "Password field is not masked"
        );
        TestResultLogger.setActual("Password is masked");


        System.out.println("Password Masking Test Passed");
    }


    // Verify Successful Redirection


    @Test(
            priority = 4,
            dataProvider = "excelData",
            dataProviderClass = GenericDataProvider.class,
            enabled = true

    )
    public void successfulPageRedirectionTest(String username, String password) {

        TestResultLogger.setScenario("Successful Page Redirection");
        TestResultLogger.setTestType("Positive");
        TestResultLogger.setExpected("User should be redirected to OTP page after successful login");
        LoginPage login = new LoginPage(driver);

        login.enterUsername(username);

        login.enterPassword(password);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        login.clickLogin();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement otpField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("otp"))
        );
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(
                otpField.isDisplayed(),
                "User was not redirected to OTP page"
        );
        TestResultLogger.setActual("User redirected to OTP page successfully");
        System.out.println("Successful Redirection Test Passed");
    }

    //Verify Username Field Accepts Input


    @Test(
            priority = 5,
            dataProvider = "excelData",
            dataProviderClass = GenericDataProvider.class,
            enabled = true
    )
    public void usernameFieldInputTest(String username) {
        TestResultLogger.setScenario("Username Field Input");
        TestResultLogger.setTestType("Positive");
        TestResultLogger.setExpected("Username field should accept input");


        LoginPage login = new LoginPage(driver);

        login.enterUsername(username);

        WebElement usernameField = driver.findElement(By.id("username"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(
                usernameField.getAttribute("value"),
                username,
                "Username input failed"
        );
        TestResultLogger.setActual("Username field accepted input successfully");
        System.out.println("Username Input Test Passed");
    }


    //Verify Password Field Accepts Input


    @Test(
            priority = 6,
            dataProvider = "excelData",
            dataProviderClass = GenericDataProvider.class,
            enabled = true
    )
    public void passwordFieldInputTest(String password) {
        TestResultLogger.setScenario("Password Field Input");
        TestResultLogger.setTestType("Positive");
        TestResultLogger.setExpected("Password field should accept input");

        LoginPage login = new LoginPage(driver);

        login.enterPassword(password);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement passwordField = driver.findElement(By.id("password"));

        Assert.assertEquals(
                passwordField.getAttribute("value"),
                password,
                "Password input failed"
        );
        TestResultLogger.setActual("Password field accepted input successfully");

        System.out.println("Password Input Test Passed");
    }

    // Verify Password Visibility Toggle


    @Test(
            priority = 7,
            dataProvider = "excelData",
            dataProviderClass = GenericDataProvider.class,
            enabled = true
    )
    public void passwordVisibilityToggleTest(String password) {
        TestResultLogger.setScenario("Password Visibility Toggle");
        TestResultLogger.setTestType("Positive");

        TestResultLogger.setExpected("Password visibility toggle should work correctly");

        LoginPage login = new LoginPage(driver);

        login.enterPassword(password);

        WebElement eyeButton = driver.findElement(
                By.xpath("//button[@type='button']")
        );

        eyeButton.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String type = driver.findElement(By.id("password"))
                .getAttribute("type");

        Assert.assertEquals(
                type,
                "text",
                "Password visibility toggle failed"
        );

        TestResultLogger.setActual("Password visibility toggle worked correctly");

        System.out.println("Password Visibility Toggle Test Passed");
    }


    //Verify Login Loading Spinner

    @Test(
            priority = 8,
            dataProvider = "excelData",
            dataProviderClass = GenericDataProvider.class,
            enabled = true
    )
    public void loginLoadingSpinnerTest(String username, String password) {
        TestResultLogger.setScenario("Login Loading Spinner");
        TestResultLogger.setTestType("Positive");

        TestResultLogger.setExpected("Loading spinner/text should be displayed after clicking login");

        LoginPage login = new LoginPage(driver);

        login.enterUsername(username);

        login.enterPassword(password);

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

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TestResultLogger.setActual("Loading spinner/text displayed successfully");

        System.out.println("Loading Spinner Test Passed");
    }

    // Negative testCases
    //Empty Username


    @Test(
            priority = 9,
            dataProvider = "excelData",
            dataProviderClass = GenericDataProvider.class,
            enabled = true
    )
    public void emptyUsernameTest(String password) {
        TestResultLogger.setScenario("Empty Username Validation");
        TestResultLogger.setTestType("Negative");

        TestResultLogger.setExpected("Username is required validation message should be displayed");

        LoginPage login = new LoginPage(driver);

        login.enterPassword(password);

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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TestResultLogger.setActual("Username is required validation message displayed successfully");

        System.out.println("Empty Username Test Passed");
    }


    //Empty Password


    @Test(
            priority = 10,
            dataProvider = "excelData",
            dataProviderClass = GenericDataProvider.class,
            enabled = true
    )
    public void emptyPasswordTest(String username) {
        TestResultLogger.setScenario("Empty Password Validation");
        TestResultLogger.setTestType("Negative");
        TestResultLogger.setExpected("Password is required validation message should be displayed");

        LoginPage login = new LoginPage(driver);

        login.enterUsername(username);

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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TestResultLogger.setActual("Password is required validation message displayed successfully");
        System.out.println("Empty Password Test Passed");
    }


    //Empty Username and Password


    @Test(
            priority = 11,
            dataProvider = "excelData",
            dataProviderClass = GenericDataProvider.class,
            enabled = true
    )
    public void emptyUsernameAndPasswordTest(String username, String password) {
        TestResultLogger.setScenario("Empty Username and Password Validation");
        TestResultLogger.setTestType("Negative");

        TestResultLogger.setExpected("Username and Password required validation messages should be displayed");

        LoginPage login = new LoginPage(driver);
        login.enterUsername(username);
        login.enterPassword(password);

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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TestResultLogger.setActual("Username and Password required validation messages displayed successfully");

        System.out.println("Empty Username and Password Test Passed");
    }

    // Password Less Than 6 Characters


    @Test(
            priority = 12,
            dataProvider = "excelData",
            dataProviderClass = GenericDataProvider.class,
            enabled = true
    )
    public void shortPasswordTest(String username, String password) {
        TestResultLogger.setScenario("Short Password Validation");
        TestResultLogger.setTestType("Negative");

        TestResultLogger.setExpected("Password must be at least 6 characters validation message should be displayed");

        LoginPage login = new LoginPage(driver);

        login.enterUsername(username);

        login.enterPassword(password);

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
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TestResultLogger.setActual("Password must be at least 6 characters validation message displayed successfully");
        System.out.println("Short Password Test Passed");
    }


    //Username With Spaces Only

    @Test(
            priority = 13,
            dataProvider = "excelData",
            dataProviderClass = GenericDataProvider.class,
            enabled = true
    )
    public void usernameWithSpacesTest(String username, String password) {
        TestResultLogger.setScenario("Spaces-only Username Validation");
        TestResultLogger.setTestType("Negative");

        TestResultLogger.setExpected("Username is required validation message should be displayed for spaces-only username");

        LoginPage login = new LoginPage(driver);

        login.enterUsername(username);

        login.enterPassword(password);

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
                "Spaces-only username validation failed"
        );
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TestResultLogger.setActual("Username is required validation message displayed successfully for spaces-only username");

        System.out.println("Username With Spaces Test Passed");
    }


    //Very Long Username and Password


    @Test(
            priority = 14,
            dataProvider = "excelData",
            dataProviderClass = GenericDataProvider.class,
            enabled = true
    )
    public void longUsernameAndPasswordValidationTest(String username, String password) {

        TestResultLogger.setScenario("Long Username and Password Validation");
        TestResultLogger.setTestType("Positive");

        TestResultLogger.setExpected(
                "Application should accept long username and long password"
        );

        LoginPage login = new LoginPage(driver);

        login.enterUsername(username);

        login.enterPassword(password);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        login.clickLogin();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement otpField =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.id("otp"))
                );

        Assert.assertTrue(
                otpField.isDisplayed(),
                "Long username and password were not accepted."
        );

        TestResultLogger.setActual(
                "Application accepted long username and long password successfully."
        );

        System.out.println("Long Username and Password Validation Test Passed");
    }
}