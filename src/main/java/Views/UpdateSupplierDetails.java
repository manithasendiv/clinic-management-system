package Views;

import Controllers.SupplierController;
import Models.Supplier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateSupplierDetails {
    private JPanel BackPanel;
    private JLabel lblSupplierID;
    private JLabel lblSupplierName;
    private JLabel lblContact;
    private JLabel lblProducts;
    private JLabel lblCreditPeriod;
    private JLabel lblBankDetails;
    private JButton btnUpdate;
    private JTextField txtName;
    private JTextField txtContact;
    private JTextField txtProduct;
    private JTextField txtcreditperiod;
    private JTextField txtbankdetails;
    private JTextField txtSupplierID;

    SupplierController objController;

    public UpdateSupplierDetails() {
        objController = new SupplierController();

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int SupplierID = Integer.parseInt(txtSupplierID.getText());
                    String SupplierName = txtName.getText();
                    String Contact = txtContact.getText();
                    String Products = txtProduct.getText();
                    int CreditPeriod = Integer.parseInt(txtcreditperiod.getText());
                    String BankDetails = txtbankdetails.getText();

                    if (SupplierName.isEmpty() || Contact.isEmpty() || Products.isEmpty() || BankDetails.isEmpty()) {
                        JOptionPane.showMessageDialog(BackPanel, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        Supplier updatedSupplier = objController.addSupplier(SupplierID, SupplierName, Contact, Products, CreditPeriod, BankDetails);

                        if (!objController.updateSupplier(updatedSupplier)) {
                            JOptionPane.showMessageDialog(null, "Error in updating supplier");
                            return;
                        }
                        JOptionPane.showMessageDialog(null, "Supplier updated successfully!");
                    }

                    // Clear fields after update
                    txtSupplierID.setText("");
                    txtName.setText("");
                    txtbankdetails.setText("");
                    txtContact.setText("");
                    txtcreditperiod.setText("");
                    txtProduct.setText("");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(BackPanel, "Invalid input for SupplierID or Credit Period", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public JPanel getBackPanel(){
        return BackPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Update Supplier");
        frame.setSize(400, 400);
        frame.setContentPane(new UpdateSupplierDetails().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
