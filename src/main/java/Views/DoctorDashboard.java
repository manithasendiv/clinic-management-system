package Views;

import javax.swing.*;

public class DoctorDashboard {
    private JPanel mainPanel;
    private JButton test1;
    private JButton test2;
    private JTable table1;
    private JButton button4;
    private JButton button5;
    private JButton button6;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Doctor Dashboard");
        frame.setContentPane(new DoctorDashboard().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        test1 = new CustomComponents.CustomButton("Test 1");
        test2 = new CustomComponents.CustomButton("Test 2");

    }
}
