package microscan.Tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ServiceInventory extends BaseTest {

    private static final Logger log = LogManager.getLogger(ServiceInventory.class);

    @Test
    public void testServiceInventoryPageLoads() {
        getDriver().findElement(By.xpath("//span[text()='Service Inventory']")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[text()='Service Inventory']")));
        Assert.assertTrue(getDriver().getPageSource().contains("Service"),
                "Service Inventory page did not load");
        log.info("PASS: Service Inventory page loaded");
    }

    @Test
    public void testServiceInventoryMenuVisible() {
        WebElement menu = getDriver().findElement(By.xpath("//span[text()='Service Inventory']"));
        Assert.assertTrue(menu.isDisplayed(), "Service Inventory menu not visible");
        log.info("PASS: Service Inventory menu is visible");
    }
}