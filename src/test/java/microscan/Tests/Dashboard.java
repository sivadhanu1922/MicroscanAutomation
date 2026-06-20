package microscan.Tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Dashboard extends BaseTest {

    private static final Logger log = LogManager.getLogger(Dashboard.class);

    @Test(priority = 1)
    public void testDashboardPageLoads() {
        getDriver().findElement(By.xpath("//span[text()='Dashboard']")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[text()='Dashboard']")));
        Assert.assertTrue(getDriver().getPageSource().contains("Dashboard"),
                "Dashboard page did not load");
        log.info("PASS: Dashboard page loaded successfully");
    }

    @Test(priority = 2)
    public void testActiveServicesCardVisible() {
        getDriver().findElement(By.xpath("//span[text()='Dashboard']")).click();
        WebElement activeServices = getDriver().findElement(
                By.xpath("//p[text()='Active Services']"));
        Assert.assertTrue(activeServices.isDisplayed(),
                "Active Services card not visible");
       log.info("PASS: Active Services card is visible");
    }

    @Test(priority = 3)
    public void testActiveServicesCount() {
        getDriver().findElement(By.xpath("//span[text()='Dashboard']")).click();
        WebElement count = getDriver().findElement(
                By.xpath("//p[text()='Active Services']/following-sibling::p"));
        Assert.assertEquals(count.getText(), "20",
                "Active Services count mismatch");
       log.info("PASS: Active Services count is 20");
    }

    @Test(priority = 4)
    public void testWIPOrdersCardVisible() {
        getDriver().findElement(By.xpath("//span[text()='Dashboard']")).click();
        WebElement wipOrders = getDriver().findElement(
                By.xpath("//p[text()='WIP Orders']"));
        Assert.assertTrue(wipOrders.isDisplayed(),
                "WIP Orders card not visible");
       log.info("PASS: WIP Orders card is visible");
    }

    @Test(priority = 5)
    public void testWIPOrdersCount() {
        getDriver().findElement(By.xpath("//span[text()='Dashboard']")).click();
        WebElement count = getDriver().findElement(
                By.xpath("//p[text()='WIP Orders']/following-sibling::p"));
        Assert.assertEquals(count.getText(), "7",
                "WIP Orders count mismatch");
       log.info("PASS: WIP Orders count is 7");
    }

    @Test(priority = 6)
    public void testTerminatedServicesCardVisible() {
        getDriver().findElement(By.xpath("//span[text()='Dashboard']")).click();
        WebElement terminated = getDriver().findElement(
                By.xpath("//p[text()='Terminated Services']"));
        Assert.assertTrue(terminated.isDisplayed(),
                "Terminated Services card not visible");
       log.info("PASS: Terminated Services card is visible");
    }

    @Test(priority = 7)
    public void testTerminatedServicesCount() {
        getDriver().findElement(By.xpath("//span[text()='Dashboard']")).click();
        WebElement count = getDriver().findElement(
                By.xpath("//p[text()='Terminated Services']/following-sibling::p"));
        Assert.assertEquals(count.getText(), "7",
                "Terminated Services count mismatch");
       log.info("PASS: Terminated Services count is 7");
    }

    @Test(priority = 8)
    public void testOpenTicketsCardVisible() {
        getDriver().findElement(By.xpath("//span[text()='Dashboard']")).click();
        WebElement openTickets = getDriver().findElement(
                By.xpath("//p[text()='Open Tickets']"));
        Assert.assertTrue(openTickets.isDisplayed(),
                "Open Tickets card not visible");
       log.info("PASS: Open Tickets card is visible");
    }

    @Test(priority = 9)
    public void testOpenTicketsCount() {
        getDriver().findElement(By.xpath("//span[text()='Dashboard']")).click();
        WebElement count = getDriver().findElement(
                By.xpath("//p[text()='Open Tickets']/following-sibling::p"));
        Assert.assertEquals(count.getText(), "3",
                "Open Tickets count mismatch");
       log.info("PASS: Open Tickets count is 3");
    }

    @Test(priority = 10)
    public void testActiveServicesCardClickable() {
        getDriver().findElement(By.xpath("//span[text()='Dashboard']")).click();
        getDriver().findElement(
                By.xpath("//p[text()='Active Services']")).click();
       log.info("PASS: Active Services card clicked successfully");
    }

    @Test(priority = 11)
    public void testWIPOrdersCardClickable() {
        getDriver().findElement(By.xpath("//span[text()='Dashboard']")).click();
        getDriver().findElement(
                By.xpath("//p[text()='WIP Orders']")).click();
       log.info("PASS: WIP Orders card clicked successfully");
    }

    @Test(priority = 12)
    public void testTerminatedServicesCardClickable() {
        getDriver().findElement(By.xpath("//span[text()='Dashboard']")).click();
        getDriver().findElement(
                By.xpath("//p[text()='Terminated Services']")).click();
       log.info("PASS: Terminated Services card clicked successfully");
    }

    @Test(priority = 13)
    public void testOpenTicketsCardClickable() {
        getDriver().findElement(By.xpath("//span[text()='Dashboard']")).click();
        getDriver().findElement(
                By.xpath("//p[text()='Open Tickets']")).click();
       log.info("PASS: Open Tickets card clicked successfully");
    }

    @Test(priority = 14)
    public void testActiveServiceTableLoads() {
        getDriver().findElement(By.xpath("//span[text()='Dashboard']")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//p[text()='Active Services']")));
        Assert.assertTrue(getDriver().getPageSource().contains("Active Service"),
                "Active Service table did not load");
       log.info("PASS: Active Service table loaded");
    }

    @Test(priority = 15)
    public void testSearchBoxVisible() {
        getDriver().findElement(By.xpath("//span[text()='Dashboard']")).click();
        WebElement searchBox = getDriver().findElement(
                By.cssSelector("input[placeholder='Search...']"));
        Assert.assertTrue(searchBox.isDisplayed(),
                "Search box not visible");
       log.info("PASS: Search box is visible");
    }
}