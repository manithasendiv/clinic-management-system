package Views;

import Controllers.SupplierController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class UpdateSupplierGUI {
    private JPanel BackPanel;
    private JTextField txtSupplierID;
    private JTextField txtSupplierName;
    private JTextField txtContact;
    private JTextField txtProducts;
    private JTextField txtCreditPeriod;
    private JTextField txtBankDetails;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;

    SupplierController objController;

    public UpdateSupplierGUI() {
        objController = new SupplierController();

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int SupplierID = Integer.parseInt(txtSupplierID.getText());
                    String SupplierName = txtSupplierName.getText();
                    String Contact = txtContact.getText();
                    String Products = txtProducts.getText();
                    int CreditPeriod = Integer.parseInt(txtCreditPeriod.getText());
                    String BankDetails = txtBankDetails.getText();

                    if (SupplierName.isEmpty() || Contact.isEmpty() || Products.isEmpty() || BankDetails.isEmpty()) {
                        JOptionPane.showMessageDialog(BackPanel, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (!objController.updateSupplier(
                                objController.addSupplier(SupplierID, SupplierName, Contact, Products, CreditPeriod, BankDetails))) {
                            JOptionPane.showMessageDialog(null, "Error in updating supplier");
                            return;
                        }
                        JOptionPane.showMessageDialog(null, "Supplier updated successfully!");
                    }

                    txtSupplierID.setText("");
                    txtSupplierName.setText("");
                    txtContact.setText("");
                    txtProducts.setText("");
                    txtCreditPeriod.setText("");
                    txtBankDetails.setText("");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BackPanel, "Invalid input for ID or Credit Period", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return BackPanel;
    }
}
