package dataprovider;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

public class GenericDataProvider {

    @DataProvider(name = "excelData")
    public static Object[][] getExcelData(Method method) {

        String sheetName = method.getDeclaringClass().getSimpleName()
                .replace("Test", "");

        String scenario = method.getName();

        int parameterCount = method.getParameterCount();

        return ExcelDataProvider.getData(sheetName, scenario, method);
    }
}