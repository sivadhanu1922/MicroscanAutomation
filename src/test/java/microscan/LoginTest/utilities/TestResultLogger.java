package utilities;

public class TestResultLogger {

    private static final ThreadLocal<String> expected = new ThreadLocal<>();
    private static final ThreadLocal<String> actual = new ThreadLocal<>();
    private static final ThreadLocal<String> scenario = new ThreadLocal<>();

    private static final ThreadLocal<String> testType = new ThreadLocal<>();

    public static void setScenario(String value) {
        scenario.set(value);
    }

    public static String getScenario() {
        return scenario.get();
    }

    public static void setTestType(String value) {
        testType.set(value);
    }

    public static String getTestType() {
        return testType.get();
    }

    public static void setExpected(String expectedResult) {
        expected.set(expectedResult);
    }

    public static void setActual(String actualResult) {
        actual.set(actualResult);
    }

    public static String getExpected() {
        return expected.get();
    }

    public static String getActual() {
        return actual.get();
    }

    public static void clear() {

        expected.remove();

        actual.remove();

        scenario.remove();

        testType.remove();

    }
}