package reports;

import model.TestResultData;
import org.testng.ITestResult;
import utilities.TestCaseIdGenerator;
import utilities.TestResultLogger;

import java.util.Arrays;

public class ReportCollector {

    public static void collect(ITestResult result,
                               String status,
                               String screenshotPath) {

        TestResultData data = new TestResultData();

        // Module Name
        String module = result.getTestClass()
                .getRealClass()
                .getSimpleName()
                .replace("Test", "");

        // Auto Generate Test Case ID
        data.setTcId(TestCaseIdGenerator.generate(module));

        // Module
        data.setModule(module);

        data.setScenario(TestResultLogger.getScenario());

        data.setTestType(TestResultLogger.getTestType());

        // Test Method
        data.setTestCase(result.getMethod().getMethodName());

        // Test Data
//        data.setTestData(Arrays.toString(result.getParameters()));

        Object[] params = result.getParameters();

        String testData = "N/A";

        if (params.length == 1) {

            testData = params[0].toString();

        } else if (params.length == 2) {

            testData =
                    "Username : " + params[0]
                            + "\nPassword : " + params[1];

        }

        data.setTestData(testData);

        // Status
        data.setStatus(status);

        // Duration
        long duration =
                result.getEndMillis() - result.getStartMillis();

        data.setDuration(duration + " ms");

        // Screenshot
        data.setScreenshotPath(screenshotPath);

        // Actual Result
        if (result.getThrowable() != null) {

//            data.setActualResult(result.getThrowable().getMessage());
            String message = result.getThrowable().getMessage();

            if (message != null) {

                int index = message.indexOf("Build info:");

                if (index != -1) {
                    message = message.substring(0, index).trim();
                }

            }

            data.setActualResult(message);

        } else {

            data.setActualResult(TestResultLogger.getActual());

        }

        // Expected Result
        data.setExpectedResult(TestResultLogger.getExpected());

        // Store Result
        TestResultManager.addResult(data);
    }
}