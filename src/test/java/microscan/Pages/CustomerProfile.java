package microscan.Pages;

import microscan.Tests.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CustomerProfile extends BaseTest {

// TC-CP-001

    @Test(priority = 1)
    public void verifyCustomerProfileDefaultLoad() {

        getDriver().findElement(By.xpath("//span[text()='Customer Profile']")).click();

        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Customer Profile']")));
        Assert.assertTrue(getDriver().findElement(By.xpath("//p[text()='Customer Details']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//p[text()='User Details']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//p[text()='Active Services']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//p[text()='WIP Orders']")).isDisplayed());

        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));

        Assert.assertEquals(rows.size(),3);

        System.out.println("PASS : Customer Profile page verified");
    }

// TC-CP-002

    @Test(priority = 2, dependsOnMethods = "verifyCustomerProfileDefaultLoad")
    public void verifyCustomerTableHeaders() {

        List<WebElement> headers = getDriver().findElements(By.xpath("//table//thead//th"));

        Assert.assertEquals(headers.get(0).getText(),"Customer ID");
        Assert.assertEquals(headers.get(1).getText(),"Master Customer name");
        Assert.assertEquals(headers.get(2).getText(),"Customer name");
        Assert.assertEquals(headers.get(3).getText(),"Account status");
        Assert.assertEquals(headers.get(4).getText(),"Created on");

        System.out.println("PASS : Customer table headers verified");
    }


// TC-CP-003

    @Test(priority = 3, dependsOnMethods = "verifyCustomerProfileDefaultLoad")
    public void verifyCustomerRowsData() {

        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));

        Assert.assertEquals(rows.size(),3);

        Assert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[1]/td[1]")).getText(),"C100000064");
        Assert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[2]/td[1]")).getText(),"C100000066");
        Assert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[3]/td[1]")).getText(),"C100001181");

        System.out.println("PASS : Customer rows verified");
    }


// TC-CP-004

    @Test(priority = 4, dependsOnMethods = "verifyCustomerProfileDefaultLoad")
    public void verifyActiveStatusBadges() {

        List<WebElement> badges = getDriver().findElements(By.xpath("//tbody/tr/td//span[text()='Active']"));

        Assert.assertEquals(badges.size(), 3);

        for(WebElement badge : badges) {

            Assert.assertTrue(badge.isDisplayed());
            Assert.assertEquals(badge.getText(),"Active");
        }

        System.out.println("PASS : Active badges verified");
    }


// TC-CP-005

    @Test(priority = 5, dependsOnMethods = "verifyCustomerProfileDefaultLoad")
    public void verifyUserDetailsTileNavigation() {

        getDriver().findElement(By.xpath("//p[text()='User Details']")).click();

        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='User Details']")));

        List<WebElement> headers = getDriver().findElements(By.xpath("//table//thead//th"));

        Assert.assertEquals(headers.get(0).getText(),"User ID");
        Assert.assertEquals(headers.get(1).getText(),"User Name");
        Assert.assertEquals(headers.get(2).getText(),"Email");
        Assert.assertEquals(headers.get(3).getText(),"Role");
        Assert.assertEquals(headers.get(4).getText(),"Status");
        Assert.assertEquals(headers.get(5).getText(),"Created On");

        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));

        Assert.assertEquals(rows.size(),3);

        System.out.println("PASS : User Details tile verified");
    }


// TC-CP-006

    @Test(priority = 6, dependsOnMethods = "verifyCustomerProfileDefaultLoad")
    public void verifyActiveServicesTileNavigation() {

        getDriver().findElement(By.xpath("//p[text()='Active Services']")).click();

        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='Active Services']")));

        List<WebElement> headers = getDriver().findElements(By.xpath("//table//thead//th"));

        Assert.assertEquals(headers.get(0).getText(),"Circuit ID");
        Assert.assertEquals(headers.get(1).getText(),"Customer Name");
        Assert.assertEquals(headers.get(2).getText(),"Status");
        Assert.assertEquals(headers.get(3).getText(),"Service type");
        Assert.assertEquals(headers.get(4).getText(),"Service category");
        Assert.assertEquals(headers.get(5).getText(),"Bandwidth");
        Assert.assertEquals(headers.get(6).getText(),"Location A");
        Assert.assertEquals(headers.get(7).getText(),"Location B");
        Assert.assertTrue(headers.get(8).getText().contains("Activation"));
        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));

        Assert.assertEquals(rows.size(),6);

        System.out.println("PASS : Active Services verified");
    }


// TC-CP-007

    @Test(priority = 7, dependsOnMethods = "verifyCustomerProfileDefaultLoad")
    public void verifyWIPOrdersTileNavigation() {

        getDriver().findElement(By.xpath("//p[text()='WIP Orders']")).click();

        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='WIP Orders']")));

        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));

        Assert.assertEquals(rows.size(),3);

        List<WebElement> badges = getDriver().findElements(By.xpath("//tbody/tr/td//span[text()='WIP']"));

        Assert.assertEquals(badges.size(), 3);

        System.out.println("PASS : WIP Orders verified");
    }


// TC-CP-008

    @Test(priority = 8, dependsOnMethods = "verifyCustomerProfileDefaultLoad")
    public void verifyDynamicTableTitle() {

        getDriver().findElement(By.xpath("//p[text()='User Details']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h3")).getText(),"User Details");

        getDriver().findElement(By.xpath("//p[text()='Active Services']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h3")).getText(),"Active Services");

        System.out.println("PASS : Dynamic title verified");
    }


// TC-CP-009

    @Test(priority = 9, dependsOnMethods = "verifyCustomerProfileDefaultLoad")
    public void verifyReClickActiveTile() {

        getDriver().findElement(By.xpath("//p[text()='Customer Details']")).click();

        getDriver().findElement(By.xpath("//p[text()='Customer Details']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//h3[text()='Customer Details']")).isDisplayed());

        System.out.println("PASS : Active tile reclick verified");
    }


// TC-CP-010

    @Test(priority = 10, dependsOnMethods = "verifyCustomerProfileDefaultLoad")
    public void verifySwitchBackToCustomerDetails() {

        getDriver().findElement(By.xpath("//p[text()='Active Services']")).click();

        getDriver().findElement(By.xpath("//p[text()='Customer Details']")).click();

        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));

        Assert.assertEquals(rows.size(),3);

        System.out.println("PASS : Customer details restored");
    }


// TC-CP-011

    @Test(priority = 11, dependsOnMethods = "verifyCustomerProfileDefaultLoad")
    public void verifyCustomerIdSearch() {

        getDriver().findElement(By.xpath("//p[text()='Customer Details']")).click();

        WebElement search = getDriver().findElement(By.xpath("//input[contains(@placeholder,'Search')]"));

        search.clear();

        search.sendKeys("C100000064");

        getWait().until(ExpectedConditions.numberOfElementsToBe(By.xpath("//tbody/tr"),1));

        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));

        Assert.assertEquals(rows.size(),1);

        Assert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[1]/td[1]")).getText(),"C100000064");

        System.out.println("PASS : Customer ID search verified");
    }


// TC-CP-012

    @Test(priority = 12, dependsOnMethods = "verifyCustomerProfileDefaultLoad")
    public void verifyCustomerNameSearch() {

        getDriver().findElement(By.xpath("//p[text()='Customer Details']")).click();

        WebElement search = getDriver().findElement(By.xpath("//input[contains(@placeholder,'Search')]"));

        search.clear();

        search.sendKeys("Tower Research Capital LLC");

        getWait().until(ExpectedConditions.numberOfElementsToBe(By.xpath("//tbody/tr"),1));

        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));

        Assert.assertEquals(rows.size(),1);

        System.out.println("PASS : Customer name search verified");
    }


// TC-CP-013

    @Test(priority = 13, dependsOnMethods = "verifyCustomerProfileDefaultLoad")
    public void verifyActiveServiceSearch() {

        getDriver().findElement(By.xpath("//p[text()='Active Services']")).click();

        WebElement search = getDriver().findElement(By.xpath("//input[contains(@placeholder,'Search')]"));

        search.clear();

        search.sendKeys("MNLP100007");

        getWait().until(ExpectedConditions.numberOfElementsToBe(By.xpath("//tbody/tr"),1));

        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));

        Assert.assertEquals(rows.size(),1);

        Assert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[1]/td[1]")).getText(),"MNLP100007");

        System.out.println("PASS : Active service search verified");
    }


// TC-CP-014

    @Test(priority = 14, dependsOnMethods = "verifyCustomerProfileDefaultLoad")
    public void verifyInvalidSearch() {

        getDriver().findElement(By.xpath("//p[text()='Customer Details']")).click();

        WebElement search = getDriver().findElement(By.xpath("//input[contains(@placeholder,'Search')]"));

        search.clear();

        search.sendKeys("ZZZNOMATCH999");

        getWait().until(ExpectedConditions.numberOfElementsToBe(By.xpath("//tbody/tr"),0));

        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));

        Assert.assertEquals(rows.size(),0);

        System.out.println("PASS : Invalid search verified");
    }


// TC-CP-015

    @Test(priority = 15, dependsOnMethods = "verifyCustomerProfileDefaultLoad")
    public void verifySearchResetOnTileChange() {

        WebElement search = getDriver().findElement(By.xpath("//input[contains(@placeholder,'Search')]"));

        search.sendKeys("ABC");

        getDriver().findElement(By.xpath("//p[text()='User Details']")).click();

        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='User Details']")));

        WebElement updatedSearch = getDriver().findElement(By.xpath("//input[contains(@placeholder,'Search')]"));

        Assert.assertEquals(updatedSearch.getAttribute("value"),"");

        System.out.println("PASS : Search reset verified");
    }
}
