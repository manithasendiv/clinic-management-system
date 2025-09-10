package Views;

import Controllers.PatientController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePatientGUI {
    private JTextField txtName;
    private JTextField txtAge;
    private JTextField txtAddress;
    private JTextField txtContact;
    private JTextField txtEmail;
    private JButton btnUpdate;
    private JLabel lblName;
    private JLabel lblAge;
    private JLabel lblAddress;
    private JPanel BackPanel;
    private JLabel lblContact;
    private JLabel lblEmail;

    PatientController objController;

    public UpdatePatientGUI(){
        objController = new PatientController();

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
