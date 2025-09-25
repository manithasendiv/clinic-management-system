package Views;

import Controllers.ServiceController;
import Models.PatientReport;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePatientUI {
    private JTextField txtGender;
    private JTextField txtphone;
    private JTextField txtIllness;
    private JTextField txtallergies;
    private JComboBox comboBoxblood;
    private JButton saveButton;

    ServiceController serviceController;

    public JPanel getContentPane() {
        return ContentPane;
    }

    private JPanel ContentPane;
    private JButton resetButton;
    private JLabel txtAllergy;

    public UpdatePatientUI(PatientReport patientReport, ServiceController s){
        //setting object
        this.serviceController = s;
        // Initialize blood types once
        if (comboBoxblood.getItemCount() == 0) {
            String[] bloodTypes = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
            for (String bt : bloodTypes) {
                comboBoxblood.addItem(bt);
            }
        }

        setPanel(patientReport);

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtGender.setText("");
                txtallergies.setText("");
                txtphone.setText("");
                txtIllness.setText("");
                comboBoxblood.setSelectedIndex(0); // reset to first blood type
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(patientReport != null){
                    PatientReport updatedPatientReport = new PatientReport(
                            patientReport.getGender(),
                            patientReport.getAllergies(),
                            patientReport.getPhoneNumber(),
                            patientReport.getIllness(),
                            (String) comboBoxblood.getSelectedItem(),
                            patientReport.getPatientID()
                    );

                    boolean results = serviceController.service.updatePatientDetails(updatedPatientReport);

                    if (results) {
                        JOptionPane.showMessageDialog(ContentPane, "Patient details updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(ContentPane, "Failed to update patient details.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    // Insert new patient
                    String gender = txtGender.getText();
                    String allergy = txtallergies.getText();
                    String phone = txtphone.getText();
                    String illness = txtIllness.getText();
                    String blood = (String) comboBoxblood.getSelectedItem();

                    PatientReport newPatientReport = new PatientReport(gender, allergy, phone, illness, blood, patientReport.getPatientID());

                    boolean results = serviceController.service.addPatientDetails(newPatientReport);

                    if (results) {
                        JOptionPane.showMessageDialog(ContentPane, "Patient details inserted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(ContentPane, "Failed to insert patient details.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
    private void setPanel(PatientReport patientReport) {
        if (patientReport != null) {
            // Fill fields
            txtGender.setText(patientReport.getGender());
            txtallergies.setText(patientReport.getAllergies());
            txtphone.setText(patientReport.getPhoneNumber());
            txtIllness.setText(patientReport.getIllness());
            comboBoxblood.setSelectedItem(patientReport.getBloodType());
        }
        else {
            return;
        }
    }


}
