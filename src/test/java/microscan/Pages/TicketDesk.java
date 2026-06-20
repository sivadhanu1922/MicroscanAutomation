package microscan.Pages;

import microscan.Tests.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class TicketDesk extends BaseTest {


// TC-TD-001

    @Test(priority = 1)
    public void verifyTicketDeskPageLoad() {

        getDriver().findElement(By.xpath("//span[text()='Ticket Desk']")).click();

        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Ticket Desk']")));
        Assert.assertTrue(getDriver().findElement(By.xpath("//h1[text()='Ticket Desk']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//button[contains(.,'Create Ticket')]")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//button[contains(.,'Filter')]")).isDisplayed());
        Assert.assertTrue(getDriver().getPageSource().contains("Tower Research Capital LLC"));
        System.out.println("PASS : Ticket Desk page loaded successfully");
    }


// TC-TD-002

    @Test(priority = 2, dependsOnMethods = "verifyTicketDeskPageLoad")
    public void verifyTicketTableHeaders() {

        List<WebElement> headers = getDriver().findElements(By.xpath("//table//thead//th"));

        Assert.assertEquals(headers.get(0).getText(), "Ticket ID");
        Assert.assertEquals(headers.get(1).getText(), "Customer name");
        Assert.assertEquals(headers.get(2).getText(), "Circuit ID");
        Assert.assertEquals(headers.get(3).getText(), "Service type");
        Assert.assertEquals(headers.get(4).getText(), "Nature of Complaint");
        Assert.assertEquals(headers.get(5).getText(), "RFO");
        Assert.assertEquals(headers.get(6).getText(), "Severity");
        Assert.assertEquals(headers.get(7).getText(), "Status");
        Assert.assertEquals(headers.get(8).getText(), "Created On");
        Assert.assertEquals(headers.get(9).getText(), "Updated On");
        Assert.assertEquals(headers.get(10).getText(), "Resolved time");
        Assert.assertEquals(headers.get(11).getText(), "Closed time");
        Assert.assertEquals(headers.get(12).getText(), "MTR");

        List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(rows.size(), 10);
        System.out.println("PASS : Ticket table verified");
    }


// TC-TD-003

    @Test(priority = 3, dependsOnMethods = "verifyTicketDeskPageLoad")
    public void verifyStatusBadges() {

        Assert.assertTrue(getDriver().findElement(By.xpath("//span[text()='Open']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//span[text()='Pending']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//span[text()='Resolved']")).isDisplayed());
        System.out.println("PASS : Status badges verified");
    }


// TC-TD-004

    @Test(priority = 4, dependsOnMethods = "verifyTicketDeskPageLoad")
    public void verifyEmptyRFOValues() {

        List<WebElement> rfoCells = getDriver().findElements(By.xpath("//tbody/tr/td[6]"));
        Assert.assertEquals(rfoCells.size(), 10, "Expected 10 RFO cells");
        for (WebElement cell : rfoCells) {
            String text = cell.getText();
            Assert.assertTrue(text.equals("-") || !text.isEmpty(),"Empty RFO should display '-', found: '" + text + "'");
        }
        System.out.println("PASS : Empty RFO values verified");
    }


// TC-TD-005

    @Test(priority = 5, dependsOnMethods = "verifyTicketDeskPageLoad")
    public void verifyPaginationControls() {

        Assert.assertTrue(getDriver().findElement(By.xpath("//button[text()='Previous']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//button[text()='Next']")).isDisplayed());
        Assert.assertTrue(getDriver().getPageSource().contains("Showing 20 results"));

        System.out.println("PASS : Pagination controls verified");
    }


// TC-TD-006

    @Test(priority = 6, dependsOnMethods = "verifyTicketDeskPageLoad")
    public void verifyTabsVisible() {

        Assert.assertTrue(getDriver().findElement(By.xpath("//button[text()='Tickets']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//button[text()='History']")).isDisplayed());
        System.out.println("PASS : Tabs verified");
    }


// TC-TD-007

    @Test(priority = 7, dependsOnMethods = "verifyTicketDeskPageLoad")
    public void verifyHistoryTab() {

        getDriver().findElement(By.xpath("//button[text()='History']")).click();
        getWait().until(ExpectedConditions.attributeContains(By.xpath("//button[text()='History']"), "class", "border-b"));
        String historyClass = getDriver().findElement(By.xpath("//button[text()='History']")).getAttribute("class");
        Assert.assertTrue(historyClass.contains("border-b"), "History tab is not active after click");
        System.out.println("PASS : History tab verified");
    }


// TC-TD-008

    @Test(priority = 8, dependsOnMethods = "verifyTicketDeskPageLoad")
    public void verifyTicketsTabAgain() {

        getDriver().findElement(By.xpath("//button[text()='History']")).click();
        getDriver().findElement(By.xpath("//button[text()='Tickets']")).click();
        Assert.assertTrue(getDriver().findElement(By.xpath("//button[text()='Tickets']")).isDisplayed());
        System.out.println("PASS : Tickets tab verified");
    }


// TC-TD-009

    @Test(priority = 9, dependsOnMethods = "verifyTicketDeskPageLoad")
    public void verifyFilterPanel() {

        getDriver().findElement(By.xpath("//button[contains(.,'Filter')]")).click();

        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='Filters']")));

        Assert.assertTrue(getDriver().findElement(By.xpath("//input[@placeholder='Enter Ticket ID']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//label[text()='Circuit ID']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//label[text()='Service Type']")).isDisplayed());

        System.out.println("PASS : Filter panel verified");
    }


// TC-TD-010

    @Test(priority = 10, dependsOnMethods = "verifyFilterPanel")
    public void verifyTicketIdFilter() {

        WebElement input = getDriver().findElement(By.xpath("//input[@placeholder='Enter Ticket ID']"));
        input.sendKeys("INC2111251590148");
        Assert.assertEquals(input.getAttribute("value"), "INC2111251590148");
        System.out.println("PASS : Ticket ID filter verified");
    }


// TC-TD-011

    @Test(priority = 11, dependsOnMethods = "verifyFilterPanel")
    public void verifyCircuitDropdown() {

        Select circuitDropdown = new Select(getDriver().findElement(By.xpath("//label[text()='Circuit ID']/following-sibling::select")));
        circuitDropdown.selectByVisibleText("MNLP100007");
        Assert.assertEquals(circuitDropdown.getFirstSelectedOption().getText(), "MNLP100007");

        System.out.println("PASS : Circuit dropdown verified");
    }


// TC-TD-012

    @Test(priority = 12, dependsOnMethods = "verifyFilterPanel")
    public void verifyServiceTypeDropdown() {

        Select serviceDropdown = new Select(getDriver().findElement(By.xpath("//label[text()='Service Type']/following-sibling::select")));
        serviceDropdown.selectByVisibleText("IPLC");
        Assert.assertEquals(serviceDropdown.getFirstSelectedOption().getText(), "IPLC");

        System.out.println("PASS : Service type dropdown verified");
    }


// TC-TD-013

    @Test(priority = 13, dependsOnMethods = "verifyFilterPanel")
    public void verifyResetButton() {

        getDriver().findElement(By.xpath("//button[contains(.,'Reset')]")).click();
        Assert.assertTrue(getDriver().findElement(By.xpath("//button[contains(.,'Reset')]")).isDisplayed());
        System.out.println("PASS : Reset button verified");
    }


// TC-TD-014

    @Test(priority = 14, dependsOnMethods = "verifyFilterPanel")
    public void verifyFilterCollapse() {

        getDriver().findElement(By.xpath("//button[contains(.,'Filter')]")).click();
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h3[text()='Filters']")));
        Assert.assertFalse(getDriver().findElements(By.xpath("//h3[text()='Filters']")).size() > 0);
        System.out.println("PASS : Filter collapse verified");
    }


// TC-TD-015

    @Test(priority = 15, dependsOnMethods = "verifyTicketDeskPageLoad")
    public void verifyCreateTicketOptions() {

        getDriver().findElement(By.xpath("//button[contains(.,'Create Ticket')]")).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Incident Ticket')]")));
        Assert.assertTrue(getDriver().findElement(By.xpath("//*[contains(text(),'Incident Ticket')]")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//*[contains(text(),'Service Request Ticket')]")).isDisplayed());

        System.out.println("PASS : Create Ticket options verified");
    }
    // TC-TD-016

    @Test(priority = 16, dependsOnMethods = "verifyCreateTicketOptions")
    public void verifyIncidentTicketModal() {

        getDriver().findElement(By.xpath("//*[contains(text(),'Incident Ticket')]")).click();

        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Incident Ticket')]")));
        Assert.assertTrue(getDriver().findElement(By.xpath("//h2[contains(text(),'Incident Ticket')]")).isDisplayed());

        Select iDropdown = new Select(getDriver().findElement(By.xpath("//label[text()='Circuit ID']/following-sibling::select")));
        iDropdown.selectByVisibleText("MNLPXC005");
        Assert.assertEquals(iDropdown.getFirstSelectedOption().getText(), "MNLPXC005");

        Select iiDropdown = new Select(getDriver().findElement(By.xpath("//label[text()='Nature of complaints']/following-sibling::select")));
        iiDropdown.selectByVisibleText("High Latency");
        Assert.assertEquals(iiDropdown.getFirstSelectedOption().getText(), "High Latency");

        Assert.assertTrue(getDriver().findElement(By.xpath("//textarea")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//button[text()='Submit']")).isDisplayed());
        WebElement textarea = getDriver().findElement(By.xpath("//textarea"));
        textarea.sendKeys("Internet connectivity issue");
        Assert.assertEquals(textarea.getAttribute("value"),"Internet connectivity issue");
        Assert.assertTrue(getDriver().findElement(By.xpath("//label[text()='Upload Attachment']")).isDisplayed(), "Upload Attachment is missing ");
        Assert.assertTrue(getDriver().findElement(By.xpath("//h3[text()='Service Details']")).isDisplayed(), "Service Details is missing ");
        System.out.println("PASS : Incident Ticket modal verified");
    }


// TC-TD-017

    @Test(priority = 17, dependsOnMethods = "verifyIncidentTicketModal")
    public void verifyServiceRequestTicketModal() {

        getDriver().findElement(By.xpath("//div[contains(@class,'max-w-xl')]//button[.//*[local-name()='svg']]")).click();
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2[contains(text(),'Incident Ticket')]")));

        getDriver().findElement(By.xpath("//button[contains(.,'Create Ticket')]")).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(.,'Service Request Ticket')]")));

        getDriver().findElement(By.xpath("//button[contains(.,'Service Request Ticket')]")).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Service Request')]")));
        Assert.assertTrue(getDriver().findElement(By.xpath("//h2[contains(text(),'Service Request')]")).isDisplayed());

        Select sDropdown = new Select(getDriver().findElement(By.xpath("//label[text()='Circuit ID']/following-sibling::select")));
        sDropdown.selectByVisibleText("MNLPXC005");
        Assert.assertEquals(sDropdown.getFirstSelectedOption().getText(), "MNLPXC005");

        Select srDropdown = new Select(getDriver().findElement(By.xpath("//label[text()='Event Type']/following-sibling::select")));
        srDropdown.selectByVisibleText("Non - Technical");
        Assert.assertEquals(srDropdown.getFirstSelectedOption().getText(), "Non - Technical");

        Select ssDropdown = new Select(getDriver().findElement(By.xpath("//label[text()='Nature of complaints']/following-sibling::select")));
        ssDropdown.selectByVisibleText("Ip Pool Advertisement");
        Assert.assertEquals(ssDropdown.getFirstSelectedOption().getText(), "Ip Pool Advertisement");
        Assert.assertTrue(getDriver().findElement(By.xpath("//textarea")).isDisplayed());

        Assert.assertTrue(getDriver().findElement(By.xpath("//button[text()='Submit']")).isDisplayed());
        WebElement textarea = getDriver().findElement(By.xpath("//textarea"));

        textarea.clear();
        textarea.sendKeys("Internet connectivity issue");
        Assert.assertEquals(textarea.getAttribute("value"), "Internet connectivity issue");

        Assert.assertTrue(getDriver().findElement(By.xpath("//label[text()='Upload Attachment']")).isDisplayed(), "Upload Attachment is missing from Service Request modal");
        Assert.assertTrue(getDriver().findElement(By.xpath("//h3[text()='Service Details']")).isDisplayed(), "Service Details is missing from Service Request modal");
        System.out.println("PASS TC-TD-017: Service Request modal verified");
    }
    // TC-TD-018

    @Test(priority = 18, dependsOnMethods = "verifyTicketDeskPageLoad")
    public void verifyTicketDetailsTab() {
        getDriver().findElement(By.xpath("//div[contains(@class,'max-w-xl')]//button[.//*[local-name()='svg']]")).click();
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2[contains(text(),'Service Request')]")));
        getDriver().findElement(By.xpath("//tbody/tr[1]/td[1]//button[contains(text(),'INC')]")).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'TICKET ID:')]")));

        Assert.assertTrue(getDriver().findElement(By.xpath("//button[text()='Details']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//*[text()='Ticket Details']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//*[text()='Service Details']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//*[text()='Service ID']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//*[text()='Status']")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//*[text()='Bandwidth']")).isDisplayed());

        System.out.println("PASS : Details tab verified");
    }
    // TC-TD-019

    @Test(priority = 19, dependsOnMethods = "verifyTicketDetailsTab")
    public void verifyActivityUpdatesTab() {
        getDriver().findElement(By.xpath("//button[contains(.,'Activity Updates')]")).click();
        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Latest Updates')]")));

        Assert.assertTrue(getDriver().findElement(By.xpath("//*[contains(text(),'Latest Updates')]")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//textarea")).isDisplayed());
        Assert.assertTrue(getDriver().getPageSource().contains("Click to upload"));
        Assert.assertTrue(getDriver().findElement(By.xpath("//button[text()='Submit']")).isDisplayed());
        WebElement messageBox = getDriver().findElement(By.xpath("//textarea"));
        messageBox.sendKeys("Testing activity updates");
        Assert.assertEquals(messageBox.getAttribute("value"), "Testing activity updates");
        Assert.assertTrue(getDriver().findElement(By.xpath("//h4[text()='Microscan Infocommtech Private Limited']")).isDisplayed());
        System.out.println("PASS : Activity Updates tab verified");
    }
}
