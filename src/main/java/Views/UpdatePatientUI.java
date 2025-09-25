package Views;

import Controllers.ServiceController;
import Models.Patient;

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

    public UpdatePatientUI(Patient patient, ServiceController s){
        //setting object
        this.serviceController = s;
        // Initialize blood types once
        if (comboBoxblood.getItemCount() == 0) {
            String[] bloodTypes = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
            for (String bt : bloodTypes) {
                comboBoxblood.addItem(bt);
            }
        }

        setPanel(patient);

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
                if(patient != null){
                    Patient updatedPatient = new Patient(
                            patient.getGender(),
                            patient.getAllergies(),
                            patient.getPhoneNumber(),
                            patient.getIllness(),
                            (String) comboBoxblood.getSelectedItem(),
                            patient.getPatientID()
                    );

                    boolean results = serviceController.service.updatePatientDetails(updatedPatient);

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

                    Patient newPatient = new Patient(gender, allergy, phone, illness, blood,patient.getPatientID());

                    boolean results = serviceController.service.addPatientDetails(newPatient);

                    if (results) {
                        JOptionPane.showMessageDialog(ContentPane, "Patient details inserted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(ContentPane, "Failed to insert patient details.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
    private void setPanel(Patient patient) {
        if (patient != null) {
            // Fill fields
            txtGender.setText(patient.getGender());
            txtallergies.setText(patient.getAllergies());
            txtphone.setText(patient.getPhoneNumber());
            txtIllness.setText(patient.getIllness());
            comboBoxblood.setSelectedItem(patient.getBloodType());
        }
        else {
            return;
        }
    }


}
