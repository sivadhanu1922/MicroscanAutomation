package microscan.Pages.reports;

public class TestContextHelper {

    private static TestResultData current = new TestResultData();

    public static void startTest() {
        current = new TestResultData();
    }

    public static TestResultData getCurrent() {
        return current;
    }

    public static void setInput(String input) {
        current.setInputData(input);
    }

    public static void setExpected(String expected) {
        current.setExpectedResult(expected);
    }

    public static void setActual(String actual) {
        current.setActualResult(actual);
    }

    public static void setTestCaseId(String testCaseId) {
        current.setTestCaseId(testCaseId);
    }

    public static void setFailureReason(String reason) {
        current.setFailureReason(reason);
    }

    public static void clear() {
        current = null;
    }
}