package reports;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import model.TestResultData;
import reports.TestResultManager;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PDFReportGenerator {

    // =========================================================
    //  BRAND COLORS
    // =========================================================

    private static final BaseColor COLOR_HEADER_BG       = new BaseColor(26,  54,  93);   // Dark navy blue
    private static final BaseColor COLOR_HEADER_TEXT      = BaseColor.WHITE;
    private static final BaseColor COLOR_TITLE_BLUE       = new BaseColor(21,  86, 145);   // Deep blue
    private static final BaseColor COLOR_SEPARATOR        = new BaseColor(26,  54,  93);

    private static final BaseColor COLOR_PASS_BG          = new BaseColor(198, 239, 206);  // Light green
    private static final BaseColor COLOR_PASS_TEXT        = new BaseColor(0,   97,  0);    // Dark green
    private static final BaseColor COLOR_FAIL_BG          = new BaseColor(255, 199, 206);  // Light red
    private static final BaseColor COLOR_FAIL_TEXT        = new BaseColor(156, 0,   6);    // Dark red
    private static final BaseColor COLOR_SKIP_BG          = new BaseColor(255, 235, 156);  // Light yellow
    private static final BaseColor COLOR_SKIP_TEXT        = new BaseColor(124, 101, 2);    // Brown

    private static final BaseColor COLOR_ROW_ODD          = new BaseColor(242, 246, 252);  // Very light blue-grey
    private static final BaseColor COLOR_ROW_EVEN         = BaseColor.WHITE;
    private static final BaseColor COLOR_SECTION_BORDER   = new BaseColor(26,  54,  93);
    private static final BaseColor COLOR_SECTION_BG       = new BaseColor(235, 241, 250);

    private static final BaseColor COLOR_SUMMARY_TOTAL_BG = new BaseColor(213, 227, 246);
    private static final BaseColor COLOR_INFO_LABEL_BG    = new BaseColor(235, 241, 250);

    // =========================================================
    //  FONTS
    // =========================================================

    private static final Font FONT_TITLE = new Font(
            Font.FontFamily.HELVETICA, 26, Font.BOLD, COLOR_TITLE_BLUE);

    private static final Font FONT_SUBTITLE = new Font(
            Font.FontFamily.HELVETICA, 14, Font.NORMAL, new BaseColor(80, 80, 80));

    private static final Font FONT_SECTION_HEADING = new Font(
            Font.FontFamily.HELVETICA, 13, Font.BOLD, COLOR_TITLE_BLUE);

    private static final Font FONT_TABLE_HEADER = new Font(
            Font.FontFamily.HELVETICA, 10, Font.BOLD, COLOR_HEADER_TEXT);

    private static final Font FONT_TABLE_BODY = new Font(
            Font.FontFamily.HELVETICA, 9, Font.NORMAL, new BaseColor(40, 40, 40));

    private static final Font FONT_TABLE_BODY_BOLD = new Font(
            Font.FontFamily.HELVETICA, 9, Font.BOLD, new BaseColor(40, 40, 40));

    private static final Font FONT_INFO_LABEL = new Font(
            Font.FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(40, 40, 40));

    private static final Font FONT_INFO_VALUE = new Font(
            Font.FontFamily.HELVETICA, 10, Font.NORMAL, new BaseColor(40, 40, 40));

    private static final Font FONT_SUMMARY_LABEL = new Font(
            Font.FontFamily.HELVETICA, 10, Font.BOLD, new BaseColor(40, 40, 40));

    private static final Font FONT_SUMMARY_COUNT = new Font(
            Font.FontFamily.HELVETICA, 18, Font.BOLD, new BaseColor(40, 40, 40));

    private static final Font FONT_PASS_STATUS = new Font(
            Font.FontFamily.HELVETICA, 9, Font.BOLD, COLOR_PASS_TEXT);

    private static final Font FONT_FAIL_STATUS = new Font(
            Font.FontFamily.HELVETICA, 9, Font.BOLD, COLOR_FAIL_TEXT);

    private static final Font FONT_SKIP_STATUS = new Font(
            Font.FontFamily.HELVETICA, 9, Font.BOLD, COLOR_SKIP_TEXT);

    private static final Font FONT_FOOTER = new Font(
            Font.FontFamily.HELVETICA, 8, Font.ITALIC, new BaseColor(120, 120, 120));

    private static final Font FONT_FAILED_LABEL = new Font(
            Font.FontFamily.HELVETICA, 9, Font.BOLD, new BaseColor(80, 80, 80));

    private static final Font FONT_FAILED_VALUE = new Font(
            Font.FontFamily.HELVETICA, 9, Font.NORMAL, new BaseColor(40, 40, 40));

    private static final Font FONT_NO_FAILURE = new Font(
            Font.FontFamily.HELVETICA, 12, Font.BOLD, COLOR_PASS_TEXT);

    // =========================================================
    //  PAGE EVENT — page numbers + footer on last page
    // =========================================================

    static class ReportPageEvent extends PdfPageEventHelper {

        private boolean addFooterOnLastPage = false;

        void triggerLastPageFooter() {
            this.addFooterOnLastPage = true;
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {

            PdfContentByte cb = writer.getDirectContent();
            Rectangle pageSize = document.getPageSize();

            // ---- page number ----
            String pageText = "Page " + writer.getPageNumber();

            ColumnText.showTextAligned(
                    cb,
                    Element.ALIGN_CENTER,
                    new Phrase(pageText, new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL,
                            new BaseColor(120, 120, 120))),
                    (pageSize.getLeft() + pageSize.getRight()) / 2,
                    pageSize.getBottom() + 15,
                    0
            );

            // ---- report footer on last page ----
            if (addFooterOnLastPage) {

                float centerX = (pageSize.getLeft() + pageSize.getRight()) / 2;
                float startY  = pageSize.getBottom() + 55;

                cb.setColorStroke(new BaseColor(180, 180, 180));
                cb.moveTo(pageSize.getLeft() + 60, startY + 28);
                cb.lineTo(pageSize.getRight() - 60, startY + 28);
                cb.stroke();

                ColumnText.showTextAligned(
                        cb, Element.ALIGN_CENTER,
                        new Phrase("Generated by Selenium Automation Framework",
                                new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD,
                                        new BaseColor(80, 80, 80))),
                        centerX, startY + 17, 0);

                ColumnText.showTextAligned(
                        cb, Element.ALIGN_CENTER,
                        new Phrase("Java  \u2022  Selenium  \u2022  TestNG  \u2022  iText",
                                new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL,
                                        new BaseColor(100, 100, 100))),
                        centerX, startY + 6, 0);

                ColumnText.showTextAligned(
                        cb, Element.ALIGN_CENTER,
                        new Phrase("Microscan Customer Portal Automation",
                                new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC,
                                        new BaseColor(120, 120, 120))),
                        centerX, startY - 5, 0);

                cb.moveTo(pageSize.getLeft() + 60, startY - 14);
                cb.lineTo(pageSize.getRight() - 60, startY - 14);
                cb.stroke();
            }
        }
    }

    // =========================================================
    //  PUBLIC ENTRY POINT
    // =========================================================

    public static void generate() {

        try {

            String timeStamp =
                    new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

            String reportPath =
                    System.getProperty("user.dir")
                            + "/test-output/AutomationReport_"
                            + timeStamp + ".pdf";

            File folder = new File(System.getProperty("user.dir") + "/test-output");

            if (!folder.exists()) {
                folder.mkdirs();
            }

            Document document = new Document(PageSize.A4.rotate(),
                    36, 36, 60, 60);   // left, right, top, bottom margins

            PdfWriter writer = PdfWriter.getInstance(
                    document, new FileOutputStream(reportPath));

            ReportPageEvent pageEvent = new ReportPageEvent();
            writer.setPageEvent(pageEvent);

            document.open();

            addTitle(document);
            addProjectInformation(document);
            addSummary(document);
//            addTable(document);
            document.newPage();

            addPositiveTable(document);

            addNegativeTable(document);


            // signal page event before closing so footer appears on last page
            pageEvent.triggerLastPageFooter();

            addFailedTests(document);

            document.close();

            System.out.println("PDF Report Generated : " + reportPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================================================
    //  SECTION 1 — TITLE
    // =========================================================

    private static void addTitle(Document document) throws Exception {

        // Main title
        Paragraph title = new Paragraph("Microscan Customer Portal Automation", FONT_TITLE);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingBefore(10);
        document.add(title);

        // Subtitle
        Paragraph subtitle = new Paragraph("Automation Execution Report", FONT_SUBTITLE);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingBefore(4);
        subtitle.setSpacingAfter(14);
        document.add(subtitle);

        // Horizontal separator line drawn via a 1-row, 1-col table with colored background
        PdfPTable line = new PdfPTable(1);
        line.setWidthPercentage(100);
        PdfPCell lineCell = new PdfPCell(new Phrase(" "));
        lineCell.setFixedHeight(3f);
        lineCell.setBackgroundColor(COLOR_SEPARATOR);
        lineCell.setBorder(Rectangle.NO_BORDER);
        line.addCell(lineCell);
        document.add(line);

        document.add(spacer(10));
    }

    // =========================================================
    //  SECTION 2 — PROJECT INFORMATION
    // =========================================================

    private static void addProjectInformation(Document document) throws Exception {

        document.add(sectionHeading("Project Information"));

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(65);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setWidths(new float[]{3f, 5f});

        String executionDate =
                new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        String generatedTime =
                new SimpleDateFormat("dd MMM yyyy, hh:mm:ss a").format(new Date());

        addInfoRow(table, "Project Name",     "Customer Portal Automation", true);
        addInfoRow(table, "Tester",           "Jayasri",                    false);
        addInfoRow(table, "Browser",          "Chrome",                     true);
        addInfoRow(table, "Environment",      "QA",                         false);
        addInfoRow(table, "Execution Date",   executionDate,                true);
        addInfoRow(table, "Report Generated", generatedTime,                false);

        document.add(table);
        document.add(spacer(14));
    }

    private static void addInfoRow(PdfPTable table,
                                   String label,
                                   String value,
                                   boolean shaded) {

        PdfPCell labelCell = new PdfPCell(new Phrase(label, FONT_INFO_LABEL));
        labelCell.setBackgroundColor(shaded ? COLOR_INFO_LABEL_BG : BaseColor.WHITE);
        labelCell.setPadding(7);
        labelCell.setBorderColor(new BaseColor(210, 210, 210));
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, FONT_INFO_VALUE));
        valueCell.setBackgroundColor(shaded ? COLOR_INFO_LABEL_BG : BaseColor.WHITE);
        valueCell.setPadding(7);
        valueCell.setBorderColor(new BaseColor(210, 210, 210));
        table.addCell(valueCell);
    }

    // =========================================================
    //  SECTION 3 — EXECUTION SUMMARY
    // =========================================================

    private static void addSummary(Document document) throws Exception {

        document.add(sectionHeading("Execution Summary"));

        List<TestResultData> results = TestResultManager.getResults();

        int total = results.size();
        int pass  = 0;
        int fail  = 0;
        int skip  = 0;

        for (TestResultData r : results) {
            switch (r.getStatus()) {
                case "PASS":    pass++; break;
                case "FAIL":    fail++; break;
                default:        skip++; break;
            }
        }

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(80);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setWidths(new float[]{1f, 1f, 1f, 1f});

        addSummaryCell(table, "Total Tests", String.valueOf(total),
                COLOR_SUMMARY_TOTAL_BG, new BaseColor(26, 54, 93));

        addSummaryCell(table, "Passed", String.valueOf(pass),
                COLOR_PASS_BG, COLOR_PASS_TEXT);

        addSummaryCell(table, "Failed", String.valueOf(fail),
                COLOR_FAIL_BG, COLOR_FAIL_TEXT);

        addSummaryCell(table, "Skipped", String.valueOf(skip),
                COLOR_SKIP_BG, COLOR_SKIP_TEXT);

        document.add(table);
        document.add(spacer(14));
    }

    private static void addSummaryCell(PdfPTable table,
                                       String label,
                                       String count,
                                       BaseColor bgColor,
                                       BaseColor textColor) {

        Font countFont = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD, textColor);
        Font labelFont = new Font(Font.FontFamily.HELVETICA, 9,  Font.BOLD, textColor);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(bgColor);
        cell.setPadding(12);
        cell.setBorderColor(new BaseColor(200, 200, 200));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        Paragraph content = new Paragraph();
        content.setAlignment(Element.ALIGN_CENTER);
        content.add(new Chunk(count + "\n", countFont));
        content.add(new Chunk(label, labelFont));

        cell.addElement(content);
        table.addCell(cell);
    }

    // =========================================================
    //  SECTION 4 — TEST EXECUTION DETAILS TABLE
    // =========================================================

    private static void addPositiveTable(Document document) throws Exception {

        document.add(sectionHeading("Positive Test Cases"));

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{
                1.6f,   // TC ID
                2.2f,   // Module
                3.0f,   // Test Method
                3.5f,   // Test Data
                3.0f,   // Expected Result
                3.0f,   // Actual Result
                1.8f,   // Status
                1.8f    // Duration
        });

        // --- Header row ---
        addTableHeader(table, "TC ID");
        addTableHeader(table, "Module");
        addTableHeader(table, "Scenario");
        addTableHeader(table, "Test Data");
        addTableHeader(table, "Expected Result");
        addTableHeader(table, "Actual Result");
        addTableHeader(table, "Status");
        addTableHeader(table, "Duration");

        // --- Data rows ---
        List<TestResultData> results = TestResultManager.getResults();
        int rowIndex = 0;

        for (TestResultData result : results) {

            if (!"Positive".equalsIgnoreCase(result.getTestType())) {
                continue;
            }

            boolean isOdd       = (rowIndex % 2 == 0);
            BaseColor rowBg     = isOdd ? COLOR_ROW_ODD : COLOR_ROW_EVEN;

            addBodyCell(table, result.getTcId(),          rowBg, Element.ALIGN_LEFT);
            addBodyCell(table, result.getModule(),        rowBg, Element.ALIGN_LEFT);
//            addBodyCell(table, result.getTestCase(),      rowBg, Element.ALIGN_LEFT);
            addBodyCell(table, result.getScenario(), rowBg, Element.ALIGN_LEFT);
            String testData = result.getTestData();

            if (testData == null || testData.trim().equals("[]") || testData.trim().isEmpty()) {
                testData = "N/A";
            } else {

//                testData = testData.replaceAll(",\\s*[^,\\]]+", ", ********");

                if (testData.length() > 80) {
                    testData = testData.substring(0, 80) + "...";
                }
            }

            addBodyCell(table, testData, rowBg, Element.ALIGN_LEFT);
//            addBodyCell(table, result.getTestData(),      rowBg, Element.ALIGN_LEFT);
            addBodyCell(table, result.getExpectedResult(), rowBg, Element.ALIGN_LEFT);
            String actualResult = result.getActualResult();

            if ("Test Executed Successfully".equals(actualResult)) {
                actualResult = "Expected behaviour verified.";
            }

            addBodyCell(table, actualResult, rowBg, Element.ALIGN_LEFT);
//            addBodyCell(table, result.getActualResult(),  rowBg, Element.ALIGN_LEFT);
            addStatusCell(table, result.getStatus());
            addBodyCell(table, result.getDuration(),      rowBg, Element.ALIGN_CENTER);

            rowIndex++;
        }

        document.add(table);
        document.add(spacer(14));
    }
    private static void addNegativeTable(Document document) throws Exception {

        document.add(sectionHeading("Negative Test Cases"));

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{
                1.6f,   // TC ID
                2.2f,   // Module
                3.0f,   // Test Method
                3.5f,   // Test Data
                3.0f,   // Expected Result
                3.0f,   // Actual Result
                1.8f,   // Status
                1.8f    // Duration
        });

        // --- Header row ---
        addTableHeader(table, "TC ID");
        addTableHeader(table, "Module");
        addTableHeader(table, "Scenario");
        addTableHeader(table, "Test Data");
        addTableHeader(table, "Expected Result");
        addTableHeader(table, "Actual Result");
        addTableHeader(table, "Status");
        addTableHeader(table, "Duration");

        // --- Data rows ---
        List<TestResultData> results = TestResultManager.getResults();
        int rowIndex = 0;

        for (TestResultData result : results) {

            if (!"Negative".equalsIgnoreCase(result.getTestType())) {
                continue;
            }

            boolean isOdd       = (rowIndex % 2 == 0);
            BaseColor rowBg     = isOdd ? COLOR_ROW_ODD : COLOR_ROW_EVEN;

            addBodyCell(table, result.getTcId(),          rowBg, Element.ALIGN_LEFT);
            addBodyCell(table, result.getModule(),        rowBg, Element.ALIGN_LEFT);
//            addBodyCell(table, result.getTestCase(),      rowBg, Element.ALIGN_LEFT);
            addBodyCell(table, result.getScenario(), rowBg, Element.ALIGN_LEFT);
            String testData = result.getTestData();

            if (testData != null) {
//                testData = testData.replaceAll(",\\s*[^,\\]]+", ", ********");

                if (testData.length() > 80) {
                    testData = testData.substring(0, 80) + "...";
                }
            }

            addBodyCell(table, testData, rowBg, Element.ALIGN_LEFT);
//            addBodyCell(table, result.getTestData(),      rowBg, Element.ALIGN_LEFT);
            addBodyCell(table, result.getExpectedResult(), rowBg, Element.ALIGN_LEFT);
            String actualResult = result.getActualResult();

            if ("Test Executed Successfully".equals(actualResult)) {
                actualResult = "Expected behaviour verified.";
            }

            addBodyCell(table, actualResult, rowBg, Element.ALIGN_LEFT);
//            addBodyCell(table, result.getActualResult(),  rowBg, Element.ALIGN_LEFT);
            addStatusCell(table, result.getStatus());
            addBodyCell(table, result.getDuration(),      rowBg, Element.ALIGN_CENTER);

            rowIndex++;
        }

        document.add(table);
        document.add(spacer(14));
    }

    private static void addTableHeader(PdfPTable table, String text) {

        PdfPCell cell = new PdfPCell(new Phrase(text, FONT_TABLE_HEADER));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(COLOR_HEADER_BG);
        cell.setPadding(8);
        cell.setBorderColor(new BaseColor(255, 255, 255));
        cell.setBorderWidth(0.5f);
        table.addCell(cell);
    }

    private static void addBodyCell(PdfPTable table,
                                    String text,
                                    BaseColor bgColor,
                                    int alignment) {

        PdfPCell cell = new PdfPCell(
                new Phrase(text != null ? text : "", FONT_TABLE_BODY));
        cell.setBackgroundColor(bgColor);
        cell.setPaddingLeft(6);
        cell.setPaddingRight(6);
        cell.setPaddingTop(5);
        cell.setPaddingBottom(5);
        cell.setHorizontalAlignment(alignment);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(new BaseColor(210, 210, 210));
        cell.setBorderWidth(0.5f);
        cell.setMinimumHeight(50f);
        table.addCell(cell);
    }

    private static void addStatusCell(PdfPTable table, String status) {

        BaseColor bg;
        Font     font;
        String   label;

        if ("PASS".equalsIgnoreCase(status)) {
            bg    = COLOR_PASS_BG;
            font  = FONT_PASS_STATUS;
            label = "PASS";
        } else if ("FAIL".equalsIgnoreCase(status)) {
            bg    = COLOR_FAIL_BG;
            font  = FONT_FAIL_STATUS;
            label = "FAIL";
        } else {
            bg    = COLOR_SKIP_BG;
            font  = FONT_SKIP_STATUS;
            label = "SKIP";
        }

        PdfPCell cell = new PdfPCell(new Phrase(label, font));
        cell.setBackgroundColor(bg);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        cell.setBorderColor(new BaseColor(210, 210, 210));
        cell.setBorderWidth(0.5f);
        table.addCell(cell);
    }

    // =========================================================
    //  SECTION 5 — FAILED TEST DETAILS
    // =========================================================

    private static void addFailedTests(Document document) throws Exception {

        List<TestResultData> results = TestResultManager.getResults();

        document.add(sectionHeading("Failed Test Details"));

        boolean hasFailure = false;

        for (TestResultData result : results) {

            if (!"FAIL".equalsIgnoreCase(result.getStatus())) {
                continue;
            }

            hasFailure = true;

            // Bordered section table (1 column)
            PdfPTable section = new PdfPTable(1);
            section.setWidthPercentage(100);
            section.setSpacingBefore(8);
            section.setSpacingAfter(8);

            // Section header bar (TC ID + Module)
            String headerText = result.getTcId() + "   |   " + result.getModule();
            PdfPCell headerCell = new PdfPCell(
                    new Phrase(headerText,
                            new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE)));
            headerCell.setBackgroundColor(COLOR_HEADER_BG);
            headerCell.setPadding(8);
            headerCell.setBorder(Rectangle.BOX);
            headerCell.setBorderColor(COLOR_SECTION_BORDER);
            section.addCell(headerCell);

            // Detail rows inside the bordered section
            PdfPTable details = new PdfPTable(2);
            details.setWidthPercentage(100);
            details.setWidths(new float[]{2.5f, 7.5f});

            addDetailRow(details, "Test Method",    result.getTestCase(),     true);
//            addDetailRow(details, "Failure Reason", result.getActualResult(), false);

            String reason = result.getActualResult();

            if (reason != null && reason.contains("\n")) {
                reason = reason.substring(0, reason.indexOf("\n"));
            }

            addDetailRow(details, "Failure Reason", reason, false);

            PdfPCell detailWrapper = new PdfPCell();
            detailWrapper.addElement(details);
            detailWrapper.setPadding(0);
            detailWrapper.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
            detailWrapper.setBorderColor(COLOR_SECTION_BORDER);
            detailWrapper.setBorderWidth(1f);
            section.addCell(detailWrapper);

            document.add(section);

            // Screenshot (if available) — outside section box, indented
            if (result.getScreenshotPath() != null &&
                    !result.getScreenshotPath().isEmpty()) {

                try {
                    Image image = Image.getInstance(result.getScreenshotPath());
                    image.scaleToFit(480, 260);
                    image.setAlignment(Image.ALIGN_CENTER);
                    image.setSpacingBefore(4);
                    image.setSpacingAfter(4);
                    document.add(image);
                } catch (Exception e) {
                    Paragraph noShot = new Paragraph(
                            "  Screenshot not available.", FONT_FOOTER);
                    noShot.setSpacingBefore(2);
                    document.add(noShot);
                }
            }
        }

        if (!hasFailure) {
            Paragraph noFail = new Paragraph(
                    "\u2714  No Failed Test Cases — All tests passed successfully.",
                    FONT_NO_FAILURE);
            noFail.setSpacingBefore(6);
            document.add(noFail);
        }
    }

    private static void addDetailRow(PdfPTable table,
                                     String label,
                                     String value,
                                     boolean shaded) {

        PdfPCell lCell = new PdfPCell(new Phrase(label, FONT_FAILED_LABEL));
        lCell.setBackgroundColor(shaded ? COLOR_SECTION_BG : BaseColor.WHITE);
        lCell.setPadding(7);
        lCell.setBorderColor(new BaseColor(210, 210, 210));
        table.addCell(lCell);

        PdfPCell vCell = new PdfPCell(
                new Phrase(value != null ? value : "", FONT_FAILED_VALUE));
        vCell.setBackgroundColor(shaded ? COLOR_SECTION_BG : BaseColor.WHITE);
        vCell.setPadding(7);
        vCell.setBorderColor(new BaseColor(210, 210, 210));
        table.addCell(vCell);
    }

    // =========================================================
    //  SHARED HELPERS
    // =========================================================

    /**
     * Reusable section heading with left blue accent bar.
     */
    private static Paragraph sectionHeading(String text) {

        Paragraph heading = new Paragraph(text, FONT_SECTION_HEADING);
        heading.setSpacingBefore(12);
        heading.setSpacingAfter(6);
        return heading;
    }

    /**
     * Invisible spacer paragraph of given height in points.
     */
    private static Paragraph spacer(float height) {

        Paragraph p = new Paragraph(" ");
        p.setLeading(height);
        return p;
    }

    // =========================================================
    //  LEGACY HEADER HELPER  (kept for backward compatibility)
    // =========================================================

    private static void addHeader(PdfPTable table, String text) {
        addTableHeader(table, text);
    }
}