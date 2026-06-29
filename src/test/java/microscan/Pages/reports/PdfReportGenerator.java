package microscan.Pages.reports;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PdfReportGenerator {

    private static final Color Hbg = new DeviceRgb(43, 43, 122);
    private static final Color TotalBg = new DeviceRgb(225, 230, 245);
    private static final Color TotalTxt = new DeviceRgb(38, 50, 90);
    private static final Color PassBg = new DeviceRgb(232, 248, 240);
    private static final Color PassTxt = new DeviceRgb(15, 110, 70);
    private static final Color FailBg = new DeviceRgb(252, 232, 232);
    private static final Color FailTxt = new DeviceRgb(155, 25, 35);
    private static final Color SkipBg = new DeviceRgb(253, 240, 219);
    private static final Color SkipTxt = new DeviceRgb(160, 95, 5);

    public static void generate(List<TestResultData> results) {
        try {
            File folder = new File("reports");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String path = "reports/Report_" + timestamp + ".pdf";
            PdfWriter writer = new PdfWriter(path);//pdf
            PdfDocument pdf = new PdfDocument(writer);//structure
            Document document = new Document(pdf, PageSize.A4.rotate());//layouts
            document.setMargins(15, 15, 15, 15);
            document.add(new Paragraph("\n"));
            Paragraph title = new Paragraph("MICROSCAN AUTOMATION TEST REPORT").setFontSize(16).setFontColor(ColorConstants.WHITE).setBackgroundColor(Hbg).setPadding(8).setTextAlignment(TextAlignment.CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));
            addReportInfo(document);
            document.add(new Paragraph("\n"));
            addSummary(document, results);
            addExecutionDetails(document, results);
            document.add(new AreaBreak());
            addDetailTable(document, results);//also added failureReason
            document.close();
            System.out.println("PDF generated successfully: " + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addReportInfo(Document document) {
        document.add(new Paragraph("Report Information").setFontSize(14).setMarginBottom(10).setFontColor(Hbg));
        Table table = new Table(UnitValue.createPercentArray(new float[]{50, 50}));
        table.setWidth(UnitValue.createPercentValue(100));
        table.addCell(createInfoCell("Prepared By: Navyashree S"));
        table.addCell(createInfoCell("Department: QA"));
        table.addCell(createInfoCell("Automation Tool: Selenium + TestNG"));
        table.addCell(createInfoCell("Browser: Chrome"));
        document.add(table);
    }

    private static void addSummary(Document document, List<TestResultData> results) {
        document.add(new Paragraph("Execution Summary").setFontSize(14).setMarginBottom(10).setFontColor(Hbg));
        int total = results.size();//obj count
        int pass = (int) results.stream().filter(r -> "PASS".equals(r.getStatus())).count();
        int fail = (int) results.stream().filter(r -> "FAIL".equals(r.getStatus())).count();
        int skipped = (int) results.stream().filter(r -> "SKIPPED".equals(r.getStatus())).count();
        Table summary = new Table(4);
        summary.setWidth(UnitValue.createPercentValue(100));
        summary.addCell(createSummaryCell("Total", total, TotalBg, TotalTxt));
        summary.addCell(createSummaryCell("Passed", pass, PassBg, PassTxt));
        summary.addCell(createSummaryCell("Failed", fail, FailBg, FailTxt));
        summary.addCell(createSummaryCell("Skipped", skipped, SkipBg, SkipTxt));
        document.add(summary);
    }

    private static Cell createSummaryCell(String title, int value, Color bgColor, Color textColor) {
        return new Cell().add(new Paragraph(title + "\n\n" + value).setFontSize(14)).setFontColor(textColor).setBackgroundColor(bgColor).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setPadding(15);
    }

    private static void addExecutionDetails(Document document, List<TestResultData> results) {
        if (results.isEmpty()) {
            return;
        }
        long uniqueClasses = results.stream().map(TestResultData::getClassName).distinct().count();
        long totalDuration = results.stream().mapToLong(TestResultData::getDurationMillis).sum();                                   //extracts only the duration from every object, Because durations are numbers (long values) so use maptolong
        TestResultData first = results.get(0);//suite name, test name -> common (1st obj)
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Execution Details").setFontSize(14).setMarginBottom(10).setFontColor(Hbg));
        Table details = new Table(2);
        details.setWidth(UnitValue.createPercentValue(100));
        details.addCell(createInfoCell("Suite Name: " + value(first.getSuiteName())));
        details.addCell(createInfoCell("Test Name: " + value(first.getTestName())));
        details.addCell(createInfoCell("Classes Executed: " + uniqueClasses));
        details.addCell(createInfoCell("Methods Executed: " + results.size()));
        details.addCell(createInfoCell("Total Execution Time: " + (totalDuration / 1000) + " seconds")); //ms->s
        details.addCell(createInfoCell("Generated On: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss"))));
        document.add(details);
    }

    private static void addDetailTable(Document document, List<TestResultData> results) {
        document.add(new Paragraph("Detailed Test Results").setFontSize(14).setMarginBottom(10).setFontColor(Hbg));
        Table table = new Table(UnitValue.createPercentArray(new float[]{8, 12, 18, 8, 6, 20, 16, 4}));//add=92(100)
        table.setWidth(UnitValue.createPercentValue(100));
        addHeader(table, "TC ID");
        addHeader(table, "Module");
        addHeader(table, "Method");
        addHeader(table, "Input");
        addHeader(table, "Status");
        addHeader(table, "Expected");
        addHeader(table, "Actual");
        addHeader(table, "Duration");
        int row = 0;
        for (TestResultData data : results) {
            Color rowColor = row % 2 == 0 ? new DeviceRgb(224, 224, 224) : ColorConstants.WHITE;
            table.addCell(createRowCell(value(data.getTestCaseId()), rowColor));
            table.addCell(createRowCell(value(data.getModuleName()), rowColor));
            table.addCell(createRowCell(value(data.getMethodName()), rowColor));
            table.addCell(createRowCell(value(data.getInputData()), rowColor));
            table.addCell(createStatusCell(value(data.getStatus())));
            table.addCell(createRowCell(value(data.getExpectedResult()), rowColor));//exp
            if ("FAIL".equalsIgnoreCase(data.getStatus())) {//actual
                Link link = new Link("View Details", PdfAction.createGoTo(data.getTestCaseId()));                                   //when clicks, find the destination named TC005 and go
                Cell linkCell = new Cell().add(new Paragraph(link).setFontSize(8).setFontColor(new DeviceRgb(255, 51, 51)).setUnderline()).setBackgroundColor(rowColor).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setPadding(4);
                table.addCell(linkCell);
            } else {
                table.addCell(createRowCell(value(data.getActualResult()), rowColor));
            }
            table.addCell(createRowCell(data.getDurationMillis() + " ms", rowColor));
            row++;
        }
        table.setKeepTogether(false);//table not fit(current page), split ->multiple pages
        document.add(table);
        addFailureDetails(document, results);
    }

    private static void addHeader(Table table, String text) {
        table.addHeaderCell(new Cell().add(new Paragraph(text)).setFontColor(ColorConstants.WHITE).setFontSize(9).setBackgroundColor(Hbg).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setPadding(8));
    }

    private static Cell createRowCell(String text, Color color) {
        return new Cell().add(new Paragraph(text).setFontSize(8).setFontColor(ColorConstants.BLACK)).setBackgroundColor(color).setTextAlignment(TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE).setPadding(4).setKeepTogether(true);
    }

    private static Cell createStatusCell(String status) {
        Color color;
        switch (status) {
            case "PASS":
                color = new DeviceRgb(82, 218, 73);
                break;
            case "FAIL":
                color = new DeviceRgb(255, 51, 51);
                break;
            default:
                color = new DeviceRgb(255, 155, 53);
        }
        return new Cell().add(new Paragraph(status).setFontSize(8)).setBackgroundColor(color).setFontColor(ColorConstants.WHITE).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setKeepTogether(true);
    }

    private static void addFailureDetails(Document document, List<TestResultData> results) {
        boolean hasFailures = results.stream().anyMatch(r -> "FAIL".equals(r.getStatus()));//atleast 1 get
        if (!hasFailures) {
            return;
        }
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Failure Details").setFontSize(16).setMarginBottom(15).setFontColor(Hbg));
        for (TestResultData data : results) {
            if (!"FAIL".equalsIgnoreCase(data.getStatus())) {
                continue;//skip,pass
            }
            Paragraph heading = new Paragraph(value(data.getTestCaseId())).setDestination(data.getTestCaseId()).setFontSize(14).setFontColor(new DeviceRgb(255, 51, 51)).setMarginTop(20);//named destination.
            Div failureSection = new Div();//combine 1 failure
            failureSection.setKeepTogether(false);//split across pages
            failureSection.setMarginTop(15);
            failureSection.setMarginBottom(15);
            failureSection.add(heading);

            Table detailTable = new Table(UnitValue.createPercentArray(new float[]{25, 75}));
            detailTable.setWidth(UnitValue.createPercentValue(100));
            detailTable.addCell(createInfoCell("Class Name"));
            detailTable.addCell(createInfoCell(value(data.getClassName())));
            detailTable.addCell(createInfoCell("Method Name"));
            detailTable.addCell(createInfoCell(value(data.getMethodName())));
            detailTable.addCell(createInfoCell("Expected"));
            detailTable.addCell(createInfoCell(value(data.getExpectedResult())));
            detailTable.addCell(createInfoCell("Failure Reason"));
            detailTable.addCell(createInfoCell(value(data.getFailureReason())));

            failureSection.add(detailTable);
            if (data.getScreenshotPath() != null && !data.getScreenshotPath().isBlank()) {
                try {
                    Image image = new Image(ImageDataFactory.create(data.getScreenshotPath()));
                    image.scaleToFit(500, 350);
                    image.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    failureSection.add(new Paragraph("Screenshot").setMarginTop(10));
                    failureSection.add(image);
                } catch (Exception e) {
                    failureSection.add(new Paragraph("Unable to load screenshot"));
                }
            }
            document.add(failureSection);
        }
    }

    private static Cell createInfoCell(String text) {
        return new Cell().add(new Paragraph(text).setFontSize(8).setFontColor(ColorConstants.BLACK)).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setPadding(4);
    }

    private static String value(String text) {
        return text == null || text.isBlank() ? "N/A" : text;
    }
}