package microscan.Pages.reports;

import org.testng.annotations.DataProvider;

public class DataProviders {

    private static final String FILE_PATH = "src/test/resources/testdata/searchCustomerProfile.xlsx";
    @DataProvider(name = "customerSearchData")
    public static Object[][] customerSearchData() {
        return ExcelUtils.getData(FILE_PATH, "CustomerDetails");
    }

    @DataProvider(name = "userSearchData")
    public static Object[][] userSearchData() {
        return ExcelUtils.getData(FILE_PATH, "UserDetails");
    }

    @DataProvider(name = "activeServiceSearchData")
    public static Object[][] activeServiceSearchData() {
        return ExcelUtils.getData(FILE_PATH, "ActiveServices");
    }

    @DataProvider(name = "wipSearchData")
    public static Object[][] wipSearchData() {
        return ExcelUtils.getData(FILE_PATH, "WIPOrders");
    }
}