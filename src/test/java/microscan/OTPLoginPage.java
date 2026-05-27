package microscan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OTPLoginPage {

    WebDriver driver;

    public OTPLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators

    By otpField = By.id("otp");
    By verifyButton =
            By.xpath("//button[contains(text(),'Verify')]");
    By resendOTPButton =
            By.xpath("//button[contains(text(),'Resend OTP')]");

    // Methods

    public void enterOTP(String otp) {
        driver.findElement(otpField)
                .sendKeys(otp);
    }

    public void clickVerifyButton() {
        driver.findElement(verifyButton)
                .click();
    }

    public void clickResendOTPButton() {
        driver.findElement(resendOTPButton)
                .click();
    }
}