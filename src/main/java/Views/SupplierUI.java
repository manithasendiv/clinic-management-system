package Views;

import Controllers.SupplierController;
import Models.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SupplierUI {
    private JPanel BackPanelSupplier;
    private JLabel topicLBL;
    private JTable SupplierList;
    private JTextField txtID;
    private JTextField txtName;
    private JTextField txtContact;
    private JTextField txtProduct;
    private JTextField txtCredit;
    private JTextField txtBank;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JScrollPane scrollPane;
    ArrayList<Supplier> supplierArrayList;
    Supplier supplier;
    SupplierController supplierController;

    public JPanel getBackPanelSupplier() {
        return BackPanelSupplier;
    }

    public SupplierUI() {
        supplierController = new SupplierController();

        loadTable();

        // Add button
        addButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtID.getText().trim());
                String name = txtName.getText().trim();
                String contact = txtContact.getText().trim();
                String product = txtProduct.getText().trim();
                int credit = Integer.parseInt(txtCredit.getText().trim());
                String bank = txtBank.getText().trim();

                Supplier supplier = new Supplier(id, name, contact, product, credit, bank);

                boolean added = supplierController.ObjSupplierService.addsupplier(supplier);

                if (added) {
                    JOptionPane.showMessageDialog(null, "Supplier added successfully!");
                    loadTable();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add supplier.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        // Update button (uses selected table row)
        updateButton.addActionListener(e -> {
            try {
                int selectedRow = SupplierList.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a supplier to update.");
                    return;
                }

                int id = Integer.parseInt(SupplierList.getValueAt(selectedRow, 0).toString());
                String name = txtName.getText().trim();
                String contact = txtContact.getText().trim();
                String product = txtProduct.getText().trim();
                int credit = Integer.parseInt(txtCredit.getText().trim());
                String bank = txtBank.getText().trim();

                Supplier supplier = new Supplier(id, name, contact, product, credit, bank);

                boolean updated = supplierController.updateSupplier(supplier);

                if (updated) {
                    JOptionPane.showMessageDialog(null, "Supplier updated successfully!");
                    loadTable();
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update supplier.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        // Delete button (uses selected table row)
        deleteButton.addActionListener(e -> {
            try {
                int selectedRow = SupplierList.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a supplier to delete.");
                    return;
                }

                int id = Integer.parseInt(SupplierList.getValueAt(selectedRow, 0).toString());
                int confirm = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete supplier ID " + id + "?",
                        "Confirm Delete", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    boolean deleted = supplierController.ObjSupplierService.deleteSupplier(id);
                    if (deleted) {
                        JOptionPane.showMessageDialog(null, "Supplier deleted successfully!");
                        loadTable();
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to delete supplier.");
                    }
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        // Fill fields when a row is selected
        SupplierList.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && SupplierList.getSelectedRow() != -1) {
                int row = SupplierList.getSelectedRow();
                txtID.setText(SupplierList.getValueAt(row, 0).toString());
                txtID.setEditable(false); // Make ID read-only when row is selected
                txtName.setText(SupplierList.getValueAt(row, 1).toString());
                txtContact.setText(SupplierList.getValueAt(row, 2).toString());
                txtProduct.setText(SupplierList.getValueAt(row, 3).toString());
                txtCredit.setText(SupplierList.getValueAt(row, 4).toString());
                txtBank.setText(SupplierList.getValueAt(row, 5).toString());
            }
        });
    }


    private void clearFields() {
        txtID.setText("");
        txtName.setText("");
        txtContact.setText("");
        txtProduct.setText("");
        txtCredit.setText("");
        txtBank.setText("");
    }

    void loadTable() {
        String[] columnNames = {"Supplier ID", "Name", "Contact", "Products", "Credit Period", "Bank Details"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try {
            // Service returns a List<Supplier>
            List<Supplier> suppliers = supplierController.ObjSupplierService.getSupplier();

            for (Supplier s : suppliers) {
                Object[] row = {
                        s.getSupplierID(),
                        s.getSupplierName(),
                        s.getContact(),
                        s.getProducts(),
                        s.getCreditPeriod(),
                        s.getBankDetails()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Use custom JTable instead of normal JTable
        SupplierList = new CustomComponents.CustomJTable(model);

        // Attach table to existing scrollPane
        scrollPane.setViewportView(SupplierList);
    }


}
