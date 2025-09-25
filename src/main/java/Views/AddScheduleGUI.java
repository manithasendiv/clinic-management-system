package Views;

import Controllers.DoctorController;
import Controllers.DoctorScheduleController;
import Models.Doctor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AddScheduleGUI {
    private JPanel backPanel;
    private JComboBox doctorNameComboBox;
    private JTextField txtAvailableDate;
    private JTextField txtAvailableTime;
    private JButton addScheduleButton;


    DoctorController doctorController;
    DoctorScheduleController doctorScheduleController;

    List<Doctor> doctorList;

    public AddScheduleGUI(){
        doctorController = new DoctorController();
        doctorScheduleController = new DoctorScheduleController();

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
        addScheduleButton.addActionListener(new ActionListener() {
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
                        JOptionPane.showMessageDialog(null, "Schedule added successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add schedule");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });
    }

    public JPanel getAddScheduleGUI() {
        return backPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("AddScheduleGUI");
        frame.setContentPane(new AddScheduleGUI().backPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setVisible(true);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        CustomComponents.RoundedPanel panel = new CustomComponents.RoundedPanel(20);
        panel.setBorderColor(Color.blue);
        backPanel = panel;

        doctorNameComboBox = new CustomComponents.CustomComboBox();
        txtAvailableDate = new CustomComponents.RoundedJTextField(20);
        txtAvailableTime = new CustomComponents.RoundedJTextField(20);

        addScheduleButton = new CustomComponents.CustomButton("Add Schedule");
        addScheduleButton.setMinimumSize(new Dimension(400,35));
    }
}
