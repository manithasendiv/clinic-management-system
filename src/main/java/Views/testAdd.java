package Views;

import Controllers.DoctorController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class testAdd {
    private JPanel BackPanel;
    private JTextField txtName;
    private JTextField txtSpecialization;
    private JTextField txtEmail;
    private JTextField txtContact;
    private JButton btnAdd;

    DoctorController objController;

    public testAdd() {
        objController = new DoctorController();
        int doctorCount = 0;


        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int did = doctorCount + 1;
                String name = txtName.getText();
                String specialization = txtSpecialization.getText();
                String email = txtEmail.getText();
                String contact = txtContact.getText();

                if (name.isEmpty() || specialization.isEmpty() || email.isEmpty() || contact.isEmpty()) {
                    JOptionPane.showMessageDialog(BackPanel, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    objController.addDoctor(did, name, specialization, email, contact);

                    if (!objController.addDoctorToDataBase()) {
                        JOptionPane.showMessageDialog(null, "Error in adding doctor");
                        return;
                    }
                    JOptionPane.showMessageDialog(null, "Doctor added successfully!");
                }
                txtName.setText("");
                txtSpecialization.setText("");
                txtEmail.setText("");
                txtContact.setText("");
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("doctor");
        frame.setSize(300,300);
        frame.setContentPane(new testAdd().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
