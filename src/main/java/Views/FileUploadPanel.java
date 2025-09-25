package Views;

import Controllers.ServiceController;
import Models.PatientReport;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FileUploadPanel {
    private JPanel filePanel;
    private JLabel lblpatientID;
    private JPanel ContentPane;
    File selectedFile;
    ServiceController serviceController;
    //getter
    public JPanel getContentPane() {
        return ContentPane;
    }

    FileUploadPanel(PatientReport patientReport, ServiceController s){
        //constructing object
        serviceController =s;

        SetFileChooserPanel(patientReport.getPatientID());


        //setting lbl for patient id
        lblpatientID.setText(patientReport.getName());

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
                        JOptionPane.showMessageDialog(null, "File uploaded!");
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
        filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.X_AXIS));
        filePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // padding
        filePanel.add(uploadButton);
        filePanel.add(Box.createHorizontalStrut(15));
        filePanel.add(fileLabel);
    }

    public static void main(String[] args) {
//        JFrame frame = new JFrame("CDC");
//        frame.setContentPane(new FileUploadPanel().ContentPane);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//      //  frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        //frame.setUndecorated(true);
//        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        filePanel = new CustomComponents.RoundedPanel(20);
    }
}
