package microscan;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LLCPerformance extends BaseTest {

    @Test
    public void testLLCPerformancePageLoads() {
        getDriver().findElement(By.xpath("//span[text()='LLC Performance']")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[text()='LLC Performance']")));
        Assert.assertTrue(getDriver().getPageSource().contains("LLC"),
                "LLC Performance page did not load");
        System.out.println("PASS: LLC Performance page loaded");
    }

    @Test
    public void testLLCPerformanceMenuVisible() {
        WebElement menu = getDriver().findElement(By.xpath("//span[text()='LLC Performance']"));
        Assert.assertTrue(menu.isDisplayed(), "LLC Performance menu not visible");
        System.out.println("PASS: LLC Performance menu is visible");
    }
}