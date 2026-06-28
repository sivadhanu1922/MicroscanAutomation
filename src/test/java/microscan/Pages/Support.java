package microscan.Pages;

import microscan.Pages.reports.TestContextHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class Support extends BaseTest {

    @Test(priority = 1, description = "TC-SP-001", groups = {"navigation"})
    public void verifySupportPageDefaultLoad() {
        TestContextHelper.setExpected("Support page loads with title, welcome card, customer support services section, escalation matrices, locations section and Create Ticket button");
        SoftAssert softAssert = new SoftAssert();
        try {
            getDriver().findElement(By.xpath("//span[text()='Support']")).click();
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Support']")));
            softAssert.assertTrue(getDriver().findElement(By.xpath("//h1[text()='Support']")).isDisplayed(), "Support page title is not displayed");
            softAssert.assertTrue(getDriver().findElement(By.xpath("//*[contains(text(),'Welcome')]")).isDisplayed(), "Welcome card is not displayed");
            softAssert.assertTrue(getDriver().findElement(By.xpath("//h2[text()='Customer Support Services']")).isDisplayed(), "Customer Support Services section is not displayed");
            softAssert.assertTrue(getDriver().findElement(By.xpath("//h2[text()='Enterprise Escalation Matrix']")).isDisplayed(), "Enterprise Escalation Matrix section is not displayed");
            softAssert.assertTrue(getDriver().findElement(By.xpath("//h2[text()='LLC Escalation Matrix']")).isDisplayed(), "LLC Escalation Matrix section is not displayed");
            softAssert.assertTrue(getDriver().findElement(By.xpath("//h2[text()='Our Locations']")).isDisplayed(), "Our Locations section is not displayed");
            WebElement createTicketButton = getDriver().findElement(By.xpath("//button[text()='Create Ticket']"));
            softAssert.assertTrue(createTicketButton.isDisplayed(), "Create Ticket button is not displayed");
            softAssert.assertTrue(createTicketButton.isEnabled(), "Create Ticket button is disabled");
            String actual = "Support page title, welcome card, Customer Support Services section, Enterprise Escalation Matrix, LLC Escalation Matrix, Our Locations section and Create Ticket button verified successfully";
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

    @Test(priority = 2, description = "TC-SP-002", dependsOnMethods = "verifySupportPageDefaultLoad", groups = {"data_accuracy"})
    public void verifyLLCSupportInquiryCard() {
        TestContextHelper.setExpected("LLC support card displays correct email, phone number and support availability");
        SoftAssert softAssert = new SoftAssert();
        try {
            WebElement llcCard = getDriver().findElement(By.xpath("//h3[contains(text(),'LLC')]/parent::div"));
            softAssert.assertTrue(llcCard.getText().contains("llc.support@microscan.co.in"), "LLC support email mismatch");
            softAssert.assertTrue(llcCard.getText().contains("+91 22-6868-0098"), "LLC support phone number mismatch");
            softAssert.assertTrue(llcCard.getText().contains("24×7"), "LLC support availability mismatch");
            String actual = "LLC support card displays correct email, phone number and support timing";
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

    @Test(priority = 3, description = "TC-SP-003", dependsOnMethods = "verifySupportPageDefaultLoad", groups = {"data_accuracy"})
    public void verifyP2PSupportInquiryCard() {
        TestContextHelper.setExpected("P2P support card displays correct email, phone number and support availability");
        SoftAssert softAssert = new SoftAssert();
        try {
            WebElement p2pCard = getDriver().findElement(By.xpath("//h3[contains(text(),'P2P support')]/parent::div"));
            softAssert.assertTrue(p2pCard.getText().contains("enterprise.support@microscan.co.in"), "P2P support email mismatch");
            softAssert.assertTrue(p2pCard.getText().contains("+91 22-6868-0001"), "P2P support phone number mismatch");
            softAssert.assertTrue(p2pCard.getText().contains("24×7"), "P2P support availability mismatch");
            String actual = "P2P support card displays correct email, phone number and support timing";
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

    @Test(priority = 4, description = "TC-SP-004", dependsOnMethods = "verifySupportPageDefaultLoad", groups = {"data_accuracy"})
    public void verifyILLSupportInquiryCard() {
        TestContextHelper.setExpected("ILL support card displays correct email, phone number and support availability");
        SoftAssert softAssert = new SoftAssert();
        try {
            WebElement illCard = getDriver().findElement(By.xpath("//h3[contains(text(),'ILL')]/parent::div"));
            softAssert.assertTrue(illCard.getText().contains("enterprise.support@microscan.co.in"), "ILL support email mismatch");
            softAssert.assertTrue(illCard.getText().contains("+91 22-6868-0001"), "ILL support phone number mismatch");
            softAssert.assertTrue(illCard.getText().contains("24×7"), "ILL support availability mismatch");
            String actual = "ILL support card displays correct email, phone number and support timing";
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

    @Test(priority = 5, description = "TC-SP-005", dependsOnMethods = "verifySupportPageDefaultLoad", groups = {"data_accuracy"})
    public void verifyEnterpriseEscalationMatrix() {
        TestContextHelper.setExpected("Enterprise Escalation Matrix displays all escalation levels, email IDs and phone number");
        SoftAssert softAssert = new SoftAssert();
        try {
            WebElement enterpriseMatrix = getDriver().findElement(By.xpath("//h2[text()='Enterprise Escalation Matrix']/parent::div"));
            String matrixText = enterpriseMatrix.getText();
            softAssert.assertTrue(matrixText.contains("L1 - Service Desk"), "L1 - Service Desk is not displayed");
            softAssert.assertTrue(matrixText.contains("enterprise.support@microscan.co.in"), "L1 email ID is incorrect");
            softAssert.assertTrue(matrixText.contains("+91 22-68680001"), "L1 phone number is incorrect");
            softAssert.assertTrue(matrixText.contains("L2 - Shift lead"), "L2 - Shift lead is not displayed");
            softAssert.assertTrue(matrixText.contains("shoc.Shiftlead@microscan.co.in"), "L2 email ID is incorrect");
            softAssert.assertTrue(matrixText.contains("L3 - Rohan Nakhawa / Santosh"), "L3 escalation details are incorrect");
            softAssert.assertTrue(matrixText.contains("rohan.nakhawa@microscan.co.in"), "Rohan email ID is incorrect");
            softAssert.assertTrue(matrixText.contains("santosh@microscan.co.in"), "Santosh email ID is incorrect");
            softAssert.assertTrue(matrixText.contains("L4 - Prashant Pradhan"), "L4 escalation details are incorrect");
            softAssert.assertTrue(matrixText.contains("prashant.pradhan@microscan.co.in"), "Prashant email ID is incorrect");
            String actual = "Enterprise Escalation Matrix details verified successfully";
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

    @Test(priority = 6, description = "TC-SP-006", dependsOnMethods = "verifySupportPageDefaultLoad", groups = {"data_accuracy"})
    public void verifyLLCEscalationMatrix() {
        TestContextHelper.setExpected("LLC Escalation Matrix displays all escalation levels, email IDs and phone number");
        SoftAssert softAssert = new SoftAssert();
        try {
            WebElement llcMatrix = getDriver().findElement(By.xpath("//h2[text()='LLC Escalation Matrix']/parent::div"));
            String matrixText = llcMatrix.getText();
            softAssert.assertTrue(matrixText.contains("L1 - Service Desk"), "L1 - Service Desk is not displayed");
            softAssert.assertTrue(matrixText.contains("enterprise.support@microscan.co.in"), "L1 email ID is incorrect");
            softAssert.assertTrue(matrixText.contains("022-68680001"), "L1 phone number is incorrect");
            softAssert.assertTrue(matrixText.contains("L2 - Shift lead"), "L2 - Shift lead is not displayed");
            softAssert.assertTrue(matrixText.contains("shoc.Shiftlead@microscan.co.in"), "L2 email ID is incorrect");
            softAssert.assertTrue(matrixText.contains("L3 - Rohan Nakhawa / Niwant Rachikar"), "L3 escalation details are incorrect");
            softAssert.assertTrue(matrixText.contains("rohan.nakhawa@microscan.co.in"), "Rohan email ID is incorrect");
            softAssert.assertTrue(matrixText.contains("niwant.rachikar@microscan.co.in"), "Niwant email ID is incorrect");
            softAssert.assertTrue(matrixText.contains("L4 - Prashant Pradhan"), "L4 escalation details are incorrect");
            softAssert.assertTrue(matrixText.contains("prashant.pradhan@microscan.co.in"), "Prashant email ID is incorrect");
            String actual = "LLC Escalation Matrix details verified successfully";
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

    @Test(priority = 7, description = "TC-SP-007", dependsOnMethods = "verifySupportPageDefaultLoad", groups = {"data_accuracy"})
    public void verifyLocationsAndMapImage() {
        TestContextHelper.setExpected("Mumbai office, Pune office details and office locations map are displayed");
        SoftAssert softAssert = new SoftAssert();
        try {
            WebElement locationsCard = getDriver().findElement(By.xpath("//h2[text()='Our Locations']/parent::div"));
            String locationText = locationsCard.getText();
            softAssert.assertTrue(locationText.contains("Head Office - Mumbai"), "Mumbai office heading is not displayed");
            softAssert.assertTrue(locationText.contains("Everest Grande"), "Mumbai office address is incorrect");
            softAssert.assertTrue(locationText.contains("Mumbai - 400053"), "Mumbai office pincode is incorrect");
            softAssert.assertTrue(locationText.contains("Mon – Fri | 9:30 a.m. to 6:30 p.m."), "Mumbai office timing is incorrect");
            softAssert.assertTrue(locationText.contains("Branch Office - Pune"), "Pune office heading is not displayed");
            softAssert.assertTrue(locationText.contains("Teerth Technospace"), "Pune office address is incorrect");
            softAssert.assertTrue(locationText.contains("Pune"), "Pune office location is incorrect");
            WebElement mapImage = getDriver().findElement(By.xpath("//img[@alt='Office locations map']"));
            softAssert.assertTrue(mapImage.isDisplayed(), "Office locations map image is not displayed");
            String actual = "Mumbai office, Pune office details and map image verified successfully";
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

    @Test(priority = 8, description = "TC-SP-008", dependsOnMethods = "verifySupportPageDefaultLoad", groups = {"ui_validation"})
    public void verifyMailAndPhoneIcons() {
        TestContextHelper.setExpected("All mail and phone icons are displayed correctly in support inquiry cards");
        SoftAssert softAssert = new SoftAssert();
        try {
            WebElement headphoneIcons = getDriver().findElement(By.cssSelector("svg.lucide-headphones"));
            List<WebElement> mailIcon = getDriver().findElements(By.cssSelector("svg.lucide-mail"));
            List<WebElement> phoneIcons = getDriver().findElements(By.cssSelector("svg.lucide-phone"));
            softAssert.assertEquals(mailIcon.size(), 3, "Mail icon count mismatch");
            softAssert.assertEquals(phoneIcons.size(), 3, "Phone icon count mismatch");
            for (int i = 0; i < mailIcon.size(); i++) {
                softAssert.assertTrue(mailIcon.get(i).isDisplayed(), "Mail icon is not displayed for card " + (i + 1));
            }
            for (int i = 0; i < phoneIcons.size(); i++) {
                softAssert.assertTrue(phoneIcons.get(i).isDisplayed(), "Phone icon is not displayed for card " + (i + 1));
            }
            softAssert.assertTrue(headphoneIcons.isDisplayed(), "Headphone icons are incorrect");
            String actual = "3 mail icons and 3 phone icons displayed successfully";
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


}