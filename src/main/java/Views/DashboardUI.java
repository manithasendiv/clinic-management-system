package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardUI {
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

    public DashboardUI() {
        CardLayout cardLayout = new CardLayout();
        ViewPanel.setLayout(cardLayout);

        logoArea.setOpaque(false);
        sidepanel1.setOpaque(false);
        LogoPanel.setOpaque(false);
        logoutArea.setOpaque(false);

        ViewPanel.setSize(600, 500);

//        patientsButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                PatientUI patientUI = new PatientUI();
//                ViewPanel.add(patientUI.getPatientUI(), "patientUI");
//                cardLayout.show(ViewPanel, "patientUI");
//            }
//        });
//
//        doctorScheduleButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
////                ViewPanel.removeAll();
//                DoctorScheduleUI doctorScheduleUI = new DoctorScheduleUI();
//                ViewPanel.add(doctorScheduleUI.getDoctorScheduleUI(), "doctorScheduleUI");
//                cardLayout.show(ViewPanel, "doctorScheduleUI");
//            }
//        });
//
//        newAppointmentButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                AppointmentUI appointmentUI = new AppointmentUI();
//                ViewPanel.add(appointmentUI.getAppointmentUI(), "appointmentUI");
//                cardLayout.show(ViewPanel, "appointmentUI");
//            }
//        });
//
//        reportsButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ReportUI reportUI = new ReportUI();
//                ViewPanel.add(reportUI.getReportUI(), "reportUI");
//                cardLayout.show(ViewPanel, "reportUI");
//            }
//        });
//
//        signoutButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                LoginUI loginUI = new LoginUI();
//                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(BackPanel);
//                frame.setContentPane(loginUI.getLoginUI());
//                frame.revalidate();
//            }
//        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CDC");
        frame.setContentPane(new DashboardUI().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 550);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
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
        doctorScheduleButton.setMinimumSize(new Dimension(0, 45));

        newAppointmentButton = new CustomComponents.CustomButton("New Appointment");
        reportsButton = new CustomComponents.CustomButton("Reports");

        reportsButton.setMinimumSize(new Dimension(0, 45));
        newAppointmentButton.setMinimumSize(new Dimension(0, 45));

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
