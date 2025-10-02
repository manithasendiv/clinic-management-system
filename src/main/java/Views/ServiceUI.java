package Views;

import Controllers.ServiceController;
import Models.Patient;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;

import java.util.List;

public class ServiceUI {

    private JPanel mainPanel;
    private JPanel fixedTop;
    private JLabel Count;
    private JTextField searchTXT;
    private JTable PatientsList;
    private JScrollPane scrollPane;
    ServiceController serviceController;
    private int selectedRow =-1;
    private TableRowSorter<DefaultTableModel> sorter;


    public JPanel getMainPanel() {
        return mainPanel;
    }

    public ServiceUI() {
        this.serviceController = new ServiceController();
        //table initializer
        String[] headers = {"PatientID", "Name", "Age", "PhoneNumber"};
        PatientsList = new CustomComponents.CustomTableServiceUI(new Object[][]{}, headers);
        DefaultTableModel model = (DefaultTableModel) PatientsList.getModel();
        sorter = new TableRowSorter<>(model);
        PatientsList.setRowSorter(sorter);
        scrollPane.setViewportView(PatientsList);
        //
        loadData();
        searchTXT.putClientProperty("JTextField.placeholderText", "Search here");


        PatientsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CustomComponents.CustomTableServiceUI table;
                int row =  PatientsList.getSelectedRow();
                if(row >=0){
                    selectedRow =  Integer.parseInt(PatientsList.getValueAt(row, 0).toString());
                    mainPanel.removeAll();
                    mainPanel.setLayout(new BorderLayout());
                    ServiceProfile s = new ServiceProfile(selectedRow,serviceController);

                    mainPanel.add(s.getMainPanel(), BorderLayout.CENTER);
                    mainPanel.revalidate();
                    mainPanel.repaint();
                }
            }
        });
        searchTXT.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                performSearch();
            }
        });

       /* Search.addActionListener(new ActionListener() {  //search buttom perform
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });*/
    }

    private void performSearch() {
        String searchtext = searchTXT.getText().trim();
        if (searchtext.isEmpty()) {
            sorter.setRowFilter(null); // Show all rows
        } else {
            // Case-insensitive search across all columns
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchtext));
        }
    }


    public void loadData() {
        List<Patient> patientList = serviceController.service.getPatients();
        DefaultTableModel model = (DefaultTableModel) PatientsList.getModel();
        model.setRowCount(0); // clear old data

        for (Patient patient : patientList) {
            model.addRow(new Object[]{
                    patient.getPatientID(),
                    patient.getName(),
                    patient.getAge(),
                    patient.getPhoneNumber()
            });
        }
    }

/*
    public void loadData(){
        String []headers = {"PatientID","Name","Age","PhoneNumber"};
        List<Patient> patientList = serviceController.service.getPatients();
        PatientsList = new CustomComponents.CustomTableServiceUI(new Object[][]{},headers);
        DefaultTableModel model = (DefaultTableModel) PatientsList.getModel();
        model.setRowCount(0);
        for(Patient patient: patientList){
            model.addRow(new Object[]{
                    patient.getPatientID(),
                    patient.getName(),
                    patient.getAge(),
                    patient.getPhoneNumber()
            });
        }
        scrollPane.setViewportView(PatientsList);
    }*/


    public static void main(String[] args) {
        UIManager.put("ScrollBar.trackInsets", new Insets(2, 4, 2, 4));
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("ScrollBar.track", new Color(0xe0e0e0));
        /*UIManager.put("TableHeader.font", new Font("Segoe UI", Font.ITALIC, 12));
        UIManager.put("TableHeader.foreground", new Color(0, 0, 0, 255));
        UIManager.put("TableHeader.background", new Color(217, 217, 217, 217));*/
        UIManager.put( "Component.focusWidth", 1 );
        //flatlaf dependency execution code
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }


        JFrame frame = new JFrame("Service UI");
        frame.setContentPane(new ServiceUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);


    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        mainPanel = new CustomComponents.RoundedPanel(20);
    }
}
