package Views;

import Controllers.LoginController;
import Models.User;
import ServiceLayer.LoginService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton loginButton;
    private JButton createProfileButton;
    private JLabel logoArea;
    private JPanel backPanel;
    private JPanel headerPanel;
    private JPanel loginForm;

    LoginController loginController;

    public LoginGUI() {

        loginController = new LoginController();
        loginForm.setPreferredSize(new Dimension(400, 600));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String username = txtUsername.getText();
                        char[] password = txtPassword.getPassword();
                        String pass = new String(password);

                        User loggedInUser = loginController.validateUser(username, pass);

                        if (loggedInUser != null) {
                            String role = loggedInUser.getRole();

                            switch (role) {
                                case "Receptionist":
                                    JFrame homeFrame = new JFrame("HomeUI");
                                    homeFrame.setContentPane(new ReceptionistDashboardUI().getDashboardUI());
                                    homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    homeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                                    homeFrame.setUndecorated(true);
                                    homeFrame.setVisible(true);
                                    SwingUtilities.getWindowAncestor(backPanel).dispose();
                                    break;

                                case "Doctor":
                                    JFrame doctorFrame = new JFrame("DoctorUI");
                                    doctorFrame.setContentPane(new DoctorDashboardGUI().getDoctorDashboardUGI());
                                    doctorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    doctorFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                                    doctorFrame.setUndecorated(true);
                                    doctorFrame.setVisible(true);
                                    SwingUtilities.getWindowAncestor(backPanel).dispose();
                                    break;

                                case "Admin":
                                    // TODO: open admin dashboard
                                    break;

                                default:
                                    JOptionPane.showMessageDialog(null, "Login successful, but no role assigned.");
                                    break;
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
                        }
                    }
                });

            }
        });
        createProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame registerFrame = new JFrame("RegisterUI");
                registerFrame.setContentPane(new RegisterGUI().getBackPanel());
                registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                registerFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                registerFrame.setUndecorated(true);
                registerFrame.setVisible(true);
                SwingUtilities.getWindowAncestor(backPanel).dispose();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LoginGUI");
        frame.setContentPane(new LoginGUI().backPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
    }

    public JPanel getBackPanel() {
        return backPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here


        backPanel = new javax.swing.JPanel() {
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D g2d) {
                    float startX = 0.0f;
                    float startY = 0.0f;
                    LinearGradientPaint p = getLinearGradientPaint(startX, startY);

                    g2d.setPaint(p);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    super.paintComponent(g);
                }
            }

            private LinearGradientPaint getLinearGradientPaint(float startX, float startY) {
                float endX = 0.1f;
                float endY = getHeight();

                float[] fractions = {0.0f, 0.4f, 1.0f};
                Color[] colors = {
                        new Color(22, 37, 84, 255),
                        new Color(49, 82, 186, 255),
                        new Color(22, 37, 84, 255)
                };

                // Create a LinearGradientPaint object
                return new LinearGradientPaint(startX, startY, endX, endY, fractions, colors);
            }
        };

        loginForm = new CustomComponents.RoundedPanel(30);

        ImageIcon logo = new ImageIcon("src/main/java/assets/logo.png");
        Image logoImage = logo.getImage();
        Image newLogo = logoImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logo = new ImageIcon(newLogo);
        logoArea = new JLabel(logo);

        txtUsername = new CustomComponents.RoundedJTextField(20);
        txtPassword = new CustomComponents.RoundedJPasswordField(20);

        loginButton = new CustomComponents.CustomButton("Login");
        createProfileButton = new CustomComponents.CustomButton("Create Profile");
        loginButton.setMinimumSize(new Dimension(200,35));
        createProfileButton.setMinimumSize(new Dimension(200,35));
    }
}
