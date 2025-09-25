package Views;

import Controllers.BillController;
import Controllers.ServiceController;
import Models.Bill;
import Models.PatientReport;
import Models.PharmacyItem;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillDashbord {
    private JPanel mainPane;
    private JPanel TopPanel;
    private JPanel midPanel;
    private JPanel totalpatientcount;
    private JPanel totalEarning;
    private JTextField txtSearch;
    private JTable PatientList;
    private JPanel BottomPanel;
    private JLabel lblpatientID;
    private JTextField txtservice;
    private JPanel previewPanel;
    private JPanel bottomPreviewPanel;
    private JPanel formpanel;
    private JTextField txtItem;
    private JTextField txtPrice;
    private JSpinner spinnerQty;
    private JButton btnadditem;
    private JTextField txtdiscount;
    private JButton generateBillButton;
    private JLabel logoicon;
    private JTable Billtable;
    private JLabel lbldiscount;
    private JLabel lbltotalprice;
    private JPanel summerypanel;
    private JLabel lblinvoiceID;
    private JLabel lblname;
    private JLabel lblcurrentdate;
    private JScrollPane scrollpane;
    private JLabel lblpatientname;
    private JLabel lblservice;
    private JScrollPane scrollpane2;
    private JLabel lbltotalearning;
    private JLabel lbltotalpatient;

    private int selectedRow = -1;
    private TableRowSorter<DefaultTableModel> sorter;
    BillController billController;
    ServiceController serviceController;
    PatientReport patientReport;
    private static int counter = 1;

    List<PharmacyItem> itemList;

    public JPanel getContentPane() {
        return mainPane;
    }

    public BillDashbord() {

        this.billController = new BillController();
        serviceController = new ServiceController();
        itemList = new ArrayList<>();

        Billtable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Item", "Price", "Qty", "Subtotal"}
        ));


        String[] headers = {"PatientID", "Name", "Age", "PhoneNumber"};
        PatientList.setModel(new DefaultTableModel(new Object[][]{}, headers));
        DefaultTableModel model = (DefaultTableModel) PatientList.getModel();
        sorter = new TableRowSorter<>(model);
        PatientList.setRowSorter(sorter);

        // Load patients
        loadData();

        txtSearch.putClientProperty("JTextField.placeholderText", "Search Here");

        // Patient selection
        PatientList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = PatientList.getSelectedRow();
                if (row >= 0) {
                    selectedRow = Integer.parseInt(PatientList.getValueAt(row, 0).toString());
                    patientReport = serviceController.service.getPatientDetails(selectedRow);
                    setForm(patientReport);
                }
            }
        });

        // Search filter
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                performSearch();
            }
        });

        // Add item to bill
        btnadditem.addActionListener(e -> addItemToBill());

        // Generate bill
        generateBillButton.addActionListener(e -> generateBill());

        // Live preview updates
        txtservice.getDocument().addDocumentListener(new SimpleDocListener(() ->
                lblservice.setText(txtservice.getText())
        ));

        txtdiscount.getDocument().addDocumentListener(new SimpleDocListener(() ->
                lbldiscount.setText("Discount: Rs. " + txtdiscount.getText())
        ));
    }

    private void performSearch() {
        String searchtext = txtSearch.getText().trim();
        if (searchtext.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchtext));
        }
    }

    public void loadData() {
        List<PatientReport> patientReportList = serviceController.service.getPatients();
        DefaultTableModel model = (DefaultTableModel) PatientList.getModel();
        model.setRowCount(0);

        for (PatientReport patientReport : patientReportList) {
            model.addRow(new Object[]{
                    patientReport.getPatientID(),
                    patientReport.getName(),
                    patientReport.getAge(),
                    patientReport.getPhoneNumber()
            });
        }
    }

    private void setForm(PatientReport patientReport) {
        lblpatientID.setText("PatientID: " + patientReport.getPatientID());
        lblpatientname.setText(patientReport.getName());
        lblinvoiceID.setText(generateInvoiceId());
        lblcurrentdate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        lblname.setText(patientReport.getName());
    }

    public static String generateInvoiceId() {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String countStr = String.format("%03d", counter);
        counter++;
        return "INV" + date + "-" + countStr;
    }

    private void addItemToBill() {
        if (txtItem.getText().isEmpty() || txtPrice.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter valid item details.");
            return;
        }
        try {
            String item = txtItem.getText();
            double price = Double.parseDouble(txtPrice.getText());
            int qty = (int) spinnerQty.getValue();

            if (qty <= 0) {
                JOptionPane.showMessageDialog(null, "Quantity must be at least 1.");
                return;
            }

            double subtotal = price * qty;

            DefaultTableModel model = (DefaultTableModel) Billtable.getModel();
            model.addRow(new Object[]{item, price, qty, subtotal});

            itemList.add(new PharmacyItem(item, price, qty));

            // Clear fields
            txtItem.setText("");
            txtPrice.setText("");
            spinnerQty.setValue(1);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid price input.");
        }
    }

    private void generateBill() {
        if (patientReport == null) {
            JOptionPane.showMessageDialog(null, "Please select a patient first.");
            return;
        }
        try {
            String service = txtservice.getText();
            double discount = txtdiscount.getText().isEmpty() ? 0.0 : Double.parseDouble(txtdiscount.getText());

            double total = itemList.stream()
                    .mapToDouble(p -> p.getPrice() * p.getQuantity())
                    .sum();

            total -= discount;

            lbltotalprice.setText("Total: Rs. " + String.format("%.2f", total));

            String invoiceId = lblinvoiceID.getText();

            Bill bill = new Bill(discount, service, patientReport.getPatientID(), invoiceId, total);
            boolean success = billController.objBillService.addBill(bill);

            if (success) {
                for (PharmacyItem p : itemList) {
                    billController.objBillService.addPharmacyItem(p, invoiceId);
                }
                JOptionPane.showMessageDialog(null, "Bill generated successfully!\nInvoice ID: " + invoiceId);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to generate bill.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid discount input.", "Input Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createUIComponents() {
        formpanel = new CustomComponents.RoundedPanel(20);
        bottomPreviewPanel = new CustomComponents.RoundedPanel(20);
        midPanel = new CustomComponents.RoundedPanel(20);
    }

    // Helper: simple DocumentListener wrapper
    static class SimpleDocListener implements DocumentListener {
        private final Runnable onChange;
        public SimpleDocListener(Runnable onChange) {
            this.onChange = onChange;
        }
        public void insertUpdate(DocumentEvent e) { onChange.run(); }
        public void removeUpdate(DocumentEvent e) { onChange.run(); }
        public void changedUpdate(DocumentEvent e) { onChange.run(); }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Service UI");
        frame.setContentPane(new BillDashbord().mainPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);

    }
}
