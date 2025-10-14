package Controllers;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import org.jfree.chart.JFreeChart;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class PDFReportGenerator {

    public static void generatePDF(String filePath, HashMap<String,Object> stats,
                                   HashMap<String,Integer> monthly,
                                   HashMap<String,Integer> gender,
                                   HashMap<String,Integer> age,
                                   HashMap<String,Integer> doctor,
                                   String logoPath,
                                   String invoiceNumber,
                                   String createdDate) {
        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);
            document.setMargins(70, 50, 70, 50);

            float[] columnWidths = {1, 2}; // adjust widths as needed
            Table topTable = new Table(columnWidths);

// Logo cell
            Cell logoCell = new Cell();
            logoCell.setBorder(null); // no border
            if (logoPath != null && !logoPath.isEmpty()) {
                try {
                    Image logo = new Image(ImageDataFactory.create(logoPath));
                    logo.scaleToFit(100, 100);
                    logoCell.add(logo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            topTable.addCell(logoCell);

// Invoice info cell (right side)
            invoiceNumber = "INV-" + System.currentTimeMillis();
            createdDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            Cell infoCell = new Cell();
            infoCell.setBorder(null);
            infoCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
            infoCell.add(new Paragraph("Invoice Number: " + invoiceNumber).setTextAlignment(TextAlignment.RIGHT));
            infoCell.add(new Paragraph("Created Date: " + createdDate).setTextAlignment(TextAlignment.RIGHT));
            topTable.addCell(infoCell);

// Add table to document
            document.add(topTable);
            document.add(new Paragraph("\n")); // spacing after table

            // --- Add Basic Stats ---
            document.add(new Paragraph("=== Patient Basic Statistics ==="));
            document.add(new Paragraph("Total Patients: " + stats.get("totalPatients")));
            document.add(new Paragraph("Average Age: " + stats.get("averageAge")));
            document.add(new Paragraph("Top Doctor: " + stats.get("topDoctor")));
            document.add(new Paragraph("\n"));

            // --- Add Charts ---
            if(monthly != null && !monthly.isEmpty()) {
                JFreeChart monthlyChart = StyledBarChart.createBarChartForPDF(
                        "Monthly Patient Count", "Month", "Patients", monthly);
                addChartToPDF(document, monthlyChart, 500, 300);
            }

            if(gender != null && !gender.isEmpty()) {
                JFreeChart genderChart = StyledPieChart.createPieChartForPDF(
                        "Gender Distribution", gender);
                addChartToPDF(document, genderChart, 400, 300);
            }

            if(age != null && !age.isEmpty()) {
                JFreeChart ageChart = StyledBarChart.createBarChartForPDF(
                        "Age Distribution","Age Range","Patients", age);
                addChartToPDF(document, ageChart, 500, 300);
            }

            if(doctor != null && !doctor.isEmpty()) {
                JFreeChart doctorChart = StyledPieChart.createPieChartForPDF(
                        "Patients per Doctor", doctor);
                addChartToPDF(document, doctorChart, 400, 300);
            }

            // --- Add Company Details at Bottom ---
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("=== Company Details ==="));
            document.add(new Paragraph("Company Name: Your Company Name"));
            document.add(new Paragraph("Address: 123 Street, City, Country"));
            document.add(new Paragraph("Contact: +123 456 789"));
            document.add(new Paragraph("Email: info@company.com"));

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- Helper: Add JFreeChart to PDF as image ---
    private static void addChartToPDF(Document document, JFreeChart chart, int width, int height) {
        try {
            BufferedImage bufferedImage = chart.createBufferedImage(width, height);
            Image pdfImage = new Image(ImageDataFactory.create(bufferedImage, null));
            pdfImage.setHorizontalAlignment(HorizontalAlignment.CENTER);
            document.add(pdfImage);
            document.add(new Paragraph("\n"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
