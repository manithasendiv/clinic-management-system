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
    File selectedFile;
    ServiceController serviceController;
    public AddServiceUI(int patientID){
        lblPatientName.setText("PatientID: "+patientID);
        serviceController = new ServiceController();
        mainPanelAddServiceUI.setBorder(new EmptyBorder(20, 20, 20, 20));
        SetFileChooserPanel(patientID);


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
                }else{
                    JOptionPane.showMessageDialog(mainPanelAddServiceUI,"UNDEFINED","Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }


    public void SetFileChooserPanel(int patientID) {
        // Upload button with FlatLaf style
        JButton uploadButton = new JButton("📂 Choose File");
        uploadButton.putClientProperty("JButton.buttonType", "roundRect");  // FlatLaf style
        uploadButton.putClientProperty("JButton.focusedBackground", UIManager.getColor("Component.accentColor"));

        JLabel fileLabel = new JLabel("No file selected");
        fileLabel.putClientProperty("FlatLaf.style", "font: 12 bold;"); // FlatLaf font style

        // Add action listener
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select a file to upload");

            // Only allow PDF
            FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter("PDF Documents", "pdf");
            fileChooser.setFileFilter(pdfFilter);

            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                fileLabel.setText(selectedFile.getName());
                try {
                    File destDir = new File("C:\\Users\\isum\\OneDrive\\Desktop\\Y02 Sem02\\PPA\\ServiceManagementSystem\\clinic-management-system\\src\\main\\java\\Assets\\Documents\\");
                    if (!destDir.exists()) destDir.mkdirs();


                    String uniqueName = System.currentTimeMillis() + "_" + selectedFile.getName();
                    File destFile = new File(destDir, uniqueName);


                    Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);


                    boolean success = serviceController.service.AddFile(
                            selectedFile.getName(),
                            destFile.getAbsolutePath(),
                            patientID
                    );

                    if (success) {
                        JOptionPane.showMessageDialog(null, "File uploaded & record saved!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Database insert failed!");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error copying file: " + ex.getMessage());
                }

            } else {
                fileLabel.setText("No file selected");
            }
        });

        // Layout with padding
        FileUploadPanel.setLayout(new BoxLayout(FileUploadPanel, BoxLayout.X_AXIS));
        FileUploadPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // padding
        FileUploadPanel.add(uploadButton);
        FileUploadPanel.add(Box.createHorizontalStrut(15));
        FileUploadPanel.add(fileLabel);
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
}
