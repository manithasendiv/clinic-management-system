package Views;

import Controllers.BillController;
import Models.Bill;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class BillUI {
    JPanel MainPanel;
    private JPanel AddBillForm;
    private JTextField textFieldPatientId;
    private JTextField textFieldService;
    private JTextField textFieldPharmacyItems;
    private JTextField textFieldTotal;
    private JTextField textFieldDiscount;
    private JButton btnRemove;
    private JButton btnAdd;
    private JButton btnView;
    private JButton btnUpdate;
    private JLabel txtPatientId;
    private JLabel txtService;
    private JLabel txtPharmacyItems;
    private JLabel txtTotal;
    private JLabel txtDiscount;
    private JLabel titleLabel;

    BillController objController;

    public BillUI() {
        initCustomUI();

        objController = new BillController();

        // ADD BUTTON
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String patientId = textFieldPatientId.getText().trim();
                String service = textFieldService.getText().trim();
                String pharmacyItems = textFieldPharmacyItems.getText().trim();
                String total = textFieldTotal.getText().trim();
                String discount = textFieldDiscount.getText().trim();

                if (patientId.isEmpty() || service.isEmpty() || pharmacyItems.isEmpty() || total.isEmpty() || discount.isEmpty()) {
                    JOptionPane.showMessageDialog(MainPanel, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Bill bill = objController.addBill(
                            Integer.parseInt(patientId),
                            service,
                            pharmacyItems,
                            Double.parseDouble(total),
                            Double.parseDouble(discount)
                    );

                    if (objController.addBillToDatabase()) {
                        JOptionPane.showMessageDialog(MainPanel, "Bill added successfully!");
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(MainPanel, "Error in adding bill", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // REMOVE BUTTON
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = JOptionPane.showInputDialog(MainPanel, "Enter Bill ID to remove:");
                    if (input != null && !input.trim().isEmpty()) {
                        int billId = Integer.parseInt(input.trim());
                        boolean success = objController.removeBill(billId);

                        if (success) {
                            JOptionPane.showMessageDialog(MainPanel, "Bill removed successfully!");
                        } else {
                            JOptionPane.showMessageDialog(MainPanel, "Failed to remove bill.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainPanel, "Invalid Bill ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // VIEW BUTTON
        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Bill> billList = objController.getAllBills();

                if (billList.isEmpty()) {
                    JOptionPane.showMessageDialog(MainPanel, "No bills found!");
                    return;
                }

                String[] columnNames = {"Bill ID", "Patient ID", "Service", "Pharmacy Items", "Total", "Discount"};
                Object[][] data = new Object[billList.size()][6];

                for (int i = 0; i < billList.size(); i++) {
                    Bill b = billList.get(i);
                    data[i][0] = b.getBillId();
                    data[i][1] = b.getPatientId();
                    data[i][2] = b.getService();
                    data[i][3] = b.getPharmacyItems();
                    data[i][4] = b.getTotal();
                    data[i][5] = b.getDiscount();
                }

                JTable table = new JTable(data, columnNames);
                JScrollPane scrollPane = new JScrollPane(table);

                JFrame viewFrame = new JFrame("View Bills");
                viewFrame.setSize(700, 400);
                viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                viewFrame.add(scrollPane);
                viewFrame.setLocationRelativeTo(null);
                viewFrame.setVisible(true);
            }
        });

        // UPDATE BUTTON
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = JOptionPane.showInputDialog(MainPanel, "Enter Bill ID to update:");
                    if (input != null && !input.trim().isEmpty()) {
                        int billId = Integer.parseInt(input.trim());
                        Bill updatedBill = objController.addBill(
                                Integer.parseInt(textFieldPatientId.getText().trim()),
                                textFieldService.getText().trim(),
                                textFieldPharmacyItems.getText().trim(),
                                Double.parseDouble(textFieldTotal.getText().trim()),
                                Double.parseDouble(textFieldDiscount.getText().trim())
                        );

                        if (objController.updateBill(billId, updatedBill)) {
                            JOptionPane.showMessageDialog(MainPanel, "Bill updated successfully!");
                            clearFields();
                        } else {
                            JOptionPane.showMessageDialog(MainPanel, "Failed to update bill.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainPanel, "Invalid Bill ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void initCustomUI() {
        MainPanel = new JPanel(new BorderLayout());
        MainPanel.setBackground(new Color(245, 247, 250));

        titleLabel = new JLabel("Bill Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(60, 70, 90));
        titleLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
        MainPanel.add(titleLabel, BorderLayout.NORTH);

        AddBillForm = new JPanel(new GridBagLayout());
        AddBillForm.setBackground(new Color(245, 247, 250));
        AddBillForm.setBorder(new EmptyBorder(20, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        txtPatientId = createStyledLabel("Patient ID:");
        txtService = createStyledLabel("Service:");
        txtPharmacyItems = createStyledLabel("Pharmacy Items:");
        txtTotal = createStyledLabel("Total Amount:");
        txtDiscount = createStyledLabel("Discount (%):");

        textFieldPatientId = createStyledTextField();
        textFieldService = createStyledTextField();
        textFieldPharmacyItems = createStyledTextField();
        textFieldTotal = createStyledTextField();
        textFieldDiscount = createStyledTextField();

        btnAdd = createElegantButton("Add Bill", new Color(76, 175, 80));
        btnRemove = createElegantButton("Remove Bill", new Color(76, 175, 80));
        btnView = createElegantButton("View Bills", new Color(76, 175, 80));
        btnUpdate = createElegantButton("Update Bill", new Color(76, 175, 80));

        // Add Labels & Fields
        gbc.gridx = 0; gbc.gridy = 0; AddBillForm.add(txtPatientId, gbc);
        gbc.gridx = 1; AddBillForm.add(textFieldPatientId, gbc);
        gbc.gridx = 0; gbc.gridy = 1; AddBillForm.add(txtService, gbc);
        gbc.gridx = 1; AddBillForm.add(textFieldService, gbc);
        gbc.gridx = 0; gbc.gridy = 2; AddBillForm.add(txtPharmacyItems, gbc);
        gbc.gridx = 1; AddBillForm.add(textFieldPharmacyItems, gbc);
        gbc.gridx = 0; gbc.gridy = 3; AddBillForm.add(txtTotal, gbc);
        gbc.gridx = 1; AddBillForm.add(textFieldTotal, gbc);
        gbc.gridx = 0; gbc.gridy = 4; AddBillForm.add(txtDiscount, gbc);
        gbc.gridx = 1; AddBillForm.add(textFieldDiscount, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(new Color(245, 247, 250));
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRemove);
        buttonPanel.add(btnView);
        buttonPanel.add(btnUpdate);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        AddBillForm.add(buttonPanel, gbc);

        MainPanel.add(AddBillForm, BorderLayout.CENTER);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(80, 90, 110));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(18);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 210, 220), 1),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        textField.setBackground(Color.WHITE);
        textField.setForeground(new Color(60, 70, 90));
        return textField;
    }

    private JButton createElegantButton(String text, Color baseColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(baseColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(baseColor.brighter());
                } else {
                    g2.setColor(baseColor);
                }

                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2.dispose();

                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {}
        };

        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void clearFields() {
        textFieldPatientId.setText("");
        textFieldService.setText("");
        textFieldPharmacyItems.setText("");
        textFieldTotal.setText("");
        textFieldDiscount.setText("");
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace(); }

        JFrame frame = new JFrame("Bill Management System");
        frame.setSize(600, 500);
        frame.setContentPane(new BillUI().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }
}
