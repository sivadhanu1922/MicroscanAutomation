package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;

    private static String reportPath;

    public static String getReportPath() {
        return reportPath;
    }

    public static ExtentReports getInstance() {

        if (extent == null) {

            // Generate unique report name
            String timeStamp =
                    new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
                            .format(new Date());

            // Store report path in class variable
            reportPath = "test-output/ExtentReport_" + timeStamp + ".html";

            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter(reportPath);

            sparkReporter.config().setReportName("Customer Portal Automation Report");
            sparkReporter.config().setDocumentTitle("Automation Test Report");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // System Information
            extent.setSystemInfo("Tester", "Jayasri");
            extent.setSystemInfo("Project", "Customer Portal Automation");
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }

        return extent;
    }
}