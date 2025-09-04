package Views;

import Models.PharmacyInventory;
import ServiceLayer.PharmacyInventoryService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;

public class PharmacyInventoryView {
    private JTextField txtName;
    private JTextField txtQuantity;
    private JTextField txtStock;
    private JTextField txtMDate;
    private JTextField txtEDate;
    private JTextField txtPrice;
    private JTextField txtDiscount;
    private JTextField txtSupplier;
    private JTextField txtBDate;
    private JButton btnSave;
    private JPanel panel1;
    private JPanel formPanel;

    private PharmacyInventoryService service = new PharmacyInventoryService();

    public PharmacyInventoryView() {
        setupUI();
        btnSave.addActionListener(e -> saveItem());
    }

    private void setupUI() {
        // Create main panel with border layout
        panel1 = new JPanel(new BorderLayout(10, 10));
        panel1.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Create form panel with improved layout
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Inventory Item Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add form fields with labels
        int row = 0;

        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        txtName = new JTextField(20);
        formPanel.add(txtName, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        formPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        txtQuantity = new JTextField(20);
        formPanel.add(txtQuantity, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        formPanel.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1;
        txtStock = new JTextField(20);
        formPanel.add(txtStock, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        formPanel.add(new JLabel("Manufacture Date (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        txtMDate = new JTextField(20);
        formPanel.add(txtMDate, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        formPanel.add(new JLabel("Expiry Date (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        txtEDate = new JTextField(20);
        formPanel.add(txtEDate, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        formPanel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        txtPrice = new JTextField(20);
        formPanel.add(txtPrice, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        formPanel.add(new JLabel("Discount:"), gbc);
        gbc.gridx = 1;
        txtDiscount = new JTextField(20);
        formPanel.add(txtDiscount, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        formPanel.add(new JLabel("Supplier ID:"), gbc);
        gbc.gridx = 1;
        txtSupplier = new JTextField(20);
        formPanel.add(txtSupplier, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        formPanel.add(new JLabel("Bought Date (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        txtBDate = new JTextField(20);
        formPanel.add(txtBDate, gbc);

        // Add save button
        gbc.gridx = 0;
        gbc.gridy = ++row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnSave = new JButton("Save Item");
        formPanel.add(btnSave, gbc);

        // Add form panel to main panel
        panel1.add(formPanel, BorderLayout.CENTER);
    }

    private void saveItem() {
        try {
            PharmacyInventory item = new PharmacyInventory();
            item.setItemName(txtName.getText());
            item.setQuantity(Integer.parseInt(txtQuantity.getText()));
            item.setStock(Integer.parseInt(txtStock.getText()));
            item.setManufactureDate(new SimpleDateFormat("yyyy-MM-dd").parse(txtMDate.getText()));
            item.setExpiredDate(new SimpleDateFormat("yyyy-MM-dd").parse(txtEDate.getText()));
            item.setSalePrice(Double.parseDouble(txtPrice.getText()));
            item.setDiscount(Double.parseDouble(txtDiscount.getText()));
            item.setSupplierID(Integer.parseInt(txtSupplier.getText()));
            item.setBoughtDate(new SimpleDateFormat("yyyy-MM-dd").parse(txtBDate.getText()));

            if (service.addInventoryItem(item)) {
                JOptionPane.showMessageDialog(panel1, "Item saved successfully!");
            } else {
                JOptionPane.showMessageDialog(panel1, "Failed to save item.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel1, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pharmacy Inventory");
        frame.setContentPane(new PharmacyInventoryView().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }
}