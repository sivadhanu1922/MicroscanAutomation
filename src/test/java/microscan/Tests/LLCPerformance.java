package microscan.Tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LLCPerformance extends BaseTest {

    private static final Logger log = LogManager.getLogger(LLCPerformance.class);

    @Test(priority = 1)
    public void testLLCPerformancePageLoads() {
        getDriver().findElement(By.xpath("//span[text()='LLC Performance']")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//h2[text()='Demo Access']")));
        Assert.assertTrue(getDriver().getPageSource().contains("Demo Access"),
                "LLC Performance page did not load");
        log.info("PASS: LLC Performance page loaded");
    }

    @Test(priority = 2)
    public void testDemoAccessSectionVisible() {
        getDriver().findElement(By.xpath("//span[text()='LLC Performance']")).click();
        WebElement demoAccess = getDriver().findElement(
                By.xpath("//h2[text()='Demo Access']"));
        Assert.assertTrue(demoAccess.isDisplayed(),
                "Demo Access section not visible");
        log.info("PASS: Demo Access section is visible");
    }

    @Test(priority = 3)
    public void testDemoUsernameDisplayed() {
        getDriver().findElement(By.xpath("//span[text()='LLC Performance']")).click();
        WebElement username = getDriver().findElement(
                By.xpath("//input[@value='Demo_LLC']"));
        Assert.assertTrue(username.isDisplayed(),
                "Demo username not displayed");
        log.info("PASS: Demo username Demo_LLC is displayed");
    }

    @Test(priority = 4)
    public void testDemoPasswordDisplayed() {
        getDriver().findElement(By.xpath("//span[text()='LLC Performance']")).click();
        WebElement password = getDriver().findElement(
                By.xpath("//input[@value='MIPL@2025']"));
        Assert.assertTrue(password.isDisplayed(),
                "Demo password not displayed");
        log.info("PASS: Demo password MIPL@2025 is displayed");
    }

    @Test(priority = 5)
    public void testGoToMicroLLCButtonVisible() {
        getDriver().findElement(By.xpath("//span[text()='LLC Performance']")).click();
        WebElement btn = getDriver().findElement(
                By.xpath("//button[text()='Go to Micro LLC']"));
        Assert.assertTrue(btn.isDisplayed(),
                "Go to Micro LLC button not visible");
        log.info("PASS: Go to Micro LLC button is visible");
    }

    @Test(priority = 6)
    public void testGoToMicroLLCButtonClick() {
        getDriver().findElement(By.xpath("//span[text()='LLC Performance']")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Go to Micro LLC']")));
        getDriver().findElement(
                By.xpath("//button[text()='Go to Micro LLC']")).click();
        log.info("PASS: Go to Micro LLC button clicked");
    }

    @Test(priority = 7)
    public void testImportantNotesVisible() {
        getDriver().findElement(By.xpath("//span[text()='LLC Performance']")).click();
        WebElement notes = getDriver().findElement(
                By.xpath("//h3[text()='Important Notes']"));
        Assert.assertTrue(notes.isDisplayed(),
                "Important Notes section not visible");
        log.info("PASS: Important Notes section is visible");
    }

    @Test(priority = 8)
    public void testFullAccessFormVisible() {
        getDriver().findElement(By.xpath("//span[text()='LLC Performance']")).click();
        WebElement formTitle = getDriver().findElement(
                By.xpath("//h2[text()='Interested in Full Access?']"));
        Assert.assertTrue(formTitle.isDisplayed(),
                "Full Access form not visible");
        log.info("PASS: Interested in Full Access form is visible");
    }

    @Test(priority = 9)
    public void testFullAccessFormSubmit() {
        getDriver().findElement(By.xpath("//span[text()='LLC Performance']")).click();

        // Enter Name
        getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("input[placeholder='Enter your name']")));
        getDriver().findElement(
                By.cssSelector("input[placeholder='Enter your name']")).clear();
        getDriver().findElement(
                By.cssSelector("input[placeholder='Enter your name']")).sendKeys("Siva");

        getDriver().findElement(
                By.cssSelector("input[placeholder='Enter your phone']")).clear();
        getDriver().findElement(
                By.cssSelector("input[placeholder='Enter your phone']")).sendKeys("9876543210");

        getDriver().findElement(
                By.cssSelector("input[placeholder='Enter your email']")).clear();
        getDriver().findElement(
                By.cssSelector("input[placeholder='Enter your email']")).sendKeys("siva@test.com");

        getDriver().findElement(
                By.cssSelector("input[placeholder='Enter your company']")).clear();
        getDriver().findElement(
                By.cssSelector("input[placeholder='Enter your company']")).sendKeys("ELCOT");

        getDriver().findElement(
                By.cssSelector("textarea[placeholder='Enter your message']")).clear();
        getDriver().findElement(
                By.cssSelector("textarea[placeholder='Enter your message']")).sendKeys("I am interested in full access");

        getDriver().findElement(By.xpath("//button[text()='Submit']")).click();

        log.info("PASS: Full Access form submitted");

        try {
            getWait().until(ExpectedConditions.alertIsPresent());
            Alert alert = getDriver().switchTo().alert();
            log.info("ALERT MESSAGE: " + alert.getText());
            alert.accept(); // Click OK on alert
            log.info("PASS: Alert accepted successfully");
        } catch (Exception e) {
            log.info("INFO: No alert appeared after form submit");
        }
    }

    @Test(priority = 10)
    public void testSubmitButtonVisible() {
        getDriver().findElement(By.xpath("//span[text()='LLC Performance']")).click();
        WebElement submitBtn = getDriver().findElement(
                By.xpath("//button[text()='Submit']"));
        Assert.assertTrue(submitBtn.isDisplayed(),
                "Submit button not visible");
        log.info("PASS: Submit button is visible");
    }

    @Test(priority = 11)
    public void testSubmitEmptyForm() {
        getDriver().findElement(By.xpath("//span[text()='LLC Performance']")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Submit']")));
        getDriver().findElement(By.xpath("//button[text()='Submit']")).click();

        // Handle Alert if appears
        try {
            getWait().until(ExpectedConditions.alertIsPresent());
            Alert alert = getDriver().switchTo().alert();
            log.info("ALERT MESSAGE: " + alert.getText());
            alert.accept();
            log.info("PASS: Empty form alert accepted");
        } catch (Exception e) {
            log.info("INFO: No alert for empty form submit");
        }
    }
}