package Views;

import Controllers.DoctorScheduleController;
import Controllers.PatientController;
import com.formdev.flatlaf.FlatLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

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
    private JTable tableAppointments;
    private JScrollPane scrollPane;

    PatientController objController;
    DoctorScheduleController objDoctorScheduleController;

    public PatientGUI(){
        updateScheduleTable();
        objController = new PatientController();
        objDoctorScheduleController = new DoctorScheduleController();

        scrollPane.getVerticalScrollBar().setUI(new CustomComponents.CustomScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new CustomComponents.CustomScrollBarUI());

        btnAddPatient.setMinimumSize(new Dimension(35, 35));
        btnUpdate.setMinimumSize(new Dimension(35, 35));
        btnDelete.setMinimumSize(new Dimension(35, 35));

        int patientCount = 0;

        try{
            ResultSet resultSet = objDoctorScheduleController.getAllSchedule();
            while (resultSet.next()){
                String docName = resultSet.getString("doctorName") + " - " + resultSet.getString("availableDate") + " - " + resultSet.getString("availableTime");
                doctorComboBox.addItem(docName);
            }

            if(resultSet == null){
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
                String SelectDoc = doctorComboBox.getSelectedItem().toString();

                if (Name.isEmpty() || txtAge.getText().isEmpty() || Address.isEmpty() || txtContact.getText().isEmpty() || Email.isEmpty()){
                    JOptionPane.showMessageDialog(BackPanel, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    objController.addPatient(patientID,Name,Age,Address,Contact,Email,SelectDoc);
                    if (!objController.addPatientToDataBase()) {
                        JOptionPane.showMessageDialog(null, "Error in adding patient");
                        return;
                    }
                    updateScheduleTable();
                    JOptionPane.showMessageDialog(null, "Patient added successfully!");
                }
                txtName.setText("");
                txtAge.setText("");
                txtAddress.setText("");
                txtContact.setText("");
                txtEmail.setText("");
            }

        });


        tableAppointments.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = tableAppointments.getSelectedRow();
            if (selectedRow != -1) {
                txtName.setText(tableAppointments.getValueAt(selectedRow,1).toString());
                txtAge.setText(tableAppointments.getValueAt(selectedRow,2).toString());
                txtAddress.setText(tableAppointments.getValueAt(selectedRow,3).toString());
                txtContact.setText(tableAppointments.getValueAt(selectedRow,4).toString());
                txtEmail.setText(tableAppointments.getValueAt(selectedRow,5).toString());
                doctorComboBox.setSelectedItem(tableAppointments.getValueAt(selectedRow,6).toString());
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableAppointments.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select an appointment to update");
                    return;
                }

                int patientID = Integer.parseInt(tableAppointments.getValueAt(selectedRow, 0).toString());
                String Name = txtName.getText();
                int Age = Integer.parseInt(txtAge.getText());
                String Address = txtAddress.getText();
                int Contact = Integer.parseInt(txtContact.getText());
                String Email = txtEmail.getText();
                String SelectDoc = doctorComboBox.getSelectedItem().toString();

                objController.addPatient(patientID, Name, Age, Address, Contact, Email, SelectDoc);
                if (objController.updatePatient()) {
                    updateScheduleTable();
                    JOptionPane.showMessageDialog(null, "Appointment updated successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update appointment");
                }

                txtName.setText("");
                txtAge.setText("");
                txtAddress.setText("");
                txtContact.setText("");
                txtEmail.setText("");
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableAppointments.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select an appointment to delete");
                    return;
                }

                int patientID = Integer.parseInt(tableAppointments.getValueAt(selectedRow, 0).toString());

                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this appointment?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (objController.deletePatient(patientID)) {
                        updateScheduleTable();
                        JOptionPane.showMessageDialog(null, "Appointment deleted successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to delete appointment");
                    }
                }

                txtName.setText("");
                txtAge.setText("");
                txtAddress.setText("");
                txtContact.setText("");
                txtEmail.setText("");
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

    public void updateScheduleTable(){
        objController = new PatientController();
        try{
            ResultSet resultSet = objController.getAllPatients();
            ResultSetMetaData metaData = resultSet.getMetaData();

            int columns = metaData.getColumnCount();
            DefaultTableModel model = (DefaultTableModel) tableAppointments.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Object[] row = new Object[columns];
                for (int i = 1; i <= columns; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in fetching patient appointments");
            throw new RuntimeException(e);
        }
    }

public JPanel getPatientGUI() {
    return BackPanel;
}

    private void createUIComponents() {
        // TODO: place custom component creation code here
        CustomComponents.RoundedPanel panel = new CustomComponents.RoundedPanel(20);
        panel.setBorderColor(Color.blue);
        BackPanel = panel;

        DefaultTableModel model = new DefaultTableModel();
        tableAppointments = new JTable(model);

        model.addColumn("Patient ID");
        model.addColumn("Name");
        model.addColumn("Age");
        model.addColumn("Address");
        model.addColumn("Contact");
        model.addColumn("Email");
        model.addColumn("Doctor");
        
        tableAppointments.setRowHeight(30);
        tableAppointments.setFont(new Font("Poppins", Font.PLAIN, 14));
        tableAppointments.setRowHeight(30);
        tableAppointments.setShowGrid(true);
        tableAppointments.setShowVerticalLines(false);

        txtAddress = new CustomComponents.RoundedJTextField(20);
        txtContact = new CustomComponents.RoundedJTextField(20);
        txtEmail = new CustomComponents.RoundedJTextField(20);
        txtName = new CustomComponents.RoundedJTextField(20);
        txtAge = new CustomComponents.RoundedJTextField(20);

        doctorComboBox = new CustomComponents.CustomComboBox();

        btnAddPatient = new CustomComponents.CustomButton("Add Patient");
        btnAddPatient.setMinimumSize(new Dimension(40, 35));

        btnUpdate = new CustomComponents.CustomButton("Update Appointment");
        btnUpdate.setMinimumSize(new Dimension(40, 35));

        btnDelete = new CustomComponents.CustomButton("Delete Appointment");
        btnDelete.setMinimumSize(new Dimension(40, 35));
    }
}

