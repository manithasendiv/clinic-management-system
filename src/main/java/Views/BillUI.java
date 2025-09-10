package Views;

import Controllers.BillController;
import ServiceLayer.BillService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BillUI {
    private JPanel MainPanel;
    private JPanel AddBillForm;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton btnRemove;
    private JButton btnAdd;
    private JLabel txtPatientId;
    private JLabel txtService;
    private JLabel txtPharmacyItems;
    private JLabel txtTotal;
    private JLabel txtDiscount;

    BillController objController;

    public BillUI(){
        objController = new BillController();

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String patientId = txtPatientId.getText().trim();
                String service = txtService.getText().trim();
                String pharmacyItems = txtPharmacyItems.getText().trim();
                String total = txtTotal.getText().trim();
                String discount = txtDiscount.getText().trim();

                if (patientId.isEmpty() || service.isEmpty() || pharmacyItems.isEmpty() || total.isEmpty() || discount.isEmpty()){
                    JOptionPane.showMessageDialog(MainPanel,"Please fill all the fields","Error",JOptionPane.ERROR_MESSAGE);
                }else{
                    boolean success = objController.addBill(patientId,service,pharmacyItems,total, discount);

                    if (success){
                        JOptionPane.showMessageDialog(MainPanel, "Bill added successfully!");
                    }else {
                        JOptionPane.showMessageDialog(MainPanel,"Error in adding bill","Error",JOptionPane.ERROR_MESSAGE);
                    }


                }
            }
        });

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


    }
    private void clearFields() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
    }
    public JPanel getMainPanel() {
        return MainPanel;
    }

}
