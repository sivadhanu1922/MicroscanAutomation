package listeners;
import base.BaseTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import reports.PDFReportGenerator;
import utilities.PDFReportUtil;
import reports.ReportCollector;
import utilities.ScreenshotUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentManager;
import utilities.ExtentLogger;
import utilities.TestResultLogger;

public class ExtentTestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {

        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());

        test.set(extentTest);

        ExtentLogger.setTest(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        test.get().pass("Test Passed");

        ReportCollector.collect(
                result,
                "PASS",
                null
        );
        TestResultLogger.clear();
    }


    @Override
    public void onTestFailure(ITestResult result) {

        test.get().fail(result.getThrowable());

        String screenshotPath = null;

        try {

            screenshotPath = ScreenshotUtil.captureScreenshot(
                    BaseTest.driver,
                    result.getMethod().getMethodName()
            );

            test.get().fail(
                    "Failure Screenshot",
                    MediaEntityBuilder.createScreenCaptureFromPath(
                            screenshotPath
                    ).build()
            );

        } catch (Exception e) {

            test.get().warning(
                    "Unable to attach screenshot : " + e.getMessage()
            );
        }

        ReportCollector.collect(
                result,
                "FAIL",
                screenshotPath
        );
        TestResultLogger.clear();
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        test.get().skip("Test Skipped");

        ReportCollector.collect(
                result,
                "SKIPPED",
                null
        );
        TestResultLogger.clear();
    }



    @Override
    public void onFinish(ITestContext context) {

        extent.flush();

        PDFReportGenerator.generate();

    }

}
