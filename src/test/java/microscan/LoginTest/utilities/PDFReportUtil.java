package utilities;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class PDFReportUtil {

    public static void createReport(
            int total,
            int passed,
            int failed,
            int skipped) {

        try {

            Document document = new Document();

            PdfWriter.getInstance(document,
                    new FileOutputStream("test-output/AutomationReport.pdf"));

            document.open();

            Font title =
                    new Font(Font.FontFamily.HELVETICA,
                            20,
                            Font.BOLD);

            Paragraph heading =
                    new Paragraph("CUSTOMER PORTAL AUTOMATION REPORT", title);

            heading.setAlignment(Element.ALIGN_CENTER);

            document.add(heading);

            document.add(new Paragraph(" "));

            document.add(new Paragraph("Project : Customer Portal Automation"));
            document.add(new Paragraph("Browser : Chrome"));
            document.add(new Paragraph("Environment : QA"));
            document.add(new Paragraph("Tester : Jayasri"));

            document.add(new Paragraph(" "));

            document.add(new Paragraph("========== TEST SUMMARY =========="));

            document.add(new Paragraph("Total Tests : " + total));

            document.add(new Paragraph("Passed : " + passed));

            document.add(new Paragraph("Failed : " + failed));

            document.add(new Paragraph("Skipped : " + skipped));

            document.close();

            System.out.println("PDF Generated Successfully");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}