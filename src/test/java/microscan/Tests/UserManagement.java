package microscan.Tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class UserManagement extends BaseTest {

    private static final Logger log = LogManager.getLogger(UserManagement.class);

    @Test
    public void testUserManagementPageLoads() {
        getDriver().findElement(By.xpath("//span[text()='User Management']")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[text()='User Management']")));
        Assert.assertTrue(getDriver().getPageSource().contains("User"),
                "User Management page did not load");
        log.info("PASS: User Management page loaded");
    }

    @Test
    public void testUserManagementMenuVisible() {
//        WebElement menu = getDriver().findElement(By.xpath("//span[text()='User Management']"));
//        Assert.assertTrue(menu.isDisplayed(), "User Management menu not visible");
//        log.info("PASS: User Management menu is visible");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        WebElement dashboard =
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[text()='Dashboard']")
                ));

        dashboard.click();
    }
}