package utilities;

import com.aventstack.extentreports.ExtentTest;

public class ExtentLogger {

    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static void setTest(ExtentTest extentTest) {
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void info(String message) {
        test.get().info(message);
    }

    public static void pass(String message) {
        test.get().pass(message);
    }

    public static void fail(String message) {
        test.get().fail(message);
    }

    public static void warning(String message) {
        test.get().warning(message);
    }
}