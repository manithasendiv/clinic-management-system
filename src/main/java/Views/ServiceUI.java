package Views;

import Controllers.ServiceController;
import Models.Patient;
import ServiceLayer.ServicesService;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUI {

    private JPanel mainPanel;
    private JPanel fixedTop;
    private JLabel Count;
    private JTextField searchTXT;
    private JButton Search;
    private JButton Filter;
    private JTable PatientsList;
    private JScrollPane scrollPane;
    ServiceController serviceController;
    Patient patient;
    private int selectedRow =-1;


    public ServiceUI() {
        serviceController = new ServiceController();
        loadData();

        PatientsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                CustomComponents.CustomTableServiceUI table;
                int row =  PatientsList.getSelectedRow();
                if(row >=0){
                    selectedRow =  Integer.parseInt(PatientsList.getValueAt(row, 0).toString());
                    patient=serviceController.service.getPatientDetails(selectedRow);
                    System.out.println(patient);
                    JFrame frame = new JFrame("Service Profile");
                    frame.setContentPane(new ServiceProfileUI(patient).BackPanel);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                }
            }
        });
        searchTXT.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                performSearch();
            }
        });

        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
    }
    private void performSearch() {
        try {
            String searchtext = searchTXT.getText().trim();
            if (searchtext.isEmpty()) {
                loadData();
                return;
            }

            List<Patient> patientList = serviceController.service.getPatients();
            List<Patient> filteredList = new ArrayList<>();

            for (Patient patient : patientList) {
                if (String.valueOf(patient.getPatientID()).contains(searchtext) ||
                        patient.getName().toLowerCase().contains(searchtext.toLowerCase()) ||
                        String.valueOf(patient.getAge()).contains(searchtext) ||
                        patient.getPhoneNumber().contains(searchtext)) {
                    filteredList.add(patient);
                }
            }

            updateTable(filteredList);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadData() {
        List<Patient> patientList = serviceController.service.getPatients();
        updateTable(patientList);
    }

    public void updateTable(List<Patient> patientList) {
        String[] headers = {"PatientID", "Name", "Age", "PhoneNumber"};
        PatientsList = new CustomComponents.CustomTableServiceUI(new Object[][]{}, headers);
        DefaultTableModel model = (DefaultTableModel) PatientsList.getModel();
        model.setRowCount(0);

        for (Patient patient : patientList) {
            model.addRow(new Object[]{
                    patient.getPatientID(),
                    patient.getName(),
                    patient.getAge(),
                    patient.getPhoneNumber()
            });
        }
        scrollPane.setViewportView(PatientsList);
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
        UIManager.put("TableHeader.font", new Font("Segoe UI", Font.ITALIC, 12));
        UIManager.put("TableHeader.foreground", new Color(0, 0, 0, 255));
        UIManager.put("TableHeader.background", new Color(217, 217, 217, 217));
        UIManager.put( "Component.focusWidth", 3 );
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
}
