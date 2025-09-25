package Views;

import Models.PharmacyInventory;
import Controllers.PharmacyInventoryController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;

public class PharmacyInventoryView {
    private JTextField txtName, txtQuantity, txtStock, txtMDate, txtEDate, txtPrice, txtDiscount, txtSupplier, txtBDate;
    private JButton btnSave, updateButton, viewButton, deleteButton, clearButton;
    private JTable table1;
    private JPanel panel1;

    private PharmacyInventoryController service = new PharmacyInventoryController();

    public PharmacyInventoryView() {
        panel1 = new JPanel(new BorderLayout(10, 10));
        panel1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---------- FORM ----------
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Inventory Item Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        txtName = new JTextField(20);
        txtQuantity = new JTextField(20);
        txtStock = new JTextField(20);
        txtMDate = new JTextField(20);
        txtEDate = new JTextField(20);
        txtPrice = new JTextField(20);
        txtDiscount = new JTextField(20);
        txtSupplier = new JTextField(20);
        txtBDate = new JTextField(20);

        addField(formPanel, gbc, 0, "Name:", txtName, "Enter item name");
        addField(formPanel, gbc, 1, "Quantity:", txtQuantity, "Enter available quantity");
        addField(formPanel, gbc, 2, "Stock:", txtStock, "Enter stock count");
        addField(formPanel, gbc, 3, "Mfg Date (yyyy-MM-dd):", txtMDate, "Manufacture date");
        addField(formPanel, gbc, 4, "Exp Date (yyyy-MM-dd):", txtEDate, "Expiration date");
        addField(formPanel, gbc, 5, "Price:", txtPrice, "Selling price");
        addField(formPanel, gbc, 6, "Discount:", txtDiscount, "Discount in %");
        addField(formPanel, gbc, 7, "Supplier ID:", txtSupplier, "Supplier unique ID");
        addField(formPanel, gbc, 8, "Bought Date (yyyy-MM-dd):", txtBDate, "Date item was bought");

        // ---------- BUTTONS ----------
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnSave = new JButton("💾 Save");
        viewButton = new JButton("👁 View");
        updateButton = new JButton("✏ Update");
        deleteButton = new JButton("🗑 Delete");
        clearButton = new JButton("🔄 Clear");

        Dimension btnSize = new Dimension(120, 35);
        for (JButton btn : new JButton[]{btnSave, viewButton, updateButton, deleteButton, clearButton}) {
            btn.setPreferredSize(btnSize);
        }

        buttonPanel.add(btnSave);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // ---------- TABLE ----------
        String[] cols = {"ID", "Name", "Quantity", "Stock", "Mfg Date", "Exp Date", "Price", "Discount", "SupplierID", "Bought Date"};
        table1 = new JTable(new DefaultTableModel(new Object[0][10], cols));
        table1.setRowHeight(25);
        table1.setFillsViewportHeight(true);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Center align cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table1.getColumnCount(); i++) {
            table1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table1);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Inventory Records"));

        // Add panels to main
        panel1.add(topPanel, BorderLayout.NORTH);
        panel1.add(scrollPane, BorderLayout.CENTER);

        // ---------- ACTIONS ----------
        btnSave.addActionListener(e -> saveItem());
        viewButton.addActionListener(e -> loadItems());
        updateButton.addActionListener(e -> updateItem());
        deleteButton.addActionListener(e -> deleteItem());
        clearButton.addActionListener(e -> clearForm());

        table1.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());

        loadItems();
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int y, String label, JTextField field, String tooltip) {
        gbc.gridx = 0; gbc.gridy = y;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        field.setToolTipText(tooltip);
        panel.add(field, gbc);
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
                JOptionPane.showMessageDialog(panel1, "✅ Item saved successfully!");
                loadItems();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(panel1, "❌ Failed to save item.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel1, "⚠ Error: " + ex.getMessage());
        }
    }

    private void loadItems() {
        try {
            var items = service.getAllItems();
            String[] cols = {"ID", "Name", "Quantity", "Stock", "Mfg Date", "Exp Date", "Price", "Discount", "SupplierID", "Bought Date"};
            Object[][] data = new Object[items.size()][10];

            for (int i = 0; i < items.size(); i++) {
                PharmacyInventory item = items.get(i);
                data[i][0] = item.getItemID();
                data[i][1] = item.getItemName();
                data[i][2] = item.getQuantity();
                data[i][3] = item.getStock();
                data[i][4] = item.getManufactureDate();
                data[i][5] = item.getExpiredDate();
                data[i][6] = item.getSalePrice();
                data[i][7] = item.getDiscount();
                data[i][8] = item.getSupplierID();
                data[i][9] = item.getBoughtDate();
            }

            table1.setModel(new DefaultTableModel(data, cols));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel1, "⚠ Error loading items: " + ex.getMessage());
        }
    }

    private void updateItem() {
        try {
            int selectedRow = table1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel1, "⚠ Select a row to update.");
                return;
            }

            PharmacyInventory item = new PharmacyInventory();
            item.setItemID((int) table1.getModel().getValueAt(selectedRow, 0));
            item.setItemName(txtName.getText());
            item.setQuantity(Integer.parseInt(txtQuantity.getText()));
            item.setStock(Integer.parseInt(txtStock.getText()));
            item.setManufactureDate(new SimpleDateFormat("yyyy-MM-dd").parse(txtMDate.getText()));
            item.setExpiredDate(new SimpleDateFormat("yyyy-MM-dd").parse(txtEDate.getText()));
            item.setSalePrice(Double.parseDouble(txtPrice.getText()));
            item.setDiscount(Double.parseDouble(txtDiscount.getText()));
            item.setSupplierID(Integer.parseInt(txtSupplier.getText()));
            item.setBoughtDate(new SimpleDateFormat("yyyy-MM-dd").parse(txtBDate.getText()));

            if (service.updateInventoryItem(item)) {
                JOptionPane.showMessageDialog(panel1, "✅ Item updated successfully!");
                loadItems();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(panel1, "❌ Failed to update item.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel1, "⚠ Error: " + ex.getMessage());
        }
    }

    private void deleteItem() {
        try {
            int selectedRow = table1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel1, "⚠ Select a row to delete.");
                return;
            }

            int id = (int) table1.getValueAt(selectedRow, 0);

            if (service.deleteInventoryItem(id)) {
                JOptionPane.showMessageDialog(panel1, "🗑 Item deleted successfully!");
                loadItems();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(panel1, "❌ Failed to delete item.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel1, "⚠ Error: " + ex.getMessage());
        }
    }

    private void clearForm() {
        txtName.setText("");
        txtQuantity.setText("");
        txtStock.setText("");
        txtMDate.setText("");
        txtEDate.setText("");
        txtPrice.setText("");
        txtDiscount.setText("");
        txtSupplier.setText("");
        txtBDate.setText("");
        table1.clearSelection();
    }

    private void fillFormFromTable() {
        int row = table1.getSelectedRow();
        if (row != -1) {
            txtName.setText(table1.getValueAt(row, 1).toString());
            txtQuantity.setText(table1.getValueAt(row, 2).toString());
            txtStock.setText(table1.getValueAt(row, 3).toString());
            txtMDate.setText(table1.getValueAt(row, 4).toString());
            txtEDate.setText(table1.getValueAt(row, 5).toString());
            txtPrice.setText(table1.getValueAt(row, 6).toString());
            txtDiscount.setText(table1.getValueAt(row, 7).toString());
            txtSupplier.setText(table1.getValueAt(row, 8).toString());
            txtBDate.setText(table1.getValueAt(row, 9).toString());
        }
    }

    public JPanel getPanel() {
        return panel1;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pharmacy Inventory Management");
        frame.setContentPane(new PharmacyInventoryView().getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 650);
        frame.setLocationRelativeTo(null); // Center screen
        frame.setVisible(true);
    }
}
