package microscan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class Support extends BaseTest {


// TC-SP-001

    @Test(priority = 1)
    public void verifySupportPageLoad() {

        getDriver().findElement(By.xpath("//span[text()='Support']")).click();

        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Support']")));

        Assert.assertTrue(getDriver().findElement(By.xpath("//h1[text()='Support']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//*[contains(text(),'Welcome')]")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//*[contains(text(),'Tower Research Capital LLC')]")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//*[contains(text(),'Technical')]")).isDisplayed());

        System.out.println("PASS : Support page loaded successfully");
    }


// TC-SP-002

    @Test(priority = 2, dependsOnMethods = "verifySupportPageLoad")
    public void verifyCreateTicketButton() {

        WebElement button = getDriver().findElement(By.xpath("//button[text()='Create Ticket']"));
        Assert.assertTrue(button.isDisplayed());
        button.click();
        Assert.assertTrue(button.isEnabled());
        System.out.println("PASS : Create Ticket button verified");
    }

// TC-SP-003

    @Test(priority = 3, dependsOnMethods = "verifySupportPageLoad")
    public void verifyCustomerSupportServicesHeading() {

        Assert.assertTrue(getDriver().findElement(By.xpath("//h2[text()='Customer Support Services']")).isDisplayed());
        System.out.println("PASS : Customer Support Services heading verified");
    }


// TC-SP-004

    @Test(priority = 4, dependsOnMethods = "verifySupportPageLoad")
    public void verifyLLCSupportCard() {

        Assert.assertTrue(getDriver().getPageSource().contains("llc.support@microscan.co.in"));
        Assert.assertTrue(getDriver().getPageSource().contains("+91 22-6868-0098"));
        Assert.assertTrue(getDriver().getPageSource().contains("Mon – Sun | 24×7"));

        System.out.println("PASS : LLC support card verified");
    }


// TC-SP-005
@Test(priority = 5, dependsOnMethods = "verifySupportPageLoad")
public void verifyP2PSupportCard() {

    WebElement p2pCard = getDriver().findElement(By.xpath("//h3[contains(text(),'P2P support')]/parent::div"));
    Assert.assertTrue(p2pCard.findElement(By.xpath(".//span[contains(text(),'enterprise.support')]")).isDisplayed());
    Assert.assertTrue(p2pCard.findElement(By.xpath(".//span[contains(text(),'+91 22-6868-0001')]")).isDisplayed());
    System.out.println("PASS : P2P support card verified");
}

// TC-SP-006

    @Test(priority = 6, dependsOnMethods = "verifySupportPageLoad")
    public void verifyILLSupportCard() {
        WebElement illCard = getDriver().findElement(By.xpath("//h3[contains(text(),'ILL support')]/parent::div"));
        Assert.assertTrue(illCard.findElement(By.xpath(".//span[contains(text(),'enterprise.support')]")).isDisplayed());
        System.out.println("PASS : ILL support card verified");
    }

// TC-SP-007
@Test(priority = 7, dependsOnMethods = "verifySupportPageLoad")
public void verifyMailAndPhoneIcons() {

    List<WebElement> icons = getDriver().findElements(By.xpath("//*[local-name()='svg']"));
    Assert.assertTrue(icons.size() == 16);
    System.out.println("PASS : Mail and Phone icons verified");
}

// TC-SP-008

    @Test(priority = 8, dependsOnMethods = "verifySupportPageLoad")
    public void verifySupportCardsDisplayed() {

        List<WebElement> cards = getDriver().findElements(By.xpath("//h3[contains(text(),'support inquiries')]"));
        Assert.assertEquals(cards.size(),3);
        System.out.println("PASS : Support cards displayed correctly");
    }

// TC-SP-009

    @Test(priority = 9, dependsOnMethods = "verifySupportPageLoad")
    public void verifyEnterpriseEscalationMatrix() {

        Assert.assertTrue(getDriver().getPageSource().contains("L1 - Service Desk"));
        Assert.assertTrue(getDriver().getPageSource().contains("L2 - Shift lead"));
        Assert.assertTrue(getDriver().getPageSource().contains("L3 - Rohan Nakhawa / Santosh"));
        Assert.assertTrue(getDriver().getPageSource().contains("L4 - Prashant Pradhan"));
        System.out.println("PASS : Enterprise Escalation Matrix verified");
    }


// TC-SP-010

    @Test(priority = 10, dependsOnMethods = "verifySupportPageLoad")
    public void verifyLLCEscalationMatrix() {

        Assert.assertTrue(getDriver().getPageSource().contains("L3 - Rohan Nakhawa / Niwant Rachikar"));
        Assert.assertTrue(getDriver().getPageSource().contains("prashant.pradhan@microscan.co.in"));
        System.out.println("PASS : LLC Escalation Matrix verified");
    }


// TC-SP-011

    @Test(priority = 11, dependsOnMethods = "verifySupportPageLoad")
    public void verifyEscalationBorders() {

        List<WebElement> borders = getDriver().findElements(By.xpath("//div[contains(@class,'border-b border-gray-200 pb-3')]"));
        Assert.assertEquals(borders.size(),6);
        System.out.println("PASS : Escalation borders verified");
    }


// TC-SP-012

    @Test(priority = 12, dependsOnMethods = "verifySupportPageLoad")
    public void verifyLocationsCard() {

        Assert.assertTrue(getDriver().getPageSource().contains("Head Office - Mumbai"));
        Assert.assertTrue(getDriver().getPageSource().contains("Branch Office - Pune"));
        Assert.assertTrue(getDriver().getPageSource().contains("Mon – Fri | 9:30 a.m. to 6:30 p.m."));
        System.out.println("PASS : Locations card verified");
    }


// TC-SP-013

    @Test(priority = 13, dependsOnMethods = "verifySupportPageLoad")
    public void verifyMapImage() {

        WebElement image = getDriver().findElement(By.xpath("//img[@alt='Office locations map']"));
        Assert.assertTrue(image.isDisplayed());
        System.out.println("PASS : Map image verified");
    }

}