package Views;

import Controllers.PatientController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientGUI {
    private JTextField txtName;
    private JTextField txtAge;
    private JTextField txtAddress;
    private JTextField txtContact;
    private JTextField txtEmail;
    private JButton btnAddPatient;
    private JLabel lblName;
    private JLabel lblAge;
    private JLabel lblAddress;
    private JLabel lblContact;
    private JLabel lblEmail;

    PatientController objController;

    public PatientGUI(){
        objController = new PatientController();
        int patientCount = 0;

        btnAddPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int patientID = patientCount + 1 ;
                String Name = txtName.getText();
                int Age =Integer.parseInt(txtAge.getText());;
                String Address = txtAddress.getText();
                int Contact = Integer.parseInt(txtContact.getText());
                String Email = txtEmail.getText();
            }
        });
    }
}
