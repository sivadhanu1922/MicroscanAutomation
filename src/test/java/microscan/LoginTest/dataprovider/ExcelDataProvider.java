package dataprovider;

import utilities.ExcelUtil;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;

public class ExcelDataProvider {

    private static final String FILE_PATH =
            System.getProperty("user.dir") + "/testdata/TestData.xlsx";


    public static Object[][] getData(String sheetName,
                                     String scenario,
                                     Method method) {

        ExcelUtil excel = new ExcelUtil(FILE_PATH);

        List<Object[]> data = new ArrayList<>();

        int rowCount = excel.getRowCount(sheetName);

        for (int i = 1; i <= rowCount; i++) {

            String excelScenario = excel.getCellData(sheetName, i, 0);

            if (!excelScenario.equalsIgnoreCase(scenario))
                continue;

            String dataType = excel.getCellData(sheetName, i, 1).trim().toUpperCase();

            Object[] row;

            switch (dataType) {

                case "LOGIN":

                    row = new Object[]{
                            excel.getCellData(sheetName, i, 2),
                            excel.getCellData(sheetName, i, 3)
                    };
                    break;

                case "USERNAME":

                    row = new Object[]{
                            excel.getCellData(sheetName, i, 2)
                    };
                    break;

                case "PASSWORD":

                    row = new Object[]{
                            excel.getCellData(sheetName, i, 3)
                    };
                    break;

                case "OTP":

                    row = new Object[]{
                            excel.getCellData(sheetName, i, 2)
                    };
                    break;

                case "NONE":

                    row = new Object[0];
                    break;

                case "NEWPASSWORD":

                    row = new Object[]{
                            excel.getCellData(sheetName, i, 2)
                    };
                    break;
                case "CONFIRMPASSWORD":

                    row = new Object[]{
                            excel.getCellData(sheetName, i, 3)
                    };
                    break;


                default:

                    throw new RuntimeException("Invalid DataType '" + dataType +
                            "' for scenario: " + scenario);
            }

            data.add(row);
        }

        excel.closeWorkbook();

        return data.toArray(new Object[0][]);
    }


    }

