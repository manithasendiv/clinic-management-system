package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DoctorDashboardGUI {
    private JPanel ViewPanel;
    private JPanel BackPanel;
    private JLabel logoArea;
    private JButton signoutButton;
    private JPanel sidePanel2;
    private JPanel sidepanel1;
    private JPanel logoutArea;
    private JButton reportsButton;
    private JPanel LogoPanel;
    private JButton btnPatient;
    private JButton btnSchdeule;

    public DoctorDashboardGUI() {
        CardLayout cardLayout = new CardLayout();
        ViewPanel.setLayout(cardLayout);

        ViewPanel.setMinimumSize(new Dimension(400, 1000));

        logoArea.setOpaque(false);
        sidepanel1.setOpaque(false);
        LogoPanel.setOpaque(false);
        logoutArea.setOpaque(false);



        btnSchdeule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DoctorSchedulesGUI doctorSchedulesGUI = new DoctorSchedulesGUI();
                ViewPanel.removeAll();
                ViewPanel.add(doctorSchedulesGUI.getDoctorSchedulesGUI());
                ViewPanel.revalidate();
                ViewPanel.repaint();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CDC");
        frame.setContentPane(new DoctorDashboardGUI().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
    }

    public JPanel getDoctorDashboardUGI() {
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

        btnPatient = new CustomComponents.CustomButton("Patient");
        btnPatient.setMinimumSize(new Dimension(50, 40));


        btnSchdeule = new CustomComponents.CustomButton("Schedule");
        btnSchdeule.setMinimumSize(new Dimension(50, 40));

        reportsButton = new CustomComponents.CustomButton("Reports");
        reportsButton.setMinimumSize(new Dimension(50, 40));

        signoutButton = new CustomComponents.CustomButton("Sign Out");
        signoutButton.setMinimumSize(new Dimension(0, 40));
        signoutButton.setFont(new Font("Arial", Font.PLAIN, 18));

        ImageIcon logo = new ImageIcon("src/main/java/assets/logo.png");
        Image logoImage = logo.getImage();
        Image newLogo = logoImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logo = new ImageIcon(newLogo);
        logoArea = new JLabel(logo);

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
