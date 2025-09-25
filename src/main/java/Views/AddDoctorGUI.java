package Views;

import Controllers.DoctorController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddDoctorGUI {
    private JPanel BackPanel;
    private JTextField txtName;
    private JTextField txtSpecialization;
    private JTextField txtEmail;
    private JTextField txtContact;
    private JButton btnAdd;

    DoctorController objController;

    public AddDoctorGUI() {
        objController = new DoctorController();
        String doctorCount = "";


        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String did = doctorCount + 1;
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
        frame.setContentPane(new AddDoctorGUI().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public JPanel getAddDoctorGUI() {
        return BackPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        CustomComponents.RoundedPanel panel = new CustomComponents.RoundedPanel(20);
        panel.setBorderColor(Color.blue);
        BackPanel = panel;

        txtName = new CustomComponents.RoundedJTextField(20);
        txtSpecialization = new CustomComponents.RoundedJTextField(20);
        txtEmail = new CustomComponents.RoundedJTextField(20);
        txtContact = new CustomComponents.RoundedJTextField(20);

        btnAdd = new CustomComponents.CustomButton("Add Doctor");
        btnAdd.setMinimumSize(new Dimension(200,35));
    }
}
