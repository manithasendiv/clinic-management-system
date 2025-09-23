package Views;

import Controllers.ServiceController;
import Models.Service;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AddServiceUI {
    private JPanel addServiceForm;
    private JTextField txtServiceName;
    private JTextField txtDoctorName;
    private JPanel FileUploadPanel;
    private JButton saveDetailsButton;
    private JButton resetButton;
    JPanel mainPanelAddServiceUI;
    private JLabel lblPatientName;
    private JPanel top;

    ServiceController serviceController;


    public AddServiceUI(int patientID){
        lblPatientName.setText("PatientID: "+patientID);
        serviceController = new ServiceController();

        saveDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String doctor= txtDoctorName.getText();
                String serviceNameText= txtServiceName.getText();

                if(doctor.isEmpty() && serviceNameText.isEmpty()){
                    JOptionPane.showMessageDialog(mainPanelAddServiceUI, "Enter Details", "Caution", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                serviceController.AddService(doctor,serviceNameText,patientID);
                if(serviceController.addServiceToDatabase()){
                    JOptionPane.showMessageDialog(mainPanelAddServiceUI, "Service added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    txtServiceName.setText(null);
                    txtDoctorName.setText(null);
                }else{
                    JOptionPane.showMessageDialog(mainPanelAddServiceUI,"UNDEFINED","Error", JOptionPane.ERROR_MESSAGE);
                    txtServiceName.setText(null);
                    txtDoctorName.setText(null);
                }

            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtServiceName.setText(null);
                txtDoctorName.setText(null);
            }
        });
    }



    public static void main(String[] args) {

        UIManager.put( "Component.focusWidth", 1 );
        //flatlaf dependency execution code
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        addServiceForm = new CustomComponents.RoundedPanel(20);
        top = new CustomComponents.RoundedPanel(20);
    }
}
