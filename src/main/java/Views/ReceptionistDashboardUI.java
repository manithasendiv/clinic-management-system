package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReceptionistDashboardUI {
    private JButton patientsButton;
    private JButton doctorScheduleButton;
    private JButton newAppointmentButton;
    private JPanel ViewPanel;
    private JPanel BackPanel;
    private JLabel logoArea;
    private JButton signoutButton;
    private JPanel sidePanel2;
    private JPanel sidepanel1;
    private JPanel logoutArea;
    private JButton reportsButton;
    private JPanel LogoPanel;

    public ReceptionistDashboardUI() {
        CardLayout cardLayout = new CardLayout();
        ViewPanel.setLayout(cardLayout);

        ViewPanel.setMinimumSize(new Dimension(1200, 1000));

        logoArea.setOpaque(false);
        sidepanel1.setOpaque(false);
        LogoPanel.setOpaque(false);
        logoutArea.setOpaque(false);

        ViewPanel.setSize(600, 500);

        patientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PatientGUI patientGUI = new PatientGUI();
                ViewPanel.removeAll();
                ViewPanel.add(patientGUI.getPatientGUI());
                ViewPanel.revalidate();
                ViewPanel.repaint();
            }
        });
        doctorScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddDoctorGUI addDoctorGUI = new AddDoctorGUI();
                ViewPanel.removeAll();
                ViewPanel.add(addDoctorGUI.getAddDoctorGUI());
                ViewPanel.revalidate();
                ViewPanel.repaint();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CDC");
        frame.setContentPane(new ReceptionistDashboardUI().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
    }

    public JPanel getDashboardUI() {
        return BackPanel;
    }



    private void createUIComponents() {
        // TODO: place custom component creation code here
        sidePanel2 = new JPanel() {
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D g2d) {

                    // Define the start and end points of the gradient
                    float startX = 0.0f;
                    float startY = 0.0f;
                    float endX = 0.1f;
                    float endY = getHeight();

                    float[] fractions = {0.5f, 1.0f};
                    Color[] colors = {
                            new Color(22, 37, 84, 255),
                            new Color(49, 82, 186, 255)

                    };

                    // Create a LinearGradientPaint object
                    LinearGradientPaint p = new LinearGradientPaint(startX, startY, endX, endY, fractions, colors);

                    g2d.setPaint(p);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    super.paintComponent(g);
                }
            }
        };
        patientsButton = new CustomComponents.CustomButton("Patients");
        patientsButton.setMinimumSize(new Dimension(50, 45));

        doctorScheduleButton = new CustomComponents.CustomButton("Doctor Schedule");
        doctorScheduleButton.setMinimumSize(new Dimension(50, 45));

        newAppointmentButton = new CustomComponents.CustomButton("New Appointment");
        reportsButton = new CustomComponents.CustomButton("Reports");

        reportsButton.setMinimumSize(new Dimension(50, 45));
        newAppointmentButton.setMinimumSize(new Dimension(50, 45));

        signoutButton = new CustomComponents.CustomButton("Sign Out");
        signoutButton.setMinimumSize(new Dimension(0, 40));

        patientsButton.setFont(new Font("Arial", Font.PLAIN, 20));
        doctorScheduleButton.setFont(new Font("Arial", Font.PLAIN, 20));
        newAppointmentButton.setFont(new Font("Arial", Font.PLAIN, 20));
        signoutButton.setFont(new Font("Arial", Font.PLAIN, 18));

        ImageIcon logo = new ImageIcon("src/main/java/assets/logo.png");
        Image logoImage = logo.getImage();
        Image newLogo = logoImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logo = new ImageIcon(newLogo);
        logoArea = new JLabel(logo);

        ImageIcon patientIcon = new ImageIcon("src/main/java/assets/icons/patient.png");
        Image patientIconImage = patientIcon.getImage();
        Image newPatientIcon = patientIconImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        patientIcon = new ImageIcon(newPatientIcon);
        patientsButton.setIcon(patientIcon);

        ImageIcon doctorIcon = new ImageIcon("src/main/java/assets/icons/doctor-appointment.png");
        Image doctorIconImage = doctorIcon.getImage();
        Image newDoctorIcon = doctorIconImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        doctorIcon = new ImageIcon(newDoctorIcon);
        doctorScheduleButton.setIcon(doctorIcon);

        ImageIcon appointmentIcon = new ImageIcon("src/main/java/assets/icons/medical.png");
        Image appointmentIconImage = appointmentIcon.getImage();
        Image newAppointmentIcon = appointmentIconImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        appointmentIcon = new ImageIcon(newAppointmentIcon);
        newAppointmentButton.setIcon(appointmentIcon);

        ImageIcon reportIcon = new ImageIcon("src/main/java/assets/icons/report.png");
        Image reportIconImage = reportIcon.getImage();
        Image newReportIcon = reportIconImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        reportIcon = new ImageIcon(newReportIcon);
        reportsButton.setIcon(reportIcon);

        ImageIcon signoutIcon = new ImageIcon("src/main/java/assets/icons/exit.png");
        Image signoutIconImage = signoutIcon.getImage();
        Image newSignoutIcon = signoutIconImage.getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        signoutIcon = new ImageIcon(newSignoutIcon);
        signoutButton.setIcon(signoutIcon);
    }
}
