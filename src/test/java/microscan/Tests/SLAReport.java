package microscan.Tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SLAReport extends BaseTest {

    private void goToSLAReport() {
        getDriver().findElement(By.xpath("//span[text()='SLA Report']")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[text()='Period']")));
    }

    @Test(priority = 1)
    public void testSLAReportPageLoads() {
        goToSLAReport();
        Assert.assertTrue(getDriver().getPageSource().contains("SLA Report"),
                "SLA Report page did not load");
        System.out.println("PASS: SLA Report page loaded");
    }

    @Test(priority = 2)
    public void testPeriodDropdownVisible() {
        goToSLAReport();
        WebElement period = getDriver().findElement(By.xpath("(//select)[1]"));
        Assert.assertTrue(period.isDisplayed(), "Period dropdown not visible");
        System.out.println("PASS: Period dropdown is visible");
    }

    @Test(priority = 3)
    public void testSelectPeriodMonthly() {
        goToSLAReport();
        Select period = new Select(getDriver().findElement(By.xpath("(//select)[1]")));
        period.selectByVisibleText("Monthly");
        Assert.assertEquals(period.getFirstSelectedOption().getText(), "Monthly");
        System.out.println("PASS: Period selected as Monthly");
    }

    @Test(priority = 4)
    public void testSelectPeriodQuarterly() {
        goToSLAReport();
        Select period = new Select(getDriver().findElement(By.xpath("(//select)[1]")));
        period.selectByVisibleText("Quarterly");
        Assert.assertEquals(period.getFirstSelectedOption().getText(), "Quarterly");
        System.out.println("PASS: Period selected as Quarterly");
    }

    @Test(priority = 5)
    public void testMonthlySecondDropdownVisible() {
        goToSLAReport();
        Select period = new Select(getDriver().findElement(By.xpath("(//select)[1]")));
        period.selectByVisibleText("Monthly");

        // Second dropdown is a button for Monthly
        getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[text()='Monthly']/following-sibling::button")));
        WebElement btn = getDriver().findElement(
                By.xpath("//label[text()='Monthly']/following-sibling::button"));
        Assert.assertTrue(btn.isDisplayed(), "Monthly second dropdown button not visible");
        System.out.println("PASS: Monthly second dropdown is visible");
    }


    @Test(priority = 6)
    public void testQuarterlyDropdownVisible() {
        goToSLAReport();
        Select period = new Select(getDriver().findElement(By.xpath("(//select)[1]")));
        period.selectByVisibleText("Quarterly");

        // Quarterly button should appear
        getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[text()='Quarterly']/following-sibling::button")));
        WebElement btn = getDriver().findElement(
                By.xpath("//label[text()='Quarterly']/following-sibling::button"));
        Assert.assertTrue(btn.isDisplayed(), "Quarterly dropdown button not visible");
        System.out.println("PASS: Quarterly dropdown button is visible");
    }

    @Test(priority = 7)
    public void testYearDropdownVisible() {
        goToSLAReport();
        Select period = new Select(getDriver().findElement(By.xpath("(//select)[1]")));
        period.selectByVisibleText("Quarterly");

        // Year button should appear
        getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[text()='Year']/following-sibling::button")));
        WebElement btn = getDriver().findElement(
                By.xpath("//label[text()='Year']/following-sibling::button"));
        Assert.assertTrue(btn.isDisplayed(), "Year dropdown button not visible");
        System.out.println("PASS: Year dropdown button is visible");
    }

    @Test(priority = 8)
    public void testSubmitButtonVisible() {
        goToSLAReport();
        WebElement submitBtn = getDriver().findElement(
                By.xpath("//button[text()='Submit']"));
        Assert.assertTrue(submitBtn.isDisplayed(), "Submit button not visible");
        System.out.println("PASS: Submit button is visible");
    }

    @Test(priority = 9)
    public void testDownloadAsPDF() {
        goToSLAReport();
        getWait().until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[title='Download']")));
        getDriver().findElement(By.cssSelector("button[title='Download']")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[text()='PDF']")));
        getDriver().findElement(By.xpath("//*[text()='PDF']")).click();
        System.out.println("PASS: PDF selected");
        try {
            getWait().until(ExpectedConditions.alertIsPresent());
            Alert alert = getDriver().switchTo().alert();
            System.out.println("ALERT: " + alert.getText());
            alert.accept();
            System.out.println("PASS: Alert accepted after PDF");
        } catch (Exception e) {
            System.out.println("INFO: No alert after PDF");
        }
    }

    @Test(priority = 10)
    public void testDownloadAsCSV() {
        goToSLAReport();
        getWait().until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[title='Download']")));
        getDriver().findElement(By.cssSelector("button[title='Download']")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[text()='CSV']")));
        getDriver().findElement(By.xpath("//*[text()='CSV']")).click();
        System.out.println("PASS: CSV selected");
        try {
            getWait().until(ExpectedConditions.alertIsPresent());
            Alert alert = getDriver().switchTo().alert();
            System.out.println("ALERT: " + alert.getText());
            alert.accept();
            System.out.println("PASS: Alert accepted after CSV");
        } catch (Exception e) {
            System.out.println("INFO: No alert after CSV");
        }
    }

    @Test(priority = 11)
    public void testDownloadAsXLS() {
        goToSLAReport();
        getWait().until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[title='Download']")));
        getDriver().findElement(By.cssSelector("button[title='Download']")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[text()='XLS']")));
        getDriver().findElement(By.xpath("//*[text()='XLS']")).click();
        System.out.println("PASS: XLS selected");
        try {
            getWait().until(ExpectedConditions.alertIsPresent());
            Alert alert = getDriver().switchTo().alert();
            System.out.println("ALERT: " + alert.getText());
            alert.accept();
            System.out.println("PASS: Alert accepted after XLS");
        } catch (Exception e) {
            System.out.println("INFO: No alert after XLS");
        }
    }
}