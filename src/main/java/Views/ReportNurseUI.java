package Views;

import Controllers.ServiceController;
import Models.Patient;
import Models.Service;
import org.apache.poi.xwpf.usermodel.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportNurseUI {
    private JPanel BackPanel;
    private JPanel FinancePanel;
    private JPanel MedicalPanel;
    private JTable reportList;
    private JScrollPane reportpane;
    private JTextField searchTXT;
    private JPanel reportForm;
    private JPanel resultPanel;
    private JButton generateReportButton;
    private JPanel result;
    private JButton btnsearch;
    private JPanel SearchResultPane;
    private JLabel lblPatientName;
    private JLabel lblID;
    private JLabel lblPhone;
    private JLabel lblmessage;
    Patient results;
    Patient patient;
    List<Service> records;
    ArrayList<Service> notes;

    ServiceController serviceController;

    public JPanel getBackPanel() {
        return BackPanel;
    }

    ReportNurseUI(){
        SearchResultPane.setVisible(false);
        serviceController = new ServiceController();
        ImageIcon search = new ImageIcon("src/main/java/Assets/search_24dp_000000_FILL0_wght400_GRAD0_opsz24 (1).png");
        btnsearch.setIcon(search);
        btnsearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String value = searchTXT.getText();
                if(value.isEmpty()){
                    lblmessage.setText("Enter Filed");
                    return;
                }
                results = serviceController.service.searchPatient(value);
                if(results != null){
                    loadResultPanel(results);
                    loadData();
                }else {
                    loadResultPanel(results);
                    lblmessage.setText("Could Not Found");
                }
            }
        });
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
                generateReport(patient,records,notes);
            }
        });
    }

    private void loadResultPanel(Patient patient){
        if(patient != null){
            SearchResultPane.setVisible(true);
            lblmessage.setText("Result Found");
            lblID.setText("PatientID: "+String.valueOf(patient.getPatientID()));
            lblPatientName.setText(patient.getName());
            lblPhone.setText("Phone: "+patient.getPhoneNumber());
        }else {
            SearchResultPane.setVisible(false);
        }
    }
    private void loadData(){
        patient = serviceController.service.getPatientDetails(results.getPatientID());
        records = serviceController.service.getService(results.getPatientID());
        notes = serviceController.service.getNotes(results.getPatientID());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        reportForm = new CustomComponents.RoundedPanel(20);
        resultPanel = new CustomComponents.RoundedPanel(20);
        SearchResultPane= new CustomComponents.RoundedPanel(20);
    }

    public void generateReport(Patient patient, List<Service> records, List<Service> notesList) {
        try (FileInputStream fis = new FileInputStream("src/main/java/Assets/Report_template.docx");
             XWPFDocument document = new XWPFDocument(fis)) {

            // 1️⃣ Concatenate notes
            StringBuilder allNotes = new StringBuilder();
            if (notesList != null && !notesList.isEmpty()) {
                for (Service s : notesList) {
                    allNotes.append("[")
                            .append(s.getCheckedTime()).append("] ")
                            .append(s.getSpecialNotes()).append("\n");
                }
            }

            Map<String, String> replacements = new HashMap<>();
            replacements.put("{{PatientID}}", String.valueOf(patient.getPatientID()));
            replacements.put("{{Name}}", safe(patient.getName()));
            replacements.put("{{Age}}", String.valueOf(patient.getAge()));
            replacements.put("{{PhoneNumber}}", safe(patient.getPhoneNumber()));
            replacements.put("{{Gender}}", safe(patient.getGender()));
            replacements.put("{{RegDate}}", safe(patient.getRegDate()));
            replacements.put("{{Illness}}", safe(patient.getIllness()));
            replacements.put("{{BloodType}}", safe(patient.getBloodType()));
            replacements.put("{{Allergies}}", safe(patient.getAllergies()));
            replacements.put("{{PatientNote}}", safe(allNotes.toString()));


            // 3️⃣ Replace in paragraphs safely
            for (XWPFParagraph p : new ArrayList<>(document.getParagraphs())) {
                String text = p.getText();
                if (text != null) {
                    String replaced = text;
                    for (Map.Entry<String, String> entry : replacements.entrySet()) {
                        replaced = replaced.replace(entry.getKey(), entry.getValue());
                    }
                    if (!replaced.equals(text)) {
                        // Clear old runs
                        for (int i = p.getRuns().size() - 1; i >= 0; i--) {
                            p.removeRun(i);
                        }
                        p.createRun().setText(replaced, 0);
                    }
                }
            }

            // 4️⃣ Replace in tables safely
            for (XWPFTable table : new ArrayList<>(document.getTables())) {
                for (XWPFTableRow row : new ArrayList<>(table.getRows())) {
                    for (XWPFTableCell cell : new ArrayList<>(row.getTableCells())) {
                        String cellText = cell.getText();
                        if (cellText != null) {
                            String replaced = cellText;
                            for (Map.Entry<String, String> entry : replacements.entrySet()) {
                                replaced = replaced.replace(entry.getKey(), entry.getValue());
                            }
                            if (!replaced.equals(cellText)) {
                                cell.removeParagraph(0);
                                XWPFParagraph para = cell.addParagraph();
                                para.createRun().setText(replaced);
                            }
                        }
                    }
                }
            }

            // 5️⃣ Service table expansion
            for (XWPFTable table : new ArrayList<>(document.getTables())) {
                for (XWPFTableRow row : new ArrayList<>(table.getRows())) {
                    for (XWPFTableCell cell : new ArrayList<>(row.getTableCells())) {
                        if (cell.getText().contains("{{service_row}}")) {
                            XWPFTableRow templateRow = row;

                            // Clean template row
                            for (XWPFTableCell c : new ArrayList<>(templateRow.getTableCells())) {
                                for (XWPFParagraph p : new ArrayList<>(c.getParagraphs())) {
                                    for (XWPFRun r : new ArrayList<>(p.getRuns())) {
                                        r.setText("", 0);
                                    }
                                }
                            }

                            // Add rows
                            for (Service record : records) {
                                XWPFTableRow newRow = table.createRow();
                                newRow.getCell(0).setText(String.valueOf(record.getServiceID()));
                                newRow.getCell(1).setText(record.getServiceName());
                                newRow.getCell(2).setText(record.getDoctor());
                                newRow.getCell(3).setText(record.getCheckedTime());
                            }

                            // Remove template row
                            table.removeRow(table.getRows().indexOf(templateRow));
                            break;
                        }
                    }
                }
            }

            // 6️⃣ Save report
            File reportDir = new File("src/main/java/Assets/PatientReports");
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            File reportFile = new File(reportDir, "PatientReport_" + patient.getPatientID() + ".docx");
            try (FileOutputStream fos = new FileOutputStream(reportFile)) {
                document.write(fos);
            }

            System.out.println("Report generated successfully: " + reportFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String safe(String value) {
        return value == null ? "" : value;
    }


}
