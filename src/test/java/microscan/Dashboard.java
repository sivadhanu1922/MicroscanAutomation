package microscan;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Dashboard extends BaseTest {

    @Test
    public void testDashboardPageLoads() {
        getDriver().findElement(By.xpath("//span[text()='Dashboard']")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[text()='Dashboard']")));
        Assert.assertTrue(getDriver().getPageSource().contains("Dashboard"),
                "Dashboard page did not load");
        System.out.println("PASS: Dashboard page loaded successfully");
    }

    @Test
    public void testDashboardMenuVisible() {
        WebElement menu = getDriver().findElement(By.xpath("//span[text()='Dashboard']"));
        Assert.assertTrue(menu.isDisplayed(), "Dashboard menu not visible");
        System.out.println("PASS: Dashboard menu is visible");
    }
}