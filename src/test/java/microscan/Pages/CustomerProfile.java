package microscan.Pages;

import net.bytebuddy.implementation.bytecode.Throw;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import microscan.Pages.reports.TestContextHelper;
import org.testng.asserts.SoftAssert;
import microscan.Pages.reports.DataProviders;

import java.util.List;


public class CustomerProfile extends BaseTest {

    @Test(priority = 1, description = "TC-CP-001", groups = {"navigation"})
    public void verifyCustomerProfileDefaultLoad() {
        TestContextHelper.setExpected("Customer Profile page loads with title, 4 tiles and 3 customer rows");
        SoftAssert softAssert = new SoftAssert();
        try {
            getDriver().findElement(By.xpath("//span[text()='Customer Profile']")).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body")));
            softAssert.assertTrue(getDriver().findElement(By.xpath("//h1[text()='Customer Profile']")).isDisplayed(), "Customer Profile title is not displayed");
            softAssert.assertTrue(getDriver().findElement(By.xpath("//p[text()='Customer Details']")).isDisplayed(), "Customer Details tile is not displayed");
            softAssert.assertTrue(getDriver().findElement(By.xpath("//p[text()='User Details']")).isDisplayed(), "User Details tile is not displayed");
            softAssert.assertTrue(getDriver().findElement(By.xpath("//p[text()='Active Services']")).isDisplayed(), "Active Services tile is not displayed");
            softAssert.assertTrue(getDriver().findElement(By.xpath("//p[text()='WIP Orders']")).isDisplayed(), "WIP Orders tile is not displayed");
            List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
            softAssert.assertEquals(rows.size(), 3, "Customer row count mismatch");
            String actual = "Title 'Customer Profile' displayed, 4 tiles verified, 3 customer rows displayed";
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

    @Test(priority = 2, description = "TC-CP-002", dependsOnMethods = "verifyCustomerProfileDefaultLoad", groups = {"headers"})
    public void verifyCustomerTableHeaders() {
        TestContextHelper.setExpected("Customer ID, Master Customer name, Customer name, Account status and Created on headers are displayed");
        SoftAssert softAssert = new SoftAssert();
        try {
            List<WebElement> headers = getDriver().findElements(By.xpath("//table//thead//th"));
            softAssert.assertEquals(headers.size(), 5, "Customer table header count mismatch");
            softAssert.assertEquals(headers.get(0).getText(), "Customer ID", "Customer ID header mismatch");
            softAssert.assertEquals(headers.get(1).getText(), "Master Customer name", "Master Customer name header mismatch");
            softAssert.assertEquals(headers.get(2).getText(), "Customer name", "Customer name header mismatch");
            softAssert.assertEquals(headers.get(3).getText(), "Account status", "Account status header mismatch");
            softAssert.assertEquals(headers.get(4).getText(), "Created on", "Created on header mismatch");
            String actual = "All 5 Customer Details table headers verified successfully";
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

    @Test(priority = 3, description = "TC-CP-003", dependsOnMethods = "verifyCustomerProfileDefaultLoad", groups = {"data_quality"})
    public void verifyCustomerDetailsDataFormat() {
        TestContextHelper.setExpected("Customer ID, Master Customer Name, Customer Name and Created On values are valid");
        SoftAssert softAssert = new SoftAssert();
        try {
            List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
            for (int i = 1; i <= rows.size(); i++) {
                String customerId = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[1]")).getText().trim();
                String masterCustomerName = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[2]")).getText().trim();
                String customerName = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[3]")).getText().trim();
                String createdOn = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[5]")).getText().trim();
                softAssert.assertTrue(customerId.matches("^C\\d+$"), "Invalid Customer ID format for row " + i);
                softAssert.assertFalse(masterCustomerName.isEmpty(), "Master Customer Name is empty for row " + i);
                softAssert.assertFalse(customerName.isEmpty(), "Customer Name is empty for row " + i);
                softAssert.assertTrue(createdOn.matches("\\d{2}-\\d{2}-\\d{4}"), "Invalid Created On date format for row " + i);
            }
            String actual = "Customer Details data format verified successfully";
            TestContextHelper.setActual(actual);
            softAssert.assertAll();
        } catch (Throwable e) {
            TestContextHelper.setActual("Validation Failed");
            System.out.println("FAIL : " + e.getMessage());
            TestContextHelper.setFailureReason(e.getMessage());
            throw e;
        }
    }

    @Test(priority = 4, description = "TC-CP-004", dependsOnMethods = "verifyCustomerProfileDefaultLoad", groups = {"data_accuracy"})
    public void verifyCustomerRowsData() {
        TestContextHelper.setExpected("All customer rows display correct Customer ID, Master Customer Name, " + "Customer Name and Created On values");
        SoftAssert softAssert = new SoftAssert();
        try {
            String[][] expectedData = {{"C100000064", "Tower Research Capital Markets India Pvt Ltd", "Tower Research Capital Markets India Pvt Ltd", "03-04-2024"},
                    {"C100000066", "Tower Research Capital Markets India Pvt Ltd", "Tower Research Capital LLC", "03-04-2024"},
                    {"C100001181", "Tower Research Capital Markets India Pvt Ltd", "Tower Research Capital India Private Limited", "03-04-2024"}};
            List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
            softAssert.assertEquals(rows.size(), expectedData.length, "Customer Details row count mismatch");
            for (int row = 0; row < expectedData.length; row++) {
                int rowIndex = row + 1;
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[1]")).getText().trim(), expectedData[row][0], "Row " + rowIndex + " Customer ID mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[2]")).getText().trim(), expectedData[row][1], "Row " + rowIndex + " Master Customer Name mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[3]")).getText().trim(), expectedData[row][2], "Row " + rowIndex + " Customer Name mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[5]")).getText().trim(), expectedData[row][3], "Row " + rowIndex + " Created On date mismatch");
            }
            String actual = "All " + expectedData.length + " customer rows verified successfully";
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

    @Test(priority = 5, description = "TC-CP-005", dependsOnMethods = "verifyCustomerProfileDefaultLoad", groups = {"status_validation"})
    public void verifyCustomerActiveStatusBadges() {
        TestContextHelper.setExpected("All customer records display Active status");
        SoftAssert softAssert = new SoftAssert();
        try {
            List<WebElement> badges = getDriver().findElements(By.xpath("//tbody/tr//span"));
            softAssert.assertEquals(badges.size(), 3, "Active status badge count mismatch");
            for (int i = 0; i < badges.size(); i++) {
                softAssert.assertTrue(badges.get(i).isDisplayed(), "Active status badge is not displayed for row " + (i + 1));
                softAssert.assertEquals(badges.get(i).getText(), "Active", "Account status mismatch for row " + (i + 1));
            }
            String actual = "All customer records display Active status";
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

    @Test(priority = 6, description = "TC-CP-006A", dependsOnMethods = "verifyCustomerProfileDefaultLoad", groups = {"data_quality"})
    public void verifyUserDetailsDataFormat() {
        TestContextHelper.setExpected("User ID, User Name, Email, Role and Created On values are valid");
        SoftAssert softAssert = new SoftAssert();
        try {
            getDriver().findElement(By.xpath("//p[text()='User Details']")).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='User Details']")));
            List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
            for (int i = 1; i <= rows.size(); i++) {
                String userId = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[1]")).getText().trim();
                String userName = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[2]")).getText().trim();
                String email = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[3]")).getText().trim();
                String role = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[4]")).getText().trim();
                String createdOn = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[6]")).getText().trim();
                softAssert.assertTrue(userId.matches("^U\\d+$"), "Invalid User ID format for row " + i);
                softAssert.assertFalse(userName.isEmpty(), "User Name is empty for row " + i);
                softAssert.assertTrue(email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"), "Invalid Email format for row " + i);
                softAssert.assertFalse(role.isEmpty(), "Role is empty for row " + i);
                softAssert.assertTrue(createdOn.matches("\\d{2}-\\d{2}-\\d{4}"), "Invalid Created On date format for row " + i);
            }
            String actual = "User Details data format verified successfully";
            TestContextHelper.setActual(actual);
            softAssert.assertAll();
        } catch (Throwable e) {
            TestContextHelper.setActual("Validation Failed");
            System.out.println("FAIL : " + e.getMessage());
            TestContextHelper.setFailureReason(e.getMessage());
            throw e;
        }
    }

    @Test(priority = 7, description = "TC-CP-007", dependsOnMethods = "verifyCustomerProfileDefaultLoad", groups = {"headers"})
    public void verifyUserDetailsTableHeaders() {
        TestContextHelper.setExpected("User ID, User Name, Email, Role, Status and Created On headers are displayed");
        SoftAssert softAssert = new SoftAssert();
        try {
            getDriver().findElement(By.xpath("//p[text()='User Details']")).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='User Details']")));
            List<WebElement> headers = getDriver().findElements(By.xpath("//table//thead//th"));
            softAssert.assertEquals(headers.size(), 6, "User Details table header count mismatch");
            softAssert.assertEquals(headers.get(0).getText(), "User ID", "User ID header mismatch");
            softAssert.assertEquals(headers.get(1).getText(), "User Name", "User Name header mismatch");
            softAssert.assertEquals(headers.get(2).getText(), "Email", "Email header mismatch");
            softAssert.assertEquals(headers.get(3).getText(), "Role", "Role header mismatch");
            softAssert.assertEquals(headers.get(4).getText(), "Status", "Status header mismatch");
            softAssert.assertEquals(headers.get(5).getText(), "Created On", "Created On header mismatch");
            String actual = "All 6 User Details table headers verified successfully";
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

    @Test(priority = 8, description = "TC-CP-008", dependsOnMethods = "verifyCustomerProfileDefaultLoad", groups = {"data_accuracy"})
    public void verifyUserDetailsRowsData() {
        TestContextHelper.setExpected("All user rows display correct User ID, User Name, Email, Role and Created On values");
        SoftAssert softAssert = new SoftAssert();
        try {
            String[][] expectedData = {{"U001", "Khushbu Sharma", "khushbu@towerresearch.com", "Technical", "15-01-2024"}, {"U002", "Rajesh Kumar", "rajesh@towerresearch.com", "Admin", "20-02-2024"}, {"U003", "Priya Singh", "priya@towerresearch.com", "Technical", "10-03-2024"}};
            List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
            softAssert.assertEquals(rows.size(), expectedData.length, "User Details row count mismatch");
            for (int row = 0; row < expectedData.length; row++) {
                int rowIndex = row + 1;
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[1]")).getText().trim(), expectedData[row][0], "Row " + rowIndex + " User ID mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[2]")).getText().trim(), expectedData[row][1], "Row " + rowIndex + " User Name mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[3]")).getText().trim(), expectedData[row][2], "Row " + rowIndex + " Email mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[4]")).getText().trim(), expectedData[row][3], "Row " + rowIndex + " Role mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[6]")).getText().trim(), expectedData[row][4], "Row " + rowIndex + " Created On date mismatch");
            }
            String actual = "All " + expectedData.length + " User Details rows verified successfully";
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

    @Test(priority = 9, description = "TC-CP-009", dependsOnMethods = "verifyCustomerProfileDefaultLoad", groups = {"status_validation"})
    public void verifyUserStatusBadges() {
        TestContextHelper.setExpected("All user records display Active status");
        SoftAssert softAssert = new SoftAssert();
        try {
            List<WebElement> badges = getDriver().findElements(By.xpath("//tbody/tr//span"));
            softAssert.assertEquals(badges.size(), 3, "User status badge count mismatch");
            for (int i = 0; i < badges.size(); i++) {
                softAssert.assertTrue(badges.get(i).isDisplayed(), "Active status badge is not displayed for user row " + (i + 1));
                softAssert.assertEquals(badges.get(i).getText(), "Active", "User status mismatch for row " + (i + 1));
            }
            String actual = "All user records display Active status";
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

    @Test(priority = 10, description = "TC-CP-0010", dependsOnMethods = "verifyCustomerProfileDefaultLoad", groups = {"headers"})
    public void verifyActiveServicesTableHeaders() {
        TestContextHelper.setExpected("Circuit ID, Customer Name, Status, Service type, Service category, Bandwidth, Location A, Location B and Activation Date headers are displayed");
        SoftAssert softAssert = new SoftAssert();
        try {
            getDriver().findElement(By.xpath("//p[text()='Active Services']")).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='Active Services']")));
            List<WebElement> headers = getDriver().findElements(By.xpath("//table//thead//th"));
            softAssert.assertEquals(headers.size(), 9, "Active Services table header count mismatch");
            softAssert.assertEquals(headers.get(0).getText(), "Circuit ID", "Circuit ID header mismatch");
            softAssert.assertEquals(headers.get(1).getText(), "Customer Name", "Customer Name header mismatch");
            softAssert.assertEquals(headers.get(2).getText(), "Status", "Status header mismatch");
            softAssert.assertEquals(headers.get(3).getText(), "Service type", "Service type header mismatch");
            softAssert.assertEquals(headers.get(4).getText(), "Service category", "Service category header mismatch");
            softAssert.assertEquals(headers.get(5).getText(), "Bandwidth", "Bandwidth header mismatch");
            softAssert.assertEquals(headers.get(6).getText(), "Location A", "Location A header mismatch");
            softAssert.assertEquals(headers.get(7).getText(), "Location B", "Location B header mismatch");
            softAssert.assertEquals(headers.get(8).getText(), "Activation Date", "Activation Date header mismatch");
            String actual = "All 9 Active Services table headers verified successfully";
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

    @Test(priority = 11, description = "TC-CP-011", dependsOnMethods = "verifyCustomerProfileDefaultLoad", groups = {"data_quality"})
    public void verifyActiveServicesDataFormat() {
        TestContextHelper.setExpected("Circuit ID, Bandwidth, Location A and Activation Date values are valid");
        SoftAssert softAssert = new SoftAssert();
        try {
            getDriver().findElement(By.xpath("//p[text()='Active Services']")).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='Active Services']")));
            List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
            for (int i = 1; i <= rows.size(); i++) {
                String circuitId = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[1]")).getText().trim();
                String bandwidth = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[6]")).getText().trim();
                String locationA = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[7]")).getText().trim();
                String activationDate = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[9]")).getText().trim();
                softAssert.assertTrue(circuitId.matches("[A-Za-z0-9]+"), "Invalid Circuit ID is for row " + i);
                softAssert.assertFalse(bandwidth.isEmpty(), "Bandwidth is empty for row " + i);
                softAssert.assertFalse(locationA.isEmpty(), "Location A is empty for row " + i);
                softAssert.assertTrue(activationDate.matches("\\d{2}-\\d{2}-\\d{4}"), "Invalid Activation Date format for row " + i + ": " + activationDate);
            }
            String actual = "Circuit ID, Bandwidth, Location A and Activation Date verified for all rows";
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

    @Test(priority = 12, description = "TC-CP-0012", dependsOnMethods = "verifyCustomerProfileDefaultLoad", groups = {"data_accuracy"})
    public void verifyActiveServicesRowsData() {
        TestContextHelper.setExpected("Active Services records display correct Circuit ID, Customer Name, " + "Service Type, Service Name, Bandwidth, Location, Provider and Activation Date");
        SoftAssert softAssert = new SoftAssert();
        try {
            String[][] expectedData = {{"101018302", "Tower Research Capital Markets India Pvt Ltd", "ILL", "ILL LLC", "10 Mbps", "Dheeraj Arma, Andheri East, Mumbai", "-", "01-10-2018"},
                    {"MNLP100007", "Tower Research Capital LLC", "IPLC", "IPLC", "1 Gbps", "MRS 1", "Tata BMC", "01-10-2020"},
                    {"MNLP200001", "Tower Research Capital LLC", "IPLC", "IPLC", "1 Gbps", "SGX to CLS 3", "-", "01-11-2019"},
                    {"MNPLCC0005", "Tower Research Capital LLC", "Data_Center_Ser", "DCS_IPLC_Cross_Connect", "Cross Connect", "Microscan / Tower Rack", "Hurricane Electric", "07-07-2023"},
                    {"MT000417", "Tower Research Capital Markets India Pvt Ltd", "ILL", "ILL LLC", "10 Mbps", "63 Moons Technologies, FT Tower", "-", "12-11-2023"},
                    {"5051340", "Tower Research Capital Markets India Pvt Ltd", "NLD", "LLC", "BANDWIDTH LL CB", "Multi Commodity Exchange Of India Ltd", "63 Moons Technologies", "11-11-2023"}};
            List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
            softAssert.assertEquals(rows.size(), expectedData.length, "Active Services row count mismatch");
            for (int row = 0; row < expectedData.length; row++) {
                int rowIndex = row + 1;
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[1]")).getText().trim(), expectedData[row][0], "Row " + rowIndex + " Circuit ID mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[2]")).getText().trim(), expectedData[row][1], "Row " + rowIndex + " Customer Name mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[4]")).getText().trim(), expectedData[row][2], "Row " + rowIndex + " Service Type mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[5]")).getText().trim(), expectedData[row][3], "Row " + rowIndex + " Service Category mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[6]")).getText().trim(), expectedData[row][4], "Row " + rowIndex + " Bandwidth mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[7]")).getText().trim(), expectedData[row][5], "Row " + rowIndex + " Location A mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[8]")).getText().trim(), expectedData[row][6], "Row " + rowIndex + " Location B mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[9]")).getText().trim(), expectedData[row][7], "Row " + rowIndex + " Activation Date mismatch");
            }
            String actual = "Active Services table data verified successfully";
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

    @Test(priority = 13, description = "TC-CP-013", dependsOnMethods = "verifyCustomerProfileDefaultLoad", groups = {"status_validation"})
    public void verifyActiveServicesStatusBadges() {
        TestContextHelper.setExpected("All Active Services records display Active status");
        SoftAssert softAssert = new SoftAssert();
        try {
            List<WebElement> badges = getDriver().findElements(By.xpath("//tbody/tr//span"));
            softAssert.assertEquals(badges.size(), 6, "Active Services status badge count mismatch");
            for (int i = 0; i < badges.size(); i++) {
                softAssert.assertTrue(badges.get(i).isDisplayed(), "Active status badge is not displayed for service row " + (i + 1));
                softAssert.assertEquals(badges.get(i).getText(), "Active", "Service status mismatch for row " + (i + 1));
            }
            String actual = "All Active Services records display Active status";
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

    @Test(priority = 14, description = "TC-CP-014", dependsOnMethods = "verifyCustomerProfileDefaultLoad", groups = {"headers"})
    public void verifyWIPOrdersTableHeaders() {
        TestContextHelper.setExpected("Circuit ID, Customer Name, Status, Service type, Service category, Bandwidth, Location A, Location B and Activation Date headers are displayed");
        SoftAssert softAssert = new SoftAssert();
        try {
            getDriver().findElement(By.xpath("//p[text()='WIP Orders']")).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='WIP Orders']")));
            List<WebElement> headers = getDriver().findElements(By.xpath("//table//thead//th"));
            softAssert.assertEquals(headers.size(), 9, "WIP Orders table header count mismatch");
            softAssert.assertEquals(headers.get(0).getText(), "Circuit ID", "Circuit ID header mismatch");
            softAssert.assertEquals(headers.get(1).getText(), "Customer Name", "Customer Name header mismatch");
            softAssert.assertEquals(headers.get(2).getText(), "Status", "Status header mismatch");
            softAssert.assertEquals(headers.get(3).getText(), "Service type", "Service type header mismatch");
            softAssert.assertEquals(headers.get(4).getText(), "Service category", "Service category header mismatch");
            softAssert.assertEquals(headers.get(5).getText(), "Bandwidth", "Bandwidth header mismatch");
            softAssert.assertEquals(headers.get(6).getText(), "Location A", "Location A header mismatch");
            softAssert.assertEquals(headers.get(7).getText(), "Location B", "Location B header mismatch");
            softAssert.assertEquals(headers.get(8).getText(), "Activation Date", "Activation Date header mismatch");
            String actual = "All 9 WIP Orders table headers verified successfully";
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

    @Test(priority = 15, description = "TC-CP-015", dependsOnMethods = "verifyCustomerProfileDefaultLoad", groups = {"data_quality"})
    public void verifyWIPOrdersDataFormat() {
        TestContextHelper.setExpected("Circuit ID, Bandwidth, Location A and Activation Date values are valid");
        SoftAssert softAssert = new SoftAssert();
        try {
            getDriver().findElement(By.xpath("//p[text()='WIP Orders']")).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='WIP Orders']")));
            List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
            for (int i = 1; i <= rows.size(); i++) {
                String circuitId = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[1]")).getText().trim();
                String bandwidth = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[6]")).getText().trim();
                String locationA = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[7]")).getText().trim();
                String activationDate = getDriver().findElement(By.xpath("//tbody/tr[" + i + "]/td[9]")).getText().trim();
                softAssert.assertTrue(circuitId.matches("^WIP\\d+$"), "Invalid Circuit ID for row " + i);
                softAssert.assertFalse(bandwidth.isEmpty(), "Bandwidth is empty for row " + i);
                softAssert.assertFalse(locationA.isEmpty(), "Location A is empty for row " + i);
                softAssert.assertTrue(activationDate.matches("\\d{2}-\\d{2}-\\d{4}"), "Invalid Activation Date format for row " + i + ": " + activationDate);
            }
            String actual = "Circuit ID, Bandwidth, Location A and Activation Date verified for all rows";
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

    @Test(priority = 16, description = "TC-CP-016", dependsOnMethods = "verifyCustomerProfileDefaultLoad", groups = {"data_accuracy"})
    public void verifyWIPOrdersRowsData() {
        TestContextHelper.setExpected("All WIP Orders records display correct values");
        SoftAssert softAssert = new SoftAssert();
        try {
            String[][] expectedData = {{"WIP001", "Tower Research Capital LLC", "IPLC", "IPLC", "10 Gbps", "Data Center Mumbai", "Data Center Delhi", "15-12-2025"}, {"WIP002", "Tower Research Capital Markets India Pvt Ltd", "Data_Center_Ser", "DCS_IPLC_Cross_Connect", "100 Gbps", "Tower DC Rack A", "Tower DC Rack B", "20-12-2025"}, {"WIP003", "Tower Research Capital LLC", "ILL", "ILL LLC", "1 Gbps", "Office Mumbai BKC", "-", "25-12-2025"}};
            List<WebElement> rows = getDriver().findElements(By.xpath("//tbody/tr"));
            softAssert.assertEquals(rows.size(), expectedData.length, "WIP Orders row count mismatch");
            for (int row = 0; row < expectedData.length; row++) {
                int rowIndex = row + 1;
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[1]")).getText().trim(), expectedData[row][0], "Row " + rowIndex + " Circuit ID mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[2]")).getText().trim(), expectedData[row][1], "Row " + rowIndex + " Customer Name mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[4]")).getText().trim(), expectedData[row][2], "Row " + rowIndex + " Service Type mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[5]")).getText().trim(), expectedData[row][3], "Row " + rowIndex + " Service Category mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[6]")).getText().trim(), expectedData[row][4], "Row " + rowIndex + " Bandwidth mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[7]")).getText().trim(), expectedData[row][5], "Row " + rowIndex + " Location A mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[8]")).getText().trim(), expectedData[row][6], "Row " + rowIndex + " Location B mismatch");
                softAssert.assertEquals(getDriver().findElement(By.xpath("//tbody/tr[" + rowIndex + "]/td[9]")).getText().trim(), expectedData[row][7], "Row " + rowIndex + " Activation Date mismatch");
            }
            String actual = "All WIP Orders rows verified successfully";
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

    @Test(priority = 17, description = "TC-CP-017", dependsOnMethods = "verifyCustomerProfileDefaultLoad", groups = {"status_validation"})
    public void verifyWIPStatusBadges() {
        TestContextHelper.setExpected("All WIP Orders records display WIP status");
        SoftAssert softAssert = new SoftAssert();
        try {
            List<WebElement> badges = getDriver().findElements(By.xpath("//tbody/tr//span"));
            softAssert.assertEquals(badges.size(), 3, "WIP status badge count mismatch");
            for (int i = 0; i < badges.size(); i++) {
                softAssert.assertTrue(badges.get(i).isDisplayed(), "WIP status badge is not displayed for row " + (i + 1));
                softAssert.assertEquals(badges.get(i).getText(), "WIP", "WIP status mismatch for row " + (i + 1));
            }
            String actual = "All WIP Orders records display WIP status";
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

    @Test(priority = 18, description = "TC-CP-018", dependsOnMethods = "verifyCustomerProfileDefaultLoad", groups = {"ui_validation"})
    public void verifyIconsDisplayed() {
        TestContextHelper.setExpected("All tile and utility icons are displayed correctly");
        SoftAssert softAssert = new SoftAssert();
        try {
            //customer profile
            WebElement cp = getDriver().findElement(By.cssSelector("svg.lucide-user"));
            // Customer Details
            WebElement customerIcon = getDriver().findElement(By.cssSelector("svg.lucide-users"));
            // User Details
            WebElement userIcon = getDriver().findElement(By.cssSelector("svg.lucide-circle-user"));
            // Active Services
            WebElement activeServicesIcon = getDriver().findElement(By.cssSelector("svg.lucide-package"));
            // WIP Orders
            WebElement wipOrdersIcon = getDriver().findElement(By.cssSelector("svg.lucide-file-text"));
            // Refresh
            WebElement refreshIcon = getDriver().findElement(By.cssSelector("svg.lucide-refresh-cw"));
            // Logout
            WebElement logoutIcon = getDriver().findElement(By.cssSelector("svg.lucide-log-out"));
            softAssert.assertTrue(cp.isDisplayed(), "Customer profile icons are not displayed");
            softAssert.assertTrue(customerIcon.isDisplayed(), "Customer Details icon is not displayed");
            softAssert.assertTrue(userIcon.isDisplayed(), "User Details icon is not displayed");
            softAssert.assertTrue(activeServicesIcon.isDisplayed(), "Active Services icon is not displayed");
            softAssert.assertTrue(wipOrdersIcon.isDisplayed(), "WIP Orders icon is not displayed");
            softAssert.assertTrue(refreshIcon.isDisplayed(), "Refresh icon is not displayed");
            softAssert.assertTrue(logoutIcon.isDisplayed(), "Logout icon is not displayed");
            String actual = "All 7 icons displayed successfully";
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

    @Test(priority = 19, description = "Customer Details Search", dependsOnMethods = "verifyCustomerProfileDefaultLoad", dataProvider = "customerSearchData", dataProviderClass = DataProviders.class, groups = {"search"})
    public void verifyCustomerDetailsSearch(String tcId, String scenarioType, String searchValue, String expectedRows) {
        SoftAssert softAssert = new SoftAssert();
        int expectedCount = Integer.parseInt(expectedRows);
        try {
            TestContextHelper.setTestCaseId(tcId);
            TestContextHelper.setInput(scenarioType + ":" + searchValue);
            TestContextHelper.setExpected(expectedCount + " matching row(s) should be displayed");
            getDriver().findElement(By.xpath("//p[text()='Customer Details']")).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='Customer Details']")));
            WebElement search = getDriver().findElement(By.xpath("//input[contains(@placeholder,'Search')]"));
            search.clear();
            search.sendKeys(searchValue);
            By rowsLocator = By.xpath("//tbody/tr");
            getWait().until(ExpectedConditions.numberOfElementsToBe(rowsLocator, expectedCount));
            List<WebElement> rows = getDriver().findElements(rowsLocator);
            softAssert.assertEquals(rows.size(), expectedCount, tcId + " - Search result count mismatch");
            TestContextHelper.setActual(rows.size() + " matching row(s) displayed");

            softAssert.assertAll();

            System.out.println("PASS : " + tcId + " - " + rows.size() + " matching row(s) displayed");
        } catch (Throwable e) { //avoid long msg
            String reason;
            if (e instanceof org.openqa.selenium.TimeoutException) {
                reason = tcId + " - Expected " + expectedCount + " result(s), but actual results did not match.";
            } else {
                reason = tcId + " - " + e.getMessage();
            }
            TestContextHelper.setActual("Validation Failed");
            TestContextHelper.setFailureReason(reason);
            throw e;
        }
    }

    @Test(priority = 20, description = "User Details Search", dependsOnMethods = "verifyCustomerProfileDefaultLoad", dataProvider = "userSearchData", dataProviderClass = DataProviders.class, groups = {"search"})
    public void verifyUserDetailsSearch(String tcId, String scenarioType, String searchValue, String expectedRows) {
        SoftAssert softAssert = new SoftAssert();
        int expectedCount = Integer.parseInt(expectedRows);
        try {
            TestContextHelper.setTestCaseId(tcId);
            TestContextHelper.setInput(scenarioType + ":" + searchValue);
            TestContextHelper.setExpected(expectedCount + " matching row(s) should be displayed");
            getDriver().findElement(By.xpath("//p[text()='User Details']")).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='User Details']")));
            WebElement search = getDriver().findElement(By.xpath("//input[contains(@placeholder,'Search')]"));
            search.clear();
            search.sendKeys(searchValue);
            By rowsLocator = By.xpath("//tbody/tr");
            getWait().until(ExpectedConditions.numberOfElementsToBe(rowsLocator, expectedCount));
            List<WebElement> rows = getDriver().findElements(rowsLocator);
            softAssert.assertEquals(rows.size(), expectedCount, tcId + " - Search result count mismatch");
            TestContextHelper.setActual(rows.size() + " matching row(s) displayed");
            softAssert.assertAll();
            System.out.println("PASS : " + tcId + " - " + rows.size() + " matching row(s) displayed");
        } catch (Throwable e) {
            String reason;
            if (e instanceof org.openqa.selenium.TimeoutException) {
                reason = tcId + " - Expected " + expectedCount + " result(s), but actual results did not match.";
            } else {
                reason = tcId + " - " + e.getMessage();
            }
            TestContextHelper.setActual("Validation Failed");
            TestContextHelper.setFailureReason(reason);
            throw e;
        }
    }

    @Test(priority = 21, description = "Active Services Search", dependsOnMethods = "verifyCustomerProfileDefaultLoad", dataProvider = "activeServiceSearchData", dataProviderClass = DataProviders.class, groups = {"search"})
    public void verifyActiveServicesSearch(String tcId, String scenarioType, String searchValue, String expectedRows) {
        SoftAssert softAssert = new SoftAssert();
        int expectedCount = Integer.parseInt(expectedRows);
        try {
            TestContextHelper.setTestCaseId(tcId);
            TestContextHelper.setInput(scenarioType + ":" + searchValue);
            TestContextHelper.setExpected(expectedCount + " matching row(s) should be displayed");
            getDriver().findElement(By.xpath("//p[text()='Active Services']")).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='Active Services']")));
            WebElement search = getDriver().findElement(By.xpath("//input[contains(@placeholder,'Search')]"));
            search.clear();
            search.sendKeys(searchValue);
            By rowsLocator = By.xpath("//tbody/tr");
            getWait().until(ExpectedConditions.numberOfElementsToBe(rowsLocator, expectedCount));
            List<WebElement> rows = getDriver().findElements(rowsLocator);
            softAssert.assertEquals(rows.size(), expectedCount, tcId + " - Search result count mismatch");
            TestContextHelper.setActual(rows.size() + " matching row(s) displayed");
            softAssert.assertAll();
            System.out.println("PASS : " + tcId + " - " + rows.size() + " matching row(s) displayed");
        } catch (Throwable e) {
            String reason;
            if (e instanceof org.openqa.selenium.TimeoutException) {
                reason = tcId + " - Expected " + expectedCount + " result(s), but actual results did not match.";
            } else {
                reason = tcId + " - " + e.getMessage();
            }
            TestContextHelper.setActual("Validation Failed");
            TestContextHelper.setFailureReason(reason);
            throw e;
        }
    }

    @Test(priority = 22, description = "WIP Orders Search", dependsOnMethods = "verifyCustomerProfileDefaultLoad", dataProvider = "wipSearchData", dataProviderClass = DataProviders.class, groups = {"search"})
    public void verifyWIPOrdersSearch(String tcId, String scenarioType, String searchValue, String expectedRows) {
        SoftAssert softAssert = new SoftAssert();
        int expectedCount = Integer.parseInt(expectedRows);
        try {
            TestContextHelper.setTestCaseId(tcId);
            TestContextHelper.setInput(scenarioType + ":" + searchValue);
            TestContextHelper.setExpected(expectedCount + " matching row(s) should be displayed");
            getDriver().findElement(By.xpath("//p[text()='WIP Orders']")).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='WIP Orders']")));
            WebElement search = getDriver().findElement(By.xpath("//input[contains(@placeholder,'Search')]"));
            search.clear();
            search.sendKeys(searchValue);
            By rowsLocator = By.xpath("//tbody/tr");
            getWait().until(ExpectedConditions.numberOfElementsToBe(rowsLocator, expectedCount));
            List<WebElement> rows = getDriver().findElements(rowsLocator);
            softAssert.assertEquals(rows.size(), expectedCount, tcId + " - Search result count mismatch");
            TestContextHelper.setActual(rows.size() + " matching row(s) displayed");
            softAssert.assertAll();
            System.out.println("PASS : " + tcId + " - " + rows.size() + " matching row(s) displayed");
        } catch (Throwable e) {
            String reason;
            if (e instanceof org.openqa.selenium.TimeoutException) {
                reason = tcId + " - Expected " + expectedCount + " result(s), but actual results did not match.";
            } else {
                reason = tcId + " - " + e.getMessage();
            }
            TestContextHelper.setActual("Validation Failed");
            TestContextHelper.setFailureReason(reason);
            throw e;
        }
    }
}
