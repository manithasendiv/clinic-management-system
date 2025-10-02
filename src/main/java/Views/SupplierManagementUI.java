package Views;

import Controllers.SupplierController;
import Models.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class SupplierManagementUI {
    private JPanel MainPanel;
    private JPanel MidPanel;
    private JPanel AddSupplierForm;
    private JPanel SupplierPanel;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JPanel btnPanel;
    private JTextField txtID;
    private JTextField txtname;
    private JTextField txtcontact;
    private JTextField txtproduct;
    private JTextField txtcredit;
    private JTextField txtbank;
    private JButton clearButton;
    private JTable supplierList;
    private JScrollPane scrollPane;
    ArrayList<Supplier> supplierArrayList;
    Supplier supplier;
    SupplierController supplierController;

    public JPanel getMainPanel() {
        return MainPanel;
    }

    SupplierManagementUI(){
        supplierController = new SupplierController();
        //dynamic table loads
        loadTable();




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
        supplierList = new CustomComponents.CustomTableServiceUI(model);

        // Attach table to existing scrollPane
        scrollPane.setViewportView(supplierList);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        AddSupplierForm = new CustomComponents.RoundedPanel(20);
        SupplierPanel =new CustomComponents.RoundedPanel(20);
        btnPanel = new CustomComponents.RoundedPanel(20);
    }
}
