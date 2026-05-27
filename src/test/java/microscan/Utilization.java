package microscan;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Utilization extends BaseTest {

    @Test
    public void testUtilizationPageLoads() {
        getDriver().findElement(By.xpath("//span[text()='Utilization']")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[text()='Utilization']")));
        Assert.assertTrue(getDriver().getPageSource().contains("Utilization"),
                "Utilization page did not load");
        System.out.println("PASS: Utilization page loaded");
    }

    @Test
    public void testUtilizationMenuVisible() {
        WebElement menu = getDriver().findElement(By.xpath("//span[text()='Utilization']"));
        Assert.assertTrue(menu.isDisplayed(), "Utilization menu not visible");
        System.out.println("PASS: Utilization menu is visible");
    }
}