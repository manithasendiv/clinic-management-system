package Views;

import Controllers.ServiceController;
import Models.Patient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

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
                String gender = txtGender.getText().trim();
                String allergy = txtallergies.getText().trim();
                String phone = txtphone.getText().trim();
                String illness = txtIllness.getText().trim();
                String blood = (String) comboBoxblood.getSelectedItem();
                boolean setDec = serviceController.service.checkDescription(patient.getPatientID());
                // Basic validation
                if (gender.isEmpty() || allergy.isEmpty() || phone.isEmpty() || illness.isEmpty() || blood == null || blood.isEmpty()) {
                    JOptionPane.showMessageDialog(ContentPane, "Please fill in all fields before saving.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Create patient object
                Patient patientObj = new Patient(gender, allergy, phone, illness, blood, patient.getPatientID());

                boolean results;

                if (setDec) {
                    // UPDATE
                    results = serviceController.service.updatePatientDetails(patientObj);

                    if (results) {
                        JOptionPane.showMessageDialog(ContentPane, "Patient details updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(ContentPane, "Failed to update patient details.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    //  INSERT
                    results = serviceController.service.addPatientDetails(patientObj);

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
