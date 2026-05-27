package microscan;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ServiceInventory extends BaseTest {

    @Test
    public void testServiceInventoryPageLoads() {
        getDriver().findElement(By.xpath("//span[text()='Service Inventory']")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[text()='Service Inventory']")));
        Assert.assertTrue(getDriver().getPageSource().contains("Service"),
                "Service Inventory page did not load");
        System.out.println("PASS: Service Inventory page loaded");
    }

    @Test
    public void testServiceInventoryMenuVisible() {
        WebElement menu = getDriver().findElement(By.xpath("//span[text()='Service Inventory']"));
        Assert.assertTrue(menu.isDisplayed(), "Service Inventory menu not visible");
        System.out.println("PASS: Service Inventory menu is visible");
    }
}