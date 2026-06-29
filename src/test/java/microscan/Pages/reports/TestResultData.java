package microscan.Pages.reports;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestResultData {

    private String moduleName;
    private String className;
    private String methodName;
    private String testCaseId;
    private String inputData;
    private String expectedResult;
    private String actualResult;
    private String status;
    private String screenshotPath;
    private String failureReason;
    private long durationMillis;
    private String suiteName;
    private String testName;
}