package Views;

import javax.swing.*;
import java.awt.*;

public class LoginGUI {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton loginButton;
    private JButton createProfileButton;
    private JLabel logoArea;
    private JPanel backPanel;
    private JPanel headerPanel;
    private JPanel loginForm;

    public LoginGUI() {
        loginForm.setPreferredSize(new Dimension(400, 600));
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
