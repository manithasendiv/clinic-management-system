package Views;

import Controllers.DoctorController;
import Controllers.DoctorScheduleController;
import Models.Doctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorSchedulesGUI {
    private JPanel BackPanel;
    private JTable scheduleTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField txtAvailableDate;
    private JTextField txtAvailableTime;
    private JComboBox doctorNameComboBox;
    private JScrollPane scrollPane;

    DoctorScheduleController doctorScheduleController;
    DoctorController doctorController;
    List<Doctor> doctorList;

    public DoctorSchedulesGUI() {
        updateScheduleTable();
        doctorScheduleController = new DoctorScheduleController();
        doctorController = new DoctorController();

        scrollPane.getVerticalScrollBar().setUI(new CustomComponents.CustomScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new CustomComponents.CustomScrollBarUI());


        doctorList = new ArrayList<>();
        try {
            ResultSet resultSet = doctorController.getAllDoctors();

            while (resultSet.next()) {
                Doctor doctor = new Doctor(
                        resultSet.getString("DocID"),
                        resultSet.getString("DocName"),
                        resultSet.getString("specialization"),
                        resultSet.getString("email"),
                        resultSet.getString("contact")
                );
                doctorList.add(doctor);
            }

            for (Doctor doc : doctorList) {
                doctorNameComboBox.addItem(doc.getName());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in fetching doctor list");
            throw new RuntimeException(e);
        }

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedDoctorName = (String) doctorNameComboBox.getSelectedItem();
                    Doctor selectedDoctor = null;
                    for (Doctor doc : doctorList) {
                        if (doc.getName().equals(selectedDoctorName)) {
                            selectedDoctor = doc;
                            break;
                        }
                    }

                    if (selectedDoctor == null) {
                        JOptionPane.showMessageDialog(null, "Selected doctor not found");
                        return;
                    }

                    String availableDate = txtAvailableDate.getText();
                    String availableTime = txtAvailableTime.getText();

                    doctorScheduleController.addDoctorSchedule(0, selectedDoctor, availableDate, availableTime);
                    boolean isAdded = doctorScheduleController.addDoctorScheduleToDataBase();

                    if (isAdded) {
                        updateScheduleTable();
                        JOptionPane.showMessageDialog(null, "Schedule added successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add schedule");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
                txtAvailableDate.setText("");
                txtAvailableTime.setText("");
            }
        });

        scheduleTable.getSelectionModel().addListSelectionListener(e -> {;
            int selectedRow = scheduleTable.getSelectedRow();
            if (selectedRow != -1) {

                doctorNameComboBox.setSelectedItem(scheduleTable.getValueAt(selectedRow, 1).toString());

                Object obj = scheduleTable.getValueAt(selectedRow, 2);
                String date = "";

                if (obj != null) {
                    if (obj instanceof java.sql.Date) {
                        java.sql.Date convertedDate = (java.sql.Date) obj;
                        date = convertedDate.toString();
                    } else {
                        date = obj.toString();
                    }
                }

                txtAvailableDate.setText(date);
                txtAvailableTime.setText(scheduleTable.getValueAt(selectedRow, 3).toString());

            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDoctorName = (String) doctorNameComboBox.getSelectedItem();
                Doctor selectedDoctor = null;
                for (Doctor doc : doctorList) {
                    if (doc.getName().equals(selectedDoctorName)) {
                        selectedDoctor = doc;
                        break;
                    }
                }

                String scheduleId = scheduleTable.getValueAt(scheduleTable.getSelectedRow(), 0).toString();
                String availableDate = txtAvailableDate.getText();
                String availableTime = txtAvailableTime.getText();

                boolean isUpdated = doctorScheduleController.updateDoctorSchedule(scheduleId, selectedDoctor.getName(), availableDate, availableTime);

                if (isUpdated) {
                    updateScheduleTable();
                    JOptionPane.showMessageDialog(null, "Schedule updated successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update schedule");
                }
                txtAvailableDate.setText("");
                txtAvailableTime.setText("");
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String scheduleId = scheduleTable.getValueAt(scheduleTable.getSelectedRow(), 0).toString();
                boolean isRemoved =  doctorScheduleController.removeDoctorSchedule(scheduleId);
                if (isRemoved) {
                    updateScheduleTable();
                    JOptionPane.showMessageDialog(null, "Schedule deleted successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete schedule");
                }
                txtAvailableDate.setText("");
                txtAvailableTime.setText("");
            }
        });
    }

    public void updateScheduleTable(){
        doctorScheduleController = new DoctorScheduleController();
        try{
            ResultSet resultSet = doctorScheduleController.getAllSchedule();
            ResultSetMetaData metaData = resultSet.getMetaData();

            int columns = metaData.getColumnCount();
            DefaultTableModel model = (DefaultTableModel) scheduleTable.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Object[] row = new Object[columns];
                for (int i = 1; i <= columns; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error in fetching schedules");
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DoctorSchedulesGUI");
        frame.setContentPane(new DoctorSchedulesGUI().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setVisible(true);
    }

    public JPanel getDoctorSchedulesGUI() {
        return BackPanel;
    }

    private void createUIComponents() {
        // Round Panel
        CustomComponents.RoundedPanel panel = new CustomComponents.RoundedPanel(20);
        panel.setBorderColor(Color.blue);
        BackPanel = panel;

        // Buttons
        addButton = new CustomComponents.CustomButton("Add Schedule");
        addButton.setMinimumSize(new Dimension(200, 40));
        updateButton = new CustomComponents.CustomButton("Update Schedule");
        updateButton.setMinimumSize(new Dimension(200, 40));
        deleteButton = new CustomComponents.CustomButton("Delete Schedule");
        deleteButton.setMinimumSize(new Dimension(200, 40));

        doctorNameComboBox = new CustomComponents.CustomComboBox();
        txtAvailableDate = new CustomComponents.RoundedJTextField(20);
        txtAvailableTime = new CustomComponents.RoundedJTextField(20);

        // Table
        DefaultTableModel model = new DefaultTableModel();
        scheduleTable = new JTable(model);

        model.addColumn("Schedule ID");
        model.addColumn("Doctor Name");
        model.addColumn("Day");
        model.addColumn("Time");

        scheduleTable.setRowHeight(30);
        scheduleTable.setFont(new Font("Poppins", Font.PLAIN, 14));
        scheduleTable.setRowHeight(30);
        scheduleTable.setShowGrid(true);
        scheduleTable.setShowVerticalLines(false);
    }
}
