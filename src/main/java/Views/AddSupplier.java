package Views;

import Controllers.SupplierController;
import Models.Supplier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSupplier {
    private JPanel Backpanel;
    private JPanel panel;
    private JTextField txtsupplierID;
    private JTextField name;
    private JTextField contact;
    private JTextField product;
    private JTextField creditpoints;
    private JTextField bankdetails;
    private JButton addSupplierButton;


    SupplierController objController;
    Supplier newSupplier;
    public AddSupplier() {
        objController = new SupplierController();

        addSupplierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int SupplierID = Integer.parseInt(txtsupplierID.getText());
                    String SupplierName = name.getText();
                    String Contact = contact.getText();
                    String Products = product.getText();
                    int CreditPeriod = Integer.parseInt(creditpoints.getText());
                    String BankDetails = bankdetails.getText();

                    if (SupplierName.isEmpty() || Contact.isEmpty() || Products.isEmpty() || BankDetails.isEmpty()) {
                        JOptionPane.showMessageDialog(panel, "Please fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        newSupplier = objController.addSupplier(SupplierID, SupplierName, Contact, Products, CreditPeriod, BankDetails);

                        if (!objController.addSupplierToDataBase()) {
                            JOptionPane.showMessageDialog(null, "Error in adding supplier");
                            return;
                        }
                        JOptionPane.showMessageDialog(null, "Supplier added successfully!");
                    }

                    // Clear fields after add
                    txtsupplierID.setText("");
                    AddSupplier.this.name.setText("");
                    contact.setText("");
                    product.setText("");
                    creditpoints.setText("");
                    bankdetails.setText("");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Invalid input for SupplierID or Credit Period", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Add Supplier");
        frame.setSize(400, 400);
        frame.setContentPane(new AddSupplier().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
