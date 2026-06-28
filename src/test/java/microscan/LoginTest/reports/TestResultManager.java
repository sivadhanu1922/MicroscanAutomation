package reports;
import model.TestResultData;

import java.util.ArrayList;
import java.util.List;

public class TestResultManager {

    private static final List<TestResultData> results = new ArrayList<>();

    public static void addResult(TestResultData result) {
        results.add(result);
    }

    public static List<TestResultData> getResults() {
        return results;
    }

    public static void clearResults() {
        results.clear();
    }
}
