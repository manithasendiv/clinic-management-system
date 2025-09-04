package Views;

import Controllers.SupplierController;
import Models.supplier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class updateSupplier {
    private JPanel BackPanel;
    private JTextField txtSupplierID;
    private JTextField txtSupplierName;
    private JTextField txtContact;
    private JTextField txtProducts;
    private JTextField txtCreditPeriod;
    private JTextField txtBankDetails;
    private JButton btnUpdate;

    SupplierController objController;

    public updateSupplier() {
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
                        supplier updatedSupplier = objController.addSupplier(SupplierID, SupplierName, Contact, Products, CreditPeriod, BankDetails);

                        if (!objController.updateSupplier(updatedSupplier)) {
                            JOptionPane.showMessageDialog(null, "Error in updating supplier");
                            return;
                        }
                        JOptionPane.showMessageDialog(null, "Supplier updated successfully!");
                    }

                    // Clear fields after update
                    txtSupplierID.setText("");
                    txtSupplierName.setText("");
                    txtContact.setText("");
                    txtProducts.setText("");
                    txtCreditPeriod.setText("");
                    txtBankDetails.setText("");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BackPanel, "Invalid input for SupplierID or Credit Period", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Update Supplier");
        frame.setSize(400, 400);
        frame.setContentPane(new updateSupplier().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
