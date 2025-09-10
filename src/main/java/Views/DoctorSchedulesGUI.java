package Views;

import javax.swing.*;
import java.awt.*;

public class DoctorSchedulesGUI {
    private JPanel BackPanel;
    private JTable table1;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;


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
        // TODO: place custom component creation code here
        CustomComponents.RoundedPanel panel = new CustomComponents.RoundedPanel(20);
        panel.setBorderColor(Color.blue);
        BackPanel = panel;

        addButton = new CustomComponents.CustomButton("Add Schedule");
        addButton.setMinimumSize(new Dimension(20, 40));

        updateButton = new CustomComponents.CustomButton("Update Schedule");
        updateButton.setMinimumSize(new Dimension(20, 40));
        deleteButton = new CustomComponents.CustomButton("Delete Schedule");
        deleteButton.setMinimumSize(new Dimension(20, 40));

    }
}
