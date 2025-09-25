package Views;

import Controllers.RegistrationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGUI {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton createProfileButton;
    private JButton userLoginButton;
    private JLabel logoArea;
    private JPanel backPanel;
    private JPanel headerPanel;
    private JPanel loginForm;
    private JComboBox comboBox;
    private JTextField txtContact;

    RegistrationController registrationController;

    public RegisterGUI() {
        registrationController = new RegistrationController();
        loginForm.setPreferredSize(new Dimension(400, 600));


        createProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());
                String contact = txtContact.getText();
                String role = comboBox.getSelectedItem().toString();

                if(username.isEmpty() || password.isEmpty() || contact.isEmpty() || role.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                registrationController.addUser(username, password, contact, role);
                boolean isAdded = registrationController.addUserToDataBase();
                if(isAdded) {
                    JOptionPane.showMessageDialog(null, "User added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error in adding user", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        userLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame loginFrame = new JFrame("LoginUI");
                loginFrame.setContentPane(new LoginGUI().getBackPanel());
                loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                loginFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                loginFrame.setUndecorated(true);
                loginFrame.setVisible(true);
                SwingUtilities.getWindowAncestor(backPanel).dispose();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LoginGUI");
        frame.setContentPane(new RegisterGUI().backPanel);
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
        txtContact = new CustomComponents.RoundedJTextField(20);
        comboBox = new CustomComponents.CustomComboBox();

        createProfileButton = new CustomComponents.CustomButton("Login");
        userLoginButton = new CustomComponents.CustomButton("Create Profile");
        createProfileButton.setMinimumSize(new Dimension(200,35));
        userLoginButton.setMinimumSize(new Dimension(200,35));
    }
}
