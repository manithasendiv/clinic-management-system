package Views;

import javax.swing.*;

public class DoctorDashboardGUI {
    private JTabbedPane tabbedPane1;
    private JTable table1;
    private JButton addScheduleButton;
    private JButton updateScheduleButton;
    private JButton deleteScheduleButton;
    private JTable table2;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JButton button1;

    public DoctorDashboardGUI(){

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DoctorDashboardGUI");
        frame.setContentPane(new DoctorDashboardGUI().tabbedPane1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setVisible(true);
    }
}
