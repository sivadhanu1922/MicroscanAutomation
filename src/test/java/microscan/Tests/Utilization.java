package microscan.Tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Utilization extends BaseTest {

    private static final Logger log = LogManager.getLogger(Utilization.class);

    @Test
    public void testUtilizationPageLoads() {
        getDriver().findElement(By.xpath("//span[text()='Utilization']")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[text()='Utilization']")));
        Assert.assertTrue(getDriver().getPageSource().contains("Utilization"),
                "Utilization page did not load");
        log.info("PASS: Utilization page loaded");
    }

    @Test
    public void testUtilizationMenuVisible() {
        WebElement menu = getDriver().findElement(By.xpath("//span[text()='Utilization']"));
        Assert.assertTrue(menu.isDisplayed(), "Utilization menu not visible");
        log.info("PASS: Utilization menu is visible");
    }
}