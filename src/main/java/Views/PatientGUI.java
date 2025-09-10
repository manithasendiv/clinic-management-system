package Views;

import Controllers.DoctorScheduleController;
import Controllers.DoctorController;
import Controllers.DoctorScheduleController;
import Controllers.PatientController;
import Models.DoctorSchedule;
import Models.Doctor;
import Models.DoctorSchedule;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    private JPanel BackPanel;
    private JComboBox doctorComboBox;
    private JTextField txtDate;
    private JTextField txtTime;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JLabel lblDoctor;
    private JLabel lblDate;
    private JLabel lblTime;

    PatientController objController;
    DoctorScheduleController objDoctorScheduleController;

    public PatientGUI(){
        objController = new PatientController();
        objDoctorScheduleController = new DoctorScheduleController();
        int patientCount = 0;



        try{
            ResultSet resultSet = objDoctorScheduleController.getAllSchedule();
            while (resultSet.next()){
                String docName = resultSet.getString("doctorName");
                comboBox1.addItem(docName);
            }

            if(resultSet != null){
                JOptionPane.showMessageDialog(null, "No doctor schedules currently available");
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error in fetching doctor schedule list");
        }




        btnAddPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int patientID = patientCount + 1 ;
                String Name = txtName.getText();
                int Age =Integer.parseInt(txtAge.getText());;
                String Address = txtAddress.getText();
                int Contact = Integer.parseInt(txtContact.getText());
                String Email = txtEmail.getText();

                if (Name.isEmpty() || txtAge.getText().isEmpty() || Address.isEmpty() || txtContact.getText().isEmpty() || Email.isEmpty()){
                    JOptionPane.showMessageDialog(BackPanel, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    objController.addPatient(patientID,Name,Age,Address,Contact,Email);

                    if (!objController.addPatientToDataBase()) {
                        JOptionPane.showMessageDialog(null, "Error in adding patient");
                        return;
                    }
                    JOptionPane.showMessageDialog(null, "Patient added successfully!");
                }
                txtName.setText("");
                txtAddress.setText("");
                txtEmail.setText("");
                txtContact.setText("");
            }

        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("doctor");
        frame.setSize(300,300);
        frame.setContentPane(new PatientGUI().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

public JPanel getPatientGUI() {
    return BackPanel;
}
    }

