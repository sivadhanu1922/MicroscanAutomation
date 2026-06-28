package microscan.Pages.reports;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelUtils {

    public static Object[][] getData(String filePath, String sheetName) {

        try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getLastCellNum();
            Object[][] data = new Object[rowCount - 1][colCount];
            for (int i = 1; i < rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    DataFormatter formatter = new DataFormatter();
                    data[i - 1][j] = formatter.formatCellValue(sheet.getRow(i).getCell(j)).trim();
                }
            }
            return data;
        } catch (Exception e) {
            throw new RuntimeException("Unable to read Excel sheet: " + sheetName, e);
        }
    }
}