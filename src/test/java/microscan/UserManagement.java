package microscan;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserManagement extends BaseTest {

    @Test
    public void testUserManagementPageLoads() {
        getDriver().findElement(By.xpath("//span[text()='User Management']")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[text()='User Management']")));
        Assert.assertTrue(getDriver().getPageSource().contains("User"),
                "User Management page did not load");
        System.out.println("PASS: User Management page loaded");
    }

    @Test
    public void testUserManagementMenuVisible() {
        WebElement menu = getDriver().findElement(By.xpath("//span[text()='User Management']"));
        Assert.assertTrue(menu.isDisplayed(), "User Management menu not visible");
        System.out.println("PASS: User Management menu is visible");
    }
}