package microscan.Pages;

import microscan.Pages.reports.TestContextHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class TicketDesk extends BaseTest {
// TC-TD-001

    @Test(priority = 1, description = "TC-TD-001", groups = {"navigation"})
    public void verifyTicketDeskPageLoad() {
        TestContextHelper.setExpected("Ticket Desk page loads with Create Ticket button, Filter button and ticket table");
        SoftAssert softAssert = new SoftAssert();
        try {
            getDriver().findElement(By.xpath("//span[text()='Ticket Desk']")).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Ticket Desk']")));
            softAssert.assertTrue(getDriver().findElement(By.xpath("//h1[text()='Ticket Desk']")).isDisplayed(), "Ticket Desk title is not displayed");
            softAssert.assertTrue(getDriver().findElement(By.xpath("//button[contains(.,'Create Ticket')]")).isDisplayed(), "Create Ticket button is not displayed");
            softAssert.assertTrue(getDriver().findElement(By.xpath("//button[contains(.,'Filter')]")).isDisplayed(), "Filter button is not displayed");
            List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
            softAssert.assertTrue(!rows.isEmpty(), "Ticket table is empty");
            String actual = "Ticket Desk page loaded with " + rows.size() + " ticket records";
            TestContextHelper.setActual(actual);
            softAssert.assertAll();
            System.out.println("PASS : " + actual);
        } catch (Throwable e) {
            TestContextHelper.setActual("Validation Failed");
            System.out.println("FAIL : " + e.getMessage());
            TestContextHelper.setFailureReason(e.getMessage());
            throw e;
        }
    }
// TC-TD-002

    @Test(priority = 2, description = "TC-TD-002", dependsOnMethods = "verifyTicketDeskPageLoad", groups = {"headers"})
    public void verifyTicketTableHeaders() {
        TestContextHelper.setExpected("Ticket table displays all 13 expected headers");
        SoftAssert softAssert = new SoftAssert();
        try {
            List<WebElement> headers = getDriver().findElements(By.xpath("//table//thead//th"));
            String[] expectedHeaders = {"Ticket ID", "Customer name", "Circuit ID", "Service type", "Nature of Complaint", "RFO", "Severity", "Status", "Created On", "Updated On", "Resolved time", "Closed time", "MTR"};
            softAssert.assertEquals(headers.size(), expectedHeaders.length, "Header count mismatch");
            for (int i = 0; i < expectedHeaders.length; i++) {
                softAssert.assertEquals(headers.get(i).getText().trim(), expectedHeaders[i], "Header mismatch at position " + (i + 1));
            }
            String actual = "All " + expectedHeaders.length + " Ticket Desk table headers verified successfully";
            TestContextHelper.setActual(actual);
            softAssert.assertAll();
            System.out.println("PASS : " + actual);
        } catch (Throwable e) {
            TestContextHelper.setActual("Validation Failed");
            System.out.println("FAIL : " + e.getMessage());
            TestContextHelper.setFailureReason(e.getMessage());
            throw e;
        }
    }
// TC-TD-003

    @Test(priority = 3, description = "TC-TD-003", dependsOnMethods = "verifyTicketDeskPageLoad", groups = {"data_quality"})
    public void verifyTicketTableContent() {
        TestContextHelper.setExpected("Ticket table displays 10 records with valid Ticket ID, Customer Name, Circuit ID, Service Type and Nature of complaint values");
        SoftAssert softAssert = new SoftAssert();
        try {
            List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
            softAssert.assertEquals(rows.size(), 10, "Ticket row count mismatch");
            for (int i = 1; i <= rows.size(); i++) {
                String ticketId = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[1]")).getText().trim();
                String customerName = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[2]")).getText().trim();
                String circuitId = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[3]")).getText().trim();
                String serviceType = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[4]")).getText().trim();
                String nature = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[5]")).getText().trim();
                softAssert.assertTrue(ticketId.matches("^INC\\d+$"), "Invalid ticket ID format for row " + i);
                softAssert.assertEquals(customerName, "Tower Research Capital LLC", "Customer name mismatch for row " + i);
                softAssert.assertFalse(serviceType.isEmpty(), "Service type is empty for row " + i);
                softAssert.assertFalse(circuitId.isEmpty(), "Circuit ID is empty for row " + i);
                softAssert.assertFalse(nature.isEmpty(), "Nature of complaint is empty for row " + i);
            }
            String actual = "All 10 ticket records verified successfully with valid details";
            TestContextHelper.setActual(actual);
            softAssert.assertAll();
            System.out.println("PASS : " + actual);
        } catch (Throwable e) {
            TestContextHelper.setActual("Validation Failed");
            System.out.println("FAIL : " + e.getMessage());
            TestContextHelper.setFailureReason(e.getMessage());
            throw e;
        }
    }

    // TC-TD-004
    @Test(priority = 4, description = "TC-TD-004", dependsOnMethods = "verifyTicketDeskPageLoad", groups = {"status_validation"})
    public void verifyStatusBadges() {
        TestContextHelper.setExpected("All ticket records display valid status values");
        SoftAssert softAssert = new SoftAssert();
        try {
            List<WebElement> statusBadges = getDriver().findElements(By.xpath("//tbody/tr/td[8]/span"));
            List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
            softAssert.assertEquals(statusBadges.size(), rows.size(), "Status badge count mismatch");
            for (int i = 0; i < statusBadges.size(); i++) {
                String status = statusBadges.get(i).getText().trim();
                softAssert.assertTrue(status.equals("Open") || status.equals("Pending") || status.equals("Resolved"), "Invalid status found in row " + (i + 1) + ": " + status);
            }
            String actual = "All " + statusBadges.size() + " ticket records display valid statuses";
            TestContextHelper.setActual(actual);
            softAssert.assertAll();
            System.out.println("PASS : " + actual);
        } catch (Throwable e) {
            TestContextHelper.setActual("Validation Failed");
            System.out.println("FAIL : " + e.getMessage());
            TestContextHelper.setFailureReason(e.getMessage());
            throw e;
        }
    }

    @Test(priority = 5, description = "TC-TD-005", dependsOnMethods = "verifyTicketDeskPageLoad", groups = {"data_quality"})
    public void verifyRfoAndMtrValues() {
        TestContextHelper.setExpected("RFO and MTR columns display valid values for all ticket records");
        SoftAssert softAssert = new SoftAssert();
        try {
            List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
            for (int i = 1; i <= rows.size(); i++) {
                String rfo = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[6]")).getText().trim();
                String mtr = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[13]")).getText().trim();
                softAssert.assertTrue(rfo.equals("-") || !rfo.isEmpty(), "Invalid RFO value for row " + i);
                softAssert.assertTrue(mtr.equals("-") || mtr.matches("\\d{2}Days:\\d{2}Hrs:\\d{2}Ms"), "Invalid MTR format for row " + i + ": " + mtr);
            }
            String actual = "RFO and MTR values verified successfully for " + rows.size() + " records";
            TestContextHelper.setActual(actual);
            softAssert.assertAll();
            System.out.println("PASS : " + actual);
        } catch (Throwable e) {
            TestContextHelper.setActual("Validation Failed");
            System.out.println("FAIL : " + e.getMessage());
            TestContextHelper.setFailureReason(e.getMessage());
            throw e;
        }
    }

    @Test(priority = 6, description = "TC-TD-006", dependsOnMethods = "verifyTicketDeskPageLoad", groups = {"data_quality"})
    public void verifyTimingFields() {
        TestContextHelper.setExpected("Created On, Updated On, Resolved Time and Closed Time display valid values");
        SoftAssert softAssert = new SoftAssert();
        try {
            List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
            for (int i = 1; i <= rows.size(); i++) {
                String createdOn = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[9]")).getText().trim();
                String updatedOn = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[10]")).getText().trim();
                String resolvedTime = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[11]")).getText().trim();
                String closedTime = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[12]")).getText().trim();
                softAssert.assertFalse(createdOn.isEmpty(), "Created On is empty for row " + i);
                softAssert.assertFalse(updatedOn.isEmpty(), "Updated On is empty for row " + i);
                softAssert.assertFalse(resolvedTime.isEmpty(), "Resolved Time is empty for row " + i);
                softAssert.assertTrue(closedTime.isEmpty() || !closedTime.isBlank(), "Invalid Closed Time value for row " + i);//blank = " spaces" or \t etc
            }
            String actual = "Timing fields verified successfully for " + rows.size() + " records";
            TestContextHelper.setActual(actual);
            softAssert.assertAll();
            System.out.println("PASS : " + actual);
        } catch (Throwable e) {
            TestContextHelper.setActual("Validation Failed");
            System.out.println("FAIL : " + e.getMessage());
            TestContextHelper.setFailureReason(e.getMessage());
            throw e;
        }
    }

    @Test(priority = 7, description = "TC-TD-007", dependsOnMethods = "verifyTicketDeskPageLoad", groups = {"data_quality"})
    public void verifySeverityValues() {
        TestContextHelper.setExpected("All ticket records display valid severity values");
        SoftAssert softAssert = new SoftAssert();
        try {
            List<WebElement> severities = getDriver().findElements(By.xpath("//tbody/tr/td[7]"));
            List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
            softAssert.assertEquals(severities.size(), rows.size(), "Severity count mismatch");
            for (int i = 0; i < severities.size(); i++) {
                String severity = severities.get(i).getText().trim();
                softAssert.assertTrue(severity.equals("Email") || severity.equals("Call") || severity.equals("CSP"), "Invalid severity found in row " + (i + 1) + ": " + severity);
            }
            String actual = "All " + severities.size() + " ticket records display valid severities";
            TestContextHelper.setActual(actual);
            softAssert.assertAll();
            System.out.println("PASS : " + actual);
        } catch (Throwable e) {
            TestContextHelper.setActual("Validation Failed");
            System.out.println("FAIL : " + e.getMessage());
            TestContextHelper.setFailureReason(e.getMessage());
            throw e;
        }
    }

    @Test(priority = 8, description = "TC-TD-008", dependsOnMethods = "verifyTicketDeskPageLoad", groups = {"ui_validation"})
    public void verifyFilterPanelElements() {
        TestContextHelper.setExpected("Filter panel displays Ticket ID, Circuit ID, Service Type, Date Range, Reset and Apply controls");
        SoftAssert softAssert = new SoftAssert();
        try {
            getDriver().findElement(By.xpath("//button[contains(.,'Filter')]")).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='Filters']")));
            WebElement ticketId = getDriver().findElement(By.xpath("//input[@placeholder='Enter Ticket ID']"));
            WebElement circuitId = getDriver().findElement(By.xpath("//label[text()='Circuit ID']/following::select[1]"));
            WebElement serviceType = getDriver().findElement(By.xpath("//label[text()='Service Type']/following::select[1]"));
            WebElement dateRange = getDriver().findElement(By.xpath("//label[text()='Date Range']/following::select[1]"));
            WebElement resetButton = getDriver().findElement(By.xpath("//button[contains(.,'Reset')]"));
            WebElement applyButton = getDriver().findElement(By.xpath("//button[contains(.,'Apply')]"));
            softAssert.assertTrue(ticketId.isDisplayed(), "Ticket ID field is not displayed");
            softAssert.assertTrue(circuitId.isDisplayed(), "Circuit ID dropdown is not displayed");
            softAssert.assertTrue(serviceType.isDisplayed(), "Service Type dropdown is not displayed");
            softAssert.assertTrue(dateRange.isDisplayed(), "Date Range dropdown is not displayed");
            softAssert.assertTrue(resetButton.isDisplayed(), "Reset button is not displayed");
            softAssert.assertTrue(applyButton.isDisplayed(), "Apply button is not displayed");
            String actual = "All filter panel controls displayed successfully";
            TestContextHelper.setActual(actual);
            softAssert.assertAll();
            System.out.println("PASS : " + actual);
        } catch (Throwable e) {
            TestContextHelper.setActual("Validation Failed");
            System.out.println("FAIL : " + e.getMessage());
            TestContextHelper.setFailureReason(e.getMessage());
            throw e;
        }
    }

    @Test(priority = 9, description = "TC-TD-009", dependsOnMethods = "verifyFilterPanelElements", groups = {"ui_validation"})
    public void verifyFilterFieldInteractions() {
        TestContextHelper.setExpected("Ticket ID accepts input, Circuit ID, Service Type and Date Range allow selection, and filter panel collapses successfully");
        SoftAssert softAssert = new SoftAssert();
        try {
            WebElement ticketId = getDriver().findElement(By.xpath("//input[@placeholder='Enter Ticket ID']"));
            WebElement circuitId = getDriver().findElement(By.xpath("//label[text()='Circuit ID']/following::select[1]"));
            WebElement serviceType = getDriver().findElement(By.xpath("//label[text()='Service Type']/following::select[1]"));
            WebElement dateRange = getDriver().findElement(By.xpath("//label[text()='Date Range']/following::select[1]"));
            // default values
            Select circuitSelect = new Select(circuitId);
            Select serviceSelect = new Select(serviceType);
            Select dateRangeSelect = new Select(dateRange);
            softAssert.assertEquals(circuitSelect.getFirstSelectedOption().getText().trim(), "All", "Default Circuit ID value mismatch");
            softAssert.assertEquals(serviceSelect.getFirstSelectedOption().getText().trim(), "All", "Default Service Type value mismatch");
            softAssert.assertEquals(dateRangeSelect.getFirstSelectedOption().getText().trim(), "Today", "Default Date Range value mismatch");
            // Ticket ID
            String ticketValue = "INC2111251590148";
            ticketId.clear();
            ticketId.sendKeys(ticketValue);
            softAssert.assertEquals(ticketId.getAttribute("value"), ticketValue, "Ticket ID value mismatch");
            // non-default values
            circuitSelect.selectByVisibleText("RS10000014");
            softAssert.assertEquals(circuitSelect.getFirstSelectedOption().getText().trim(), "RS10000014", "Circuit ID selection mismatch");
            serviceSelect.selectByVisibleText("Data_Center_Service");
            softAssert.assertEquals(serviceSelect.getFirstSelectedOption().getText().trim(), "Data_Center_Service", "Service Type selection mismatch");
            dateRangeSelect.selectByVisibleText("Last 30 Days");
            softAssert.assertEquals(dateRangeSelect.getFirstSelectedOption().getText().trim(), "Last 30 Days", "Date Range selection mismatch");
            // Collapse filter panel
            getDriver().findElement(By.xpath("//button[contains(.,'Filter')]")).click();
            boolean isCollapsed = getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h3[text()='Filters']")));
            softAssert.assertTrue(isCollapsed, "Filter panel did not collapse");
            String actual = "Ticket ID accepted input, Circuit ID, Service Type and Date Range selections verified, and filter panel collapsed successfully";
            TestContextHelper.setActual(actual);
            softAssert.assertAll();
            System.out.println("PASS : " + actual);
        } catch (Throwable e) {
            TestContextHelper.setActual("Validation Failed");
            System.out.println("FAIL : " + e.getMessage());
            TestContextHelper.setFailureReason(e.getMessage());
            throw e;
        }
    }

    @Test(priority = 10, description = "TC-TD-010", dependsOnMethods = "verifyTicketDeskPageLoad", groups = {"ui_validation"})
    public void verifyTableUtilityControls() {
        TestContextHelper.setExpected("Previous, Next, Refresh and Download controls are displayed correctly");
        SoftAssert softAssert = new SoftAssert();
        try {
            // Pagination controls
            WebElement previousButton = getDriver().findElement(By.xpath("//button[text()='Previous']"));
            WebElement nextButton = getDriver().findElement(By.xpath("//button[text()='Next']"));
            // Refresh icon
            WebElement refreshButton = getDriver().findElement(By.cssSelector("svg.lucide-rotate-cw"));
            // Download icon
            WebElement downloadButton = getDriver().findElement(By.cssSelector("svg.lucide-download"));
            softAssert.assertTrue(previousButton.isDisplayed(), "Previous button is not displayed");
            softAssert.assertTrue(nextButton.isDisplayed(), "Next button is not displayed");
            softAssert.assertTrue(refreshButton.isDisplayed(), "Refresh button is not displayed");
            softAssert.assertTrue(downloadButton.isDisplayed(), "Download button is not displayed");
            softAssert.assertTrue(refreshButton.isEnabled(), "Refresh button is disabled");
            softAssert.assertTrue(downloadButton.isEnabled(), "Download button is disabled");
            softAssert.assertTrue(getDriver().getPageSource().contains("Showing 20 results"), "'Showing 20 results' text is not displayed");
            String actual = "Pagination controls, result count, refresh and download icons verified successfully";
            TestContextHelper.setActual(actual);
            softAssert.assertAll();
            System.out.println("PASS : " + actual);
        } catch (Throwable e) {
            TestContextHelper.setActual("Validation Failed");
            System.out.println("FAIL : " + e.getMessage());
            TestContextHelper.setFailureReason(e.getMessage());
            throw e;
        }
    }

    @Test(priority = 11, description = "TC-TD-011", dependsOnMethods = "verifyTicketDeskPageLoad", groups = {"ui_validation"})
    public void verifyTicketAndHistoryTabSelection() {
        TestContextHelper.setExpected("Tickets and History tabs display correct active and inactive states");
        SoftAssert softAssert = new SoftAssert();
        try {
            WebElement ticketsTab = getDriver().findElement(By.xpath("//button[text()='Tickets']"));
            WebElement historyTab = getDriver().findElement(By.xpath("//button[text()='History']"));
            // see that tabs are displayed
            softAssert.assertTrue(ticketsTab.isDisplayed(), "Tickets tab is not displayed");
            softAssert.assertTrue(historyTab.isDisplayed(), "History tab is not displayed");
            // tickets tab is active by default
            String ticketsClass = ticketsTab.getAttribute("class");
            softAssert.assertTrue(ticketsClass.contains("border-b-2"), "Tickets tab is not active by default");
            // Click History tab
            historyTab.click();
            getWait().until(ExpectedConditions.attributeContains(historyTab, "class", "border-b-2"));
            String historyClass = historyTab.getAttribute("class");
            softAssert.assertTrue(historyClass.contains("border-b-2"), "History tab did not become active");
            // see that Tickets tab becomes inactive
            ticketsClass = ticketsTab.getAttribute("class");
            softAssert.assertFalse(ticketsClass.contains("border-b-2"), "Tickets tab is still active");
            // Click Tickets tab again
            ticketsTab.click();
            getWait().until(ExpectedConditions.attributeContains(ticketsTab, "class", "border-b-2"));
            ticketsClass = ticketsTab.getAttribute("class");
            softAssert.assertTrue(ticketsClass.contains("border-b-2"), "Tickets tab did not become active again");
            String actual = "Tickets and History tabs changed active states successfully";
            TestContextHelper.setActual(actual);
            softAssert.assertAll();
            System.out.println("PASS : " + actual);
        } catch (Throwable e) {
            TestContextHelper.setActual("Validation Failed");
            System.out.println("FAIL : " + e.getMessage());
            TestContextHelper.setFailureReason(e.getMessage());
            throw e;
        }
    }

    @Test(priority = 12, description = "TC-TD-012", dependsOnMethods = "verifyTicketDeskPageLoad", groups = {"ui_validation"})
    public void verifyIncidentTicketFormFields() {
        TestContextHelper.setExpected("Clicking Create Ticket opens the Incident Ticket popup with Circuit ID, Nature of Complaints, Description, Upload Attachment, Service Details, and Submit button displayed. The form fields accept user input.");
        SoftAssert softAssert = new SoftAssert();
        try {
            getDriver().findElement(By.xpath("//button[contains(.,'Create Ticket')]")).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(.,'Incident Ticket')]")));
            getDriver().findElement(By.xpath("//button[contains(.,'Incident Ticket')]")).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Incident Ticket']")));
            WebElement circuitId = getDriver().findElement(By.xpath("//label[text()='Circuit ID']/following::select[1]"));
            WebElement complaint = getDriver().findElement(By.xpath("//label[contains(text(),'Nature of complaints')]/following::select[1]"));
            WebElement description = getDriver().findElement(By.xpath("//textarea"));
            WebElement uploadSection = getDriver().findElement(By.xpath("//*[contains(text(),'Upload Attachment')]"));
            WebElement serviceDetails = getDriver().findElement(By.xpath("//*[contains(text(),'Service Details')]"));
            WebElement submitButton = getDriver().findElement(By.xpath("//button[contains(.,'Submit')]"));
            //assert
            softAssert.assertTrue(circuitId.isDisplayed(), "Circuit ID dropdown not displayed");
            softAssert.assertTrue(complaint.isDisplayed(), "Nature of complaints dropdown not displayed");
            softAssert.assertTrue(description.isDisplayed(), "Description field not displayed");
            softAssert.assertTrue(uploadSection.isDisplayed(), "Upload Attachment section not displayed");
            softAssert.assertTrue(serviceDetails.isDisplayed(), "Service Details section not displayed");
            softAssert.assertTrue(submitButton.isDisplayed(), "Submit button is not displayed");
            //values
            String circuitValue = "MNLPXC005";
            String complaintValue = "High Latency";
            Select circuitSelect = new Select(circuitId);
            circuitSelect.selectByVisibleText(circuitValue);
            softAssert.assertEquals(circuitSelect.getFirstSelectedOption().getText().trim(), circuitValue, "Circuit ID selection mismatch");
            Select complaintSelect = new Select(complaint);
            complaintSelect.selectByVisibleText(complaintValue);
            softAssert.assertEquals(complaintSelect.getFirstSelectedOption().getText().trim(), complaintValue, "Nature of complaints selection mismatch");
            String inputText = "High latency issue observed";
            description.sendKeys(inputText);
            softAssert.assertEquals(description.getAttribute("value"), inputText, "Description value mismatch");
            softAssert.assertTrue(getDriver().getPageSource().contains(inputText), "Description text not reflected");
            // Close
            getDriver().findElement(By.cssSelector("svg.lucide-x")).click();
            getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2[contains(text(),'Incident Ticket')]")));
            String actual = "Incident Ticket form fields verified successfully";
            TestContextHelper.setActual(actual);
            softAssert.assertAll();
            System.out.println("PASS : " + actual);
        } catch (Throwable e) {
            TestContextHelper.setActual("Validation Failed");
            System.out.println("FAIL : " + e.getMessage());
            TestContextHelper.setFailureReason(e.getMessage());
            throw e;
        }
    }

    @Test(priority = 13, description = "TC-TD-013", dependsOnMethods = "verifyTicketDeskPageLoad", groups = {"ui_validation"})
    public void verifyServiceRequestTicketFormFields() {
        TestContextHelper.setExpected("Clicking Create Ticket opens the Service Ticket popup with Circuit ID,Event type, Nature of Complaints, Description, Upload Attachment, Service Details, and Submit button displayed. The form fields accept user input.");
        SoftAssert softAssert = new SoftAssert();
        try {
            getDriver().findElement(By.xpath("//button[contains(.,'Create Ticket')]")).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(.,'Service Request Ticket')]")));
            getDriver().findElement(By.xpath("//button[contains(.,'Service Request Ticket')]")).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Service Request Ticket')]")));
            // elements
            WebElement circuitId = getDriver().findElement(By.xpath("//label[text()='Circuit ID']/following::select[1]"));
            WebElement eventType = getDriver().findElement(By.xpath("//label[text()='Event Type']/following::select[1]"));
            WebElement complaint = getDriver().findElement(By.xpath("//label[contains(text(),'Nature of complaints')]/following::select[1]"));
            WebElement description = getDriver().findElement(By.xpath("//textarea"));
            WebElement uploadSection = getDriver().findElement(By.xpath("//*[contains(text(),'Upload Attachment')]"));
            WebElement serviceDetails = getDriver().findElement(By.xpath("//*[contains(text(),'Service Details')]"));
            WebElement submitButton = getDriver().findElement(By.xpath("//button[contains(.,'Submit')]"));
            // visible or not
            softAssert.assertTrue(circuitId.isDisplayed(), "Circuit ID dropdown not displayed");
            softAssert.assertTrue(eventType.isDisplayed(), "Event Type dropdown not displayed");
            softAssert.assertTrue(complaint.isDisplayed(), "Nature of complaints dropdown not displayed");
            softAssert.assertTrue(description.isDisplayed(), "Description field not displayed");
            softAssert.assertTrue(uploadSection.isDisplayed(), "Upload Attachment section not displayed");
            softAssert.assertTrue(serviceDetails.isDisplayed(), "Service Details section not displayed");
            softAssert.assertTrue(submitButton.isDisplayed(), "Submit button is not displayed");
            // test data
            String circuitValue = "MNLPXC005";
            String eventTypeValue = "Technical";
            String complaintValue = "DC Access";
            String inputText = "Scheduled maintenance request.";
            // select Circuit ID
            Select circuitSelect = new Select(circuitId);
            circuitSelect.selectByVisibleText(circuitValue);
            softAssert.assertEquals(circuitSelect.getFirstSelectedOption().getText().trim(), circuitValue, "Circuit ID selection mismatch");
            // Select Event Type
            Select eventTypeSelect = new Select(eventType);
            eventTypeSelect.selectByVisibleText(eventTypeValue);
            softAssert.assertEquals(eventTypeSelect.getFirstSelectedOption().getText().trim(), eventTypeValue, "Event Type selection mismatch");
            // Select Nature of complaints
            Select complaintSelect = new Select(complaint);
            complaintSelect.selectByVisibleText(complaintValue);
            softAssert.assertEquals(complaintSelect.getFirstSelectedOption().getText().trim(), complaintValue, "Nature of complaints selection mismatch");
            // Description
            description.clear();
            description.sendKeys(inputText);
            softAssert.assertEquals(description.getAttribute("value"), inputText, "Description value mismatch");
            // Close
            getDriver().findElement(By.cssSelector("svg.lucide-x")).click();
            getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2[contains(text(),'Service Request Ticket')]")));
            String actual = "Service Request Ticket form fields verified successfully";
            TestContextHelper.setActual(actual);
            softAssert.assertAll();
            System.out.println("PASS : " + actual);
        } catch (Throwable e) {
            TestContextHelper.setActual("Validation Failed");
            System.out.println("FAIL : " + e.getMessage());
            TestContextHelper.setFailureReason(e.getMessage());
            throw e;
        }
    }

    @Test(priority = 15, description = "TC-TD-015", dependsOnMethods = "verifyTicketDeskPageLoad", groups = {"data_accuracy"})
    public void verifyTicketIdNavigation() {
        TestContextHelper.setExpected("Each Ticket ID opens its corresponding details popup");
        SoftAssert softAssert = new SoftAssert();
        try {
            List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
            for (int i = 1; i <= rows.size(); i++) {
                String ticketId = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[1]//button")).getText().trim();
                getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[1]//button")).click();
                getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(.,'TICKET ID:')]")));
                String popupTicketId = getDriver().findElement(By.xpath("//h2")).getText();
                softAssert.assertTrue(popupTicketId.contains(ticketId), "Ticket ID mismatch for row " + i);
                closeTicketPopup();
            }
            String actual = "All Ticket IDs opened their corresponding details popup";
            TestContextHelper.setActual(actual);
            softAssert.assertAll();
        } catch (AssertionError e) {
            TestContextHelper.setActual(e.getMessage());
            throw e;
        }
    }

    @Test(priority = 16, description = "TC-TD-016", dependsOnMethods = "verifyTicketIdNavigation", groups = {"data_accuracy"})
    public void verifyTicketDetailsMatchTableData() {
        TestContextHelper.setExpected("Ticket details popup displays values matching the selected ticket row");
        SoftAssert softAssert = new SoftAssert();
        try {
            List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
            softAssert.assertFalse(rows.isEmpty(), "No ticket records available");
            for (int i = 1; i <= rows.size(); i++) {
                // Table values
                String ticketId = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[1]/button")).getText().trim();
                String circuitId = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[3]")).getText().trim();
                String serviceType = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[4]")).getText().trim();
                String issueType = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[5]")).getText().trim();
                String status = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[8]")).getText().trim();
                String createdOn = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[9]")).getText().trim();
                String updatedOn = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[10]")).getText().trim();
                // Open
                getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[1]/button")).click();
                getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(.,'TICKET ID:')]")));
                // Popup values
                String popupTicketId = getDriver().findElement(By.xpath("//h2")).getText().replace("TICKET ID:", "").trim();
                String popupServiceId = getDriver().findElement(By.xpath("//*[text()='Service ID']//following-sibling::*[1]")).getText().trim();
                String popupCircuitLabel = getDriver().findElement(By.xpath("//*[text()='Circuit Label']//following-sibling::*[1]")).getText().trim();

                List<WebElement> popupServiceTypes = getDriver().findElements(By.xpath("//*[text()='Service Type']/following-sibling::*[1]"));
                String popupIssueType = getDriver().findElement(By.xpath("//*[text()='Issue Type']/following-sibling::*[1]")).getText().trim();
                String popupStatus = getDriver().findElement(By.xpath("//*[text()='Status']/following-sibling::*[1][contains(@class,'text-sm')]")).getText().trim();
                String popupCreatedOn = getDriver().findElement(By.xpath("//*[text()='Created On']/following-sibling::*[1][contains(@class,'text-sm')]")).getText().trim();
                String popupUpdatedOn = getDriver().findElement(By.xpath("//*[text()='Updated On']/following-sibling::*[1][contains(@class,'text-sm')]")).getText().trim();
                // Assertions
                softAssert.assertEquals(popupTicketId, ticketId, "Ticket ID mismatch for row " + i);
                softAssert.assertEquals(popupServiceId, circuitId, "Circuit ID mismatch for [service]row " + i);
                softAssert.assertEquals(popupCircuitLabel, circuitId, "Circuit ID mismatch for [circuit]row " + i);
                for (WebElement element : popupServiceTypes) {
                    String popupValue = element.getText().trim();
                    softAssert.assertEquals(popupValue, serviceType, "Service Type mismatch for row " + i);
                }
                softAssert.assertEquals(popupIssueType, issueType, "Issue Type mismatch for row " + i);
                softAssert.assertEquals(popupStatus, status, "Status mismatch for row " + i);
                softAssert.assertEquals(popupCreatedOn, createdOn, "Created On mismatch for row " + i);
                softAssert.assertEquals(popupUpdatedOn, updatedOn, "Updated On mismatch for row " + i);
                closeTicketPopup();
            }
            String actual = "All " + rows.size() + " ticket detail popups displayed data matching the table values";
            TestContextHelper.setActual(actual);
            softAssert.assertAll();
            System.out.println("PASS : " + actual);
        } catch (Throwable e) {
            TestContextHelper.setActual("Validation Failed");
            System.out.println("FAIL : " + e.getMessage());
            TestContextHelper.setFailureReason(e.getMessage());
            throw e;
        }
    }

    @Test(priority = 17, description = "TC-TD-017", dependsOnMethods = "verifyTicketIdNavigation", groups = {"ui_validation", "data_accuracy"})
    public void verifyActivityUpdatesTab() {
        TestContextHelper.setExpected("Activity Updates tab displays update controls and existing updates for every ticket");
        SoftAssert softAssert = new SoftAssert();
        try {
            List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
            softAssert.assertTrue(!rows.isEmpty(), "No ticket records available");
            for (int i = 1; i <= rows.size(); i++) {
                String ticketId = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[1]//button")).getText().trim();
                // Open
                getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[1]//button")).click();
                getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(.,'TICKET ID:')]")));
                // Open Activity
                getDriver().findElement(By.xpath("//button[contains(.,'Activity Updates')]")).click();
                getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Latest Updates')]")));
                // Verify heading
                softAssert.assertTrue(getDriver().findElement(By.xpath("//*[contains(text(),'Latest Updates')]")).isDisplayed(), "Latest Updates heading not displayed for " + ticketId);
                // text area
                WebElement textArea = getDriver().findElement(By.xpath("//textarea"));
                softAssert.assertTrue(textArea.isDisplayed(), "Message text area not displayed for " + ticketId);
                String message = "navya";
                textArea.clear();
                textArea.sendKeys(message);
                softAssert.assertEquals(textArea.getAttribute("value"), message, "Message text mismatch for " + ticketId);
                // upload section
                WebElement uploadButton = getDriver().findElement(By.xpath("//*[contains(text(),'Click to upload')]"));
                softAssert.assertTrue(uploadButton.isDisplayed(), "Upload option not displayed for " + ticketId);
                // submit button
                WebElement submitButton = getDriver().findElement(By.xpath("//button[contains(.,'Submit')]"));
                softAssert.assertTrue(submitButton.isDisplayed(), "Submit button not displayed for " + ticketId);
                // author names
                List<WebElement> authors = getDriver().findElements(By.xpath("//h4[contains(@class,'text-[#F47E36]')]"));
                softAssert.assertTrue(authors.size() > 0, "Author names not displayed for " + ticketId);
                for (WebElement author : authors) {
                    softAssert.assertFalse(author.getText().trim().isEmpty(), "Empty author name found for " + ticketId);
                }
                // author messages
                List<WebElement> messages = getDriver().findElements(By.xpath("//div[contains(@class,'border-l')]//p"));
                softAssert.assertTrue(messages.size() > 0, "No update messages displayed for " + ticketId);
                for (WebElement update : messages) {
                    softAssert.assertFalse(update.getText().trim().isEmpty(), "Empty update message found for " + ticketId);
                }
                closeTicketPopup();
            }
            String actual = "Activity Updates tab verified successfully for all " + rows.size() + " tickets";
            TestContextHelper.setActual(actual);
            softAssert.assertAll();
            System.out.println("PASS : " + actual);
        } catch (Throwable e) {
            TestContextHelper.setActual("Validation Failed");
            System.out.println("FAIL : " + e.getMessage());
            TestContextHelper.setFailureReason(e.getMessage());
            throw e;
        }
    }

    private void closeTicketPopup() {
        getDriver().findElement(By.cssSelector("svg.lucide-x")).click();
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h2[contains(.,'TICKET ID:')]")));
    }
}
