package Views;

import Controllers.DoctorController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateDoctorGUI {
    private JPanel BackPanel;
    private JTextField txtDocID;
    private JTextField txtName;
    private JTextField txtContact;
    private JTextField txtEmail;
    private JTextField txtSpecialization;
    private JButton btnUpdate;

    DoctorController objController;

    public UpdateDoctorGUI(){
        objController = new DoctorController();

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String did = txtDocID.getText();
                String name = txtName.getText();
                String specialization = txtSpecialization.getText();
                String email = txtEmail.getText();
                String contact = txtContact.getText();

                if (name.isEmpty() || specialization.isEmpty() || email.isEmpty() || contact.isEmpty()) {
                    JOptionPane.showMessageDialog(BackPanel, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (!objController.updateDoctor(objController.addDoctor(did, name, specialization, email, contact))) {
                        JOptionPane.showMessageDialog(null, "Error in updating doctor");
                        return;
                    }
                    JOptionPane.showMessageDialog(null, "Doctor updated successfully!");
                }
                txtDocID.setText("");
                txtName.setText("");
                txtSpecialization.setText("");
                txtEmail.setText("");
                txtContact.setText("");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("update doctor");
        frame.setSize(300,300);
        frame.setContentPane(new UpdateDoctorGUI().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public JPanel getUpdateDoctorGUI() {
        return BackPanel;
    }
}
