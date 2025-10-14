package Views;

import Controllers.StyledBarChart;
import ServiceLayer.ReportService;
import Controllers.PDFReportGenerator;
import Controllers.StyledPieChart;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class ReportSelectionGUI {

    private JFrame frame;
    private JCheckBox monthlyCheck, genderCheck, ageCheck, doctorCheck;
    private JButton previewButton, downloadButton, refreshButton;
    private JTextField pathField;
    private JComboBox<String> monthSelector;
    private ReportService service;

    public ReportSelectionGUI() {
        try { UIManager.setLookAndFeel(new FlatLightLaf()); } catch(Exception ignored){}

        service = new ReportService();
        frame = new JFrame("Patient Reports Dashboard");
        frame.setSize(750,500);
        frame.setLayout(new BorderLayout(10,10));

        // --- Top Panel: Checkboxes + Month Selector ---
        JPanel topPanel = new JPanel(new GridLayout(3,2,10,10));
        monthlyCheck = new JCheckBox("Monthly Patient Count", true);
        genderCheck = new JCheckBox("Gender Distribution", true);
        ageCheck = new JCheckBox("Age Distribution", true);
        doctorCheck = new JCheckBox("Patients per Doctor", true);

        monthlyCheck.setToolTipText("Include monthly patient count chart");
        genderCheck.setToolTipText("Include gender distribution chart");
        ageCheck.setToolTipText("Include age distribution chart");
        doctorCheck.setToolTipText("Include patients per doctor chart");

        topPanel.add(monthlyCheck);
//        topPanel.add(genderCheck);
        topPanel.add(ageCheck);
        topPanel.add(doctorCheck);
        topPanel.add(new JLabel(""));

        // Month Selector
        String[] months = {"All","January","February","March","April","May","June","July","August",
                "September","October","November","December"};
        monthSelector = new JComboBox<>(months);
        monthSelector.setSelectedIndex(0); // default: All
        topPanel.add(new JLabel("Select Month:"));
        topPanel.add(monthSelector);

        // --- Middle Panel: File path selection ---
        JPanel middlePanel = new JPanel(new BorderLayout(5,5));
        pathField = new JTextField(System.getProperty("user.home") + "/PatientReport.pdf");
        JButton browseBtn = new JButton("Browse");
        middlePanel.add(new JLabel("Save PDF to:"), BorderLayout.WEST);
        middlePanel.add(pathField, BorderLayout.CENTER);
        middlePanel.add(browseBtn, BorderLayout.EAST);

        browseBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select location to save PDF");
            fileChooser.setSelectedFile(new File(pathField.getText()));
            int result = fileChooser.showSaveDialog(frame);
            if(result == JFileChooser.APPROVE_OPTION){
                pathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });

        // --- Bottom Panel: Buttons ---
        JPanel bottomPanel = new JPanel(new FlowLayout());
        previewButton = new JButton("Preview Selected Charts");
        refreshButton = new JButton("Refresh Monthly Charts");
        downloadButton = new JButton("Download PDF");

        bottomPanel.add(previewButton);
        bottomPanel.add(refreshButton);
        bottomPanel.add(downloadButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // --- Action Listeners ---
        previewButton.addActionListener(e -> previewCharts());
        downloadButton.addActionListener(e -> downloadPDF());
        refreshButton.addActionListener(e -> refreshCharts());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Preview selected charts in a window
    private void previewCharts() {
        JFrame chartFrame = new JFrame("Preview Charts");
        chartFrame.setLayout(new GridLayout(2,2,10,10));

        String selectedMonth = (String) monthSelector.getSelectedItem();

        if(monthlyCheck.isSelected())
            chartFrame.add(StyledBarChart.createStyledBarChart(
                    "Monthly Patient Count","Month","Patients", service.getMonthlyPatientCountByDoctor(selectedMonth)));

        if(genderCheck.isSelected())
            chartFrame.add(StyledPieChart.createStyledPieChart(
                    "Gender Distribution", service.getGenderDistribution(selectedMonth)));

        if(ageCheck.isSelected())
            chartFrame.add(StyledBarChart.createStyledBarChart(
                    "Age Distribution","Age Range","Patients", service.getAgeDistribution(selectedMonth)));

        if(doctorCheck.isSelected())
            chartFrame.add(StyledPieChart.createStyledPieChart(
                    "Patients per Doctor", service.getPatientsPerDoctorByMonth(selectedMonth)));

        chartFrame.setSize(1000,600);
        chartFrame.setLocationRelativeTo(frame);
        chartFrame.setVisible(true);
    }

    // Download PDF with selected charts
    private void downloadPDF() {
        String path = pathField.getText();
        String logoPath = "D:\\Year 2\\Sem2\\new\\ppa\\clinic-management-system\\src\\main\\java\\Assets\\logo.png";
        String selectedMonth = (String) monthSelector.getSelectedItem();

        HashMap<String,Object> stats = service.getBasicStats(selectedMonth);
        HashMap<String,Integer> monthly = monthlyCheck.isSelected() ? service.getMonthlyPatientCountByDoctor(selectedMonth) : null;
        HashMap<String,Integer> gender = genderCheck.isSelected() ? service.getGenderDistribution(selectedMonth) : null;
        HashMap<String,Integer> age = ageCheck.isSelected() ? service.getAgeDistribution(selectedMonth) : null;
        HashMap<String,Integer> doctor = doctorCheck.isSelected() ? service.getPatientsPerDoctorByMonth(selectedMonth) : null;

        // Add invoice number & created date
        String invoiceNumber = "INV-" + System.currentTimeMillis();
        String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        PDFReportGenerator.generatePDF(path, stats, monthly, gender, age, doctor, logoPath, invoiceNumber, createdDate);
        JOptionPane.showMessageDialog(frame,"PDF saved to: "+path);
    }


    // Refresh monthly charts dynamically
    private void refreshCharts() {
        String selectedMonth = (String) monthSelector.getSelectedItem();

        // Monthly Patient Count
        HashMap<String,Integer> monthlyData = service.getMonthlyPatientCountByDoctor(selectedMonth);
        JFrame monthlyFrame = new JFrame("Monthly Patient Count");
        monthlyFrame.setSize(800,600);
        monthlyFrame.add(StyledBarChart.createStyledBarChart("Monthly Patient Count","Month","Patients", monthlyData));
        monthlyFrame.setLocationRelativeTo(frame);
        monthlyFrame.setVisible(true);

        // Patients per Doctor
        HashMap<String,Integer> doctorData = service.getPatientsPerDoctorByMonth(selectedMonth);
        JFrame doctorFrame = new JFrame("Patients per Doctor");
        doctorFrame.setSize(800,600);
        doctorFrame.add(StyledPieChart.createStyledPieChart("Patients per Doctor", doctorData));
        doctorFrame.setLocationRelativeTo(frame);
        doctorFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ReportSelectionGUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}

