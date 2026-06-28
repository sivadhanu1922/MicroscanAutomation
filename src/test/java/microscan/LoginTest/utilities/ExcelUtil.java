package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtil {

    private Workbook workbook;

    public ExcelUtil(String filePath) {

        try {

            FileInputStream fis = new FileInputStream(filePath);

            workbook = new XSSFWorkbook(fis);

        } catch (IOException e) {

            throw new RuntimeException(e);

        }

    }

    public int getRowCount(String sheetName) {

        Sheet sheet = workbook.getSheet(sheetName);

        return sheet.getLastRowNum();

    }

    public int getColumnCount(String sheetName) {

        Sheet sheet = workbook.getSheet(sheetName);

        return sheet.getRow(0).getLastCellNum();

    }

    public String getCellData(String sheetName, int row, int column) {

        Sheet sheet = workbook.getSheet(sheetName);

        DataFormatter formatter = new DataFormatter();

        return formatter.formatCellValue(
                sheet.getRow(row).getCell(column)
        );

    }

    public void closeWorkbook() {

        try {

            workbook.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}