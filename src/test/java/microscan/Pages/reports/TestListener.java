package microscan.Pages.reports;

import microscan.Pages.BaseTest;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class TestListener implements ITestListener, ISuiteListener {

    public static final List<TestResultData> results = new ArrayList<>();

    @Override
    public void onStart(ISuite suite) {
        results.clear();
    }

    @Override
    public void onTestStart(ITestResult result) {
        TestContextHelper.startTest();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        saveResult(result, "PASS");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshot = ScreenshotUtil.capture(BaseTest.getDriver());
        TestContextHelper.getCurrent().setScreenshotPath(screenshot);
        saveResult(result, "FAIL");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        saveResult(result, "SKIPPED");
    }

    private void saveResult(ITestResult result, String status) {
        TestResultData data = TestContextHelper.getCurrent();//Get current obj test data
        data.setStatus(status);
        String className = result.getTestClass().getRealClass().getSimpleName();//microscan.CustomerProfile
        data.setClassName(className);
        data.setMethodName(result.getMethod().getMethodName());
        data.setPriority(result.getMethod().getPriority());
        data.setSuiteName(result.getTestContext().getSuite().getName());
        data.setTestName(result.getTestContext().getName());//testng test name
        data.setExecutedAt(Instant.ofEpochMilli(result.getStartMillis()).atZone(ZoneId.systemDefault()).toLocalDateTime());//instant:ms->2026-06-24T10:30:15Z atzone->Converts UTC(asia) time into system timezone-> [2026-06-24 10:30:15 IST] localdatetime ->[2026-06-24T10:30:15]
        data.setDurationMillis(result.getEndMillis() - result.getStartMillis());
        if (data.getFailureReason() == null || data.getFailureReason().isBlank()) {
            data.setFailureReason(result.getThrowable() != null ? result.getThrowable().getMessage() : "Not Captured");//if failure reason happen then put that else put not captured
        }
        data.setModuleName(className);
        if (data.getTestCaseId() == null || data.getTestCaseId().isBlank()) { //srch
            String description = result.getMethod().getDescription(); //get method inside description
            data.setTestCaseId(description != null ? description : "N/A");
        }
        results.add(data);
    }

    @Override
    public void onFinish(ISuite suite) {
        PdfReportGenerator.generate(results);
        TestContextHelper.clear();
    }
}