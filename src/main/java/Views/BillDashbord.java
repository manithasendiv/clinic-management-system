package Views;

import Controllers.BillController;
import Controllers.ServiceController;
import Models.Bill;
import Models.Patient;
import Models.PharmacyItem;
import org.apache.poi.xwpf.usermodel.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
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
    Patient patient;
    private static int counter = 1;

    List<PharmacyItem> itemList;

    public JPanel getContentPane() {
        return mainPane;
    }

    public BillDashbord() {
        this.billController = new BillController();
        serviceController = new ServiceController();
        itemList = new ArrayList<>();

        ImageIcon logo = new ImageIcon("src/main/java/Assets/logo.png");
        Image logoImage = logo.getImage();
        Image newLogo = logoImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        logo = new ImageIcon(newLogo);
        logoicon.setIcon(logo);

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
                    patient = serviceController.service.getPatientDetails(selectedRow);
                    setForm(patient);
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
                lbldiscount.setText(" Rs. " + txtdiscount.getText())
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
        List<Patient> patientList = serviceController.service.getPatients();
        DefaultTableModel model = (DefaultTableModel) PatientList.getModel();
        model.setRowCount(0);

        for (Patient patient : patientList) {
            model.addRow(new Object[]{
                    patient.getPatientID(),
                    patient.getName(),
                    patient.getAge(),
                    patient.getPhoneNumber()
            });
        }
    }

    private void setForm(Patient patient) {
        lblpatientID.setText("PatientID: " + patient.getPatientID());
        lblpatientname.setText(patient.getName());
        lblinvoiceID.setText(generateInvoiceId());
        lblcurrentdate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        lblname.setText(patient.getName());
    }

    private String generateInvoiceId() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        return "INV" + timestamp;
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
        if (patient == null) {
            JOptionPane.showMessageDialog(null, "Please select a patient first.");
            return;
        }

        if (itemList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please add at least one item to the bill.");
            return;
        }

        try {
            String service = txtservice.getText();
            if (service.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a service description.");
                return;
            }

            double discount = txtdiscount.getText().isEmpty() ? 0.0 : Double.parseDouble(txtdiscount.getText());
            double total = itemList.stream()
                    .mapToDouble(p -> p.getPrice() * p.getQuantity())
                    .sum();
            total -= discount;

            lbltotalprice.setText(" Rs. " + String.format("%.2f", total));
            String invoiceId = lblinvoiceID.getText();

            // Generate Word document first to get file path
            String filePath = generateInvoiceDocument(invoiceId, patient, itemList, service, discount, total);

            if (filePath != null) {
                // Create bill with file path
                Bill bill = new Bill(discount, service, patient.getPatientID(), invoiceId, total, filePath);
                bill.setPharmacyItems(new ArrayList<>(itemList));

                boolean success = billController.objBillService.addBill(bill);

                if (success) {
                    for (PharmacyItem p : itemList) {
                        billController.objBillService.addPharmacyItem(p, invoiceId);
                    }

                    JOptionPane.showMessageDialog(null,
                            "Bill generated successfully!\nInvoice ID: " + invoiceId);

                    // Clear form for next bill
                    clearBillForm();

                } else {
                    JOptionPane.showMessageDialog(null, "Failed to save bill to database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Failed to generate invoice document.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid discount input.", "Input Error", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateInvoiceDocument(String invoiceId, Patient patient, List<PharmacyItem> items,
                                           String service, double discount, double total) {
        try (FileInputStream fis = new FileInputStream("src/main/java/Assets/Invoice_template.docx");
             XWPFDocument document = new XWPFDocument(fis)) {

            // Calculate subtotal
            double subtotal = items.stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();

            // 1️⃣ Replace basic placeholders
            Map<String, String> replacements = new HashMap<>();
            replacements.put("{{InvoiceID}}", invoiceId);
            replacements.put("{{CurrentDate}}", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            replacements.put("{{PatientID}}", String.valueOf(patient.getPatientID()));
            replacements.put("{{PatientName}}", patient.getName());
            replacements.put("{{Service}}", service);
            replacements.put("{{Subtotal}}", String.format("Rs. %.2f", subtotal));
            replacements.put("{{Discount}}", String.format("Rs. %.2f", discount));
            replacements.put("{{Total}}", String.format("Rs. %.2f", total));

            // Replace in paragraphs
            for (XWPFParagraph p : document.getParagraphs()) {
                String text = p.getText();
                if (text != null) {
                    String replaced = text;
                    for (Map.Entry<String, String> entry : replacements.entrySet()) {
                        replaced = replaced.replace(entry.getKey(), entry.getValue());
                    }

                    if (!replaced.equals(text)) {
                        // Clear old runs
                        for (int i = p.getRuns().size() - 1; i >= 0; i--) {
                            p.removeRun(i);
                        }
                        // Insert updated text
                        p.createRun().setText(replaced, 0);
                    }
                }
            }


            // 2️⃣ Fill bill items table
            for (XWPFTable table : document.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        if (cell.getText().contains("{{item_name}}")) {
                            XWPFTableRow templateRow = row;

                            // Clear placeholder row content
                            for (XWPFTableCell c : templateRow.getTableCells()) {
                                for (XWPFParagraph p : c.getParagraphs()) {
                                    for (XWPFRun r : p.getRuns()) {
                                        r.setText("", 0);
                                    }
                                }
                            }

                            // Add rows for each pharmacy item
                            for (PharmacyItem item : items) {
                                XWPFTableRow newRow = table.createRow();

                                // Item name
                                setCellText(newRow.getCell(0), item.getName());

                                // Price
                                setCellText(newRow.getCell(1), String.format("Rs. %.2f", item.getPrice()));

                                // Quantity
                                setCellText(newRow.getCell(2), String.valueOf(item.getQuantity()));

                                // Subtotal
                                double itemSubtotal = item.getPrice() * item.getQuantity();
                                setCellText(newRow.getCell(3), String.format("Rs. %.2f", itemSubtotal));
                            }

                            // Remove the template row
                            int rowIndex = table.getRows().indexOf(templateRow);
                            if (rowIndex >= 0) {
                                table.removeRow(rowIndex);
                            }
                            break;
                        }
                    }
                }
            }

            // 3️⃣ Save the invoice
            File invoiceDir = new File("src/main/java/Assets/Invoices");
            if (!invoiceDir.exists()) {
                invoiceDir.mkdirs();
            }

            String fileName = "Invoice_" + invoiceId + ".docx";
            File invoiceFile = new File(invoiceDir, fileName);
            String filePath = invoiceFile.getAbsolutePath();

            try (FileOutputStream fos = new FileOutputStream(invoiceFile)) {
                document.write(fos);
            }

            System.out.println("Invoice generated successfully: " + filePath);

            // Optional: Open the file automatically
//            if (Desktop.isDesktopSupported()) {
//                Desktop.getDesktop().open(invoiceFile);
//            }

            return filePath;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Failed to generate invoice document: " + e.getMessage(),
                    "Document Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // Helper method to set cell text
    private void setCellText(XWPFTableCell cell, String text) {
        if (cell != null) {
            // Clear existing content
            for (XWPFParagraph p : cell.getParagraphs()) {
                for (XWPFRun r : p.getRuns()) {
                    r.setText("", 0);
                }
            }
            // Set new text
            cell.setText(text);
        }
    }

    private void clearBillForm() {
        // Clear bill items
        DefaultTableModel model = (DefaultTableModel) Billtable.getModel();
        model.setRowCount(0);
        itemList.clear();

        // Clear input fields
        txtservice.setText("");
        txtdiscount.setText("");
        lbltotalprice.setText(" Rs. 0.00");
        lbldiscount.setText(" Rs. 0");

        // Generate new invoice ID
        lblinvoiceID.setText(generateInvoiceId());
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
        JFrame frame = new JFrame("Bill Dashboard");
        frame.setContentPane(new BillDashbord().mainPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}