package microscan;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SLAReport extends BaseTest {

    @Test
    public void testSLAReportPageLoads() {
        getDriver().findElement(By.xpath("//span[text()='SLA Report']")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[text()='SLA Report']")));
        Assert.assertTrue(getDriver().getPageSource().contains("SLA"),
                "SLA Report page did not load");
        System.out.println("PASS: SLA Report page loaded");
    }

    @Test
    public void testSLAReportMenuVisible() {
        WebElement menu = getDriver().findElement(By.xpath("//span[text()='SLA Report']"));
        Assert.assertTrue(menu.isDisplayed(), "SLA Report menu not visible");
        System.out.println("PASS: SLA Report menu is visible");
    }
}