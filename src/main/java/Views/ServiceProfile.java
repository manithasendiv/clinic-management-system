package Views;

import Controllers.ServiceController;
import Models.PatientReport;
import Models.Service;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ServiceProfile {
    private JPanel contentPane; //Main Panel
    private JPanel Top;
    private JPanel Mid;
    private JPanel Bottom;
    private JPanel ProfilePanel;
    private JPanel DetailPanel;
    private JLabel imageIcon;
    private JLabel lblName;
    private JLabel lblGender;
    private JLabel lblPhoneNo;
    private JLabel lblRegDate;
    private JLabel lblIllness;
    private JLabel lblBloodType;
    private JLabel lblAllergies;
    private JPanel DocumentPanel;
    private JButton btnAddFile;
    private JList DocList;
    private JScrollPane DocSPane;
    private JPanel NotePanel;
    private JTextArea txtNote;
    private JButton btnsaveNote;
    private JButton btnaddSer;
    private JScrollPane SerSPane;
    private JButton btnBack;
    private JTable SerList;
    private JButton ShowNotes;
    private JScrollPane txtareawrapspane;
    private JButton btnnew;
    private JButton btndeleteSer;
    private JButton btneditadd;
    private int selectedRow =-1;
    private JLabel btnLblBack;

    // Return Main panel
    public JPanel getMainPanel() {
        return contentPane;
    }

    //object initialization
    ServiceController serviceController;

    //serviceList initialization
    ArrayList<Service> serviceList;
    //documentList initialization
    ArrayList<Service> documentList;

    //Constructor for Service UI
    ServiceProfile(PatientReport patientReport, ServiceController s){
        //Constructing object
        this.serviceController =s;

        //Method Calls For Setting Panels
        SetTopPanel(patientReport);
        SetDocumentPanel(patientReport);
        SetServicePanel(patientReport);
        SetNotePanel();

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //back button code here
                JPanel parent = (JPanel) contentPane.getParent(); // get the container that holds ServiceProfile
                parent.removeAll();
                parent.add(new ServiceUI().getMainPanel(), BorderLayout.CENTER);
                parent.revalidate();
                parent.repaint();
            }
        });
        btnAddFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //File upload Button Work Here
                JFrame frame = new JFrame("UploadFile");
                frame.setContentPane(new FileUploadPanel(patientReport,serviceController).getContentPane());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        refreshDocumentList(patientReport); // refresh the list
                    }
                });
            }
        });
        btnaddSer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("AddService");
                frame.setContentPane(new AddServiceUI(patientReport.getPatientID(),serviceController).mainPanelAddServiceUI);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        loadServiceData(patientReport); // refresh the list
                    }
                });
            }
        });
        ShowNotes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Notes");
                frame.setContentPane(new NotesPanel(patientReport.getPatientID(),serviceController).getContentPane());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        SerList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row =  SerList.getSelectedRow();
                if(row >=0){
                    selectedRow =  Integer.parseInt(SerList.getValueAt(row, 0).toString());
                    String note = serviceController.service.getSingleServiceNote(selectedRow);
                    txtNote.setText(note);
                }
            }
        });
        btnsaveNote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String note = txtNote.getText();
                    if(selectedRow>=0){
                        if(note.isEmpty()){
                            JOptionPane.showMessageDialog(contentPane,"Please Write Note","Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }else {
                            serviceController.addNote(selectedRow,note);
                            if(serviceController.addNoteToDatabase()){
                                JOptionPane.showMessageDialog(contentPane, "Note added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                txtNote.setText(null);
                            }else{
                                JOptionPane.showMessageDialog(contentPane,"UNDEFINED","Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(contentPane, "Must Select Service To Proceed", "Caution", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                } catch (RuntimeException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnnew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtNote.setText(null);
            }
        });
        btndeleteSer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = SerList.getSelectedRow(); // get selected row
                if (selectedIndex != -1) { // make sure something is selected
                    // Get the service ID from column 0 of the selected row
                    int serviceID = (int) SerList.getValueAt(selectedIndex, 0);

                    // Call controller to remove it from DB
                    serviceController.removeService(serviceID);
                    System.out.println("Removed service ID: " + serviceID);

                    // Remove from your ArrayList too
                    serviceList.removeIf(s -> s.getServiceID() == serviceID);

                    // Remove the row from JTable model
                    DefaultTableModel model = (DefaultTableModel) SerList.getModel();
                    model.removeRow(selectedIndex);
                }

            }
        });
        DocList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int index = DocList.getSelectedIndex();
                if (index != -1 && index < documentList.size()) {
                    Service service = documentList.get(index); // get the corresponding Service object
                    String filePath = service.getFilePath();       // get the file path
                    openPDF(filePath);
                }
            }
        });

        btneditadd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Profile");
                frame.setContentPane(new UpdatePatientUI(patientReport,serviceController).getContentPane());
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }


    private void SetTopPanel(PatientReport patientReport){ //Setting Patient Profile Method
        //setting profile btn
        ImageIcon editbtnicon  = new ImageIcon("src/main/java/Assets/person_edit_24dp_000000_FILL0_wght400_GRAD0_opsz24.png");
        btneditadd.setIcon(editbtnicon);
        //setting profile image
        ImageIcon profileImage = new ImageIcon("src/main/java/Assets/icons/UserAvatar.png");
        Image image = profileImage.getImage();
        Image profile = image.getScaledInstance(100,100,Image.SCALE_SMOOTH);
        profileImage = new ImageIcon(profile);
        assert imageIcon != null;
        imageIcon.setIcon(profileImage);

        //back button
        btnBack.setIcon(new ImageIcon("src/main/java/Assets/icons/arrow_back_ios_25dp_000000_FILL0_wght400_GRAD0_opsz24.png"));

        //Setting Other details
        try{
            assert lblName != null;
            lblName.setText(patientReport.getName());
            assert lblGender != null;
            lblGender.setText(patientReport.getGender());
            assert lblPhoneNo != null;
            lblPhoneNo.setText(patientReport.getPhoneNumber());
            assert lblIllness != null;
            lblIllness.setText(patientReport.getIllness());
            assert lblBloodType != null;
            lblBloodType.setText(patientReport.getBloodType());
            assert lblRegDate != null;
            lblRegDate.setText(patientReport.getRegDate());
            assert lblAllergies != null;
            lblAllergies.setText(patientReport.getAllergies());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    private void SetNotePanel(){
        ImageIcon showservices = new ImageIcon("src/main/java/Assets/expand_all_25dp_000000_FILL0_wght400_GRAD0_opsz24.png");
        ShowNotes.setIcon(showservices);
        ImageIcon newicon = new ImageIcon("src/main/java/Assets/note_add_24dp_000000_FILL0_wght400_GRAD0_opsz24.png");
        btnnew.setIcon(newicon);
    }
    private void SetDocumentPanel(PatientReport patientReport){
        ImageIcon fileAttachment = new ImageIcon("src/main/java/Assets/attach_file_add_25dp_000000_FILL0_wght400_GRAD0_opsz24.png");
        btnAddFile.setIcon(fileAttachment);

        DocList.setModel(new DefaultListModel<>());
        DocList.setCellRenderer(new ListCellRenderer<Service>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends Service> list, Service value, int index, boolean isSelected, boolean cellHasFocus) {
                // Inner panel for content
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(Color.WHITE);
                card.setOpaque(true);

                JLabel icon = new JLabel();
                icon.setIcon(new ImageIcon("src/main/java/Assets/docs_25dp_000000_FILL0_wght400_GRAD0_opsz24.png"));

                JLabel label = new JLabel(value.getFile());
                label.setBorder(new EmptyBorder(5, 10, 5, 10));

                card.add(label, BorderLayout.CENTER);
                card.add(icon, BorderLayout.EAST);

                if (isSelected) {
                    card.setBackground(new Color(200, 230, 255));
                }

                // Outer panel adds spacing around the card
                JPanel wrapper = new JPanel(new BorderLayout());
                wrapper.setOpaque(false); // make background transparent
                wrapper.setBorder(new EmptyBorder(5, 5, 5, 5)); // top, left, bottom, right margin
                wrapper.add(card, BorderLayout.CENTER);

                return wrapper;
            }
        });

        //dynamic loader call
        refreshDocumentList(patientReport);

    }
    private void SetServicePanel(PatientReport patientReport){
        ImageIcon AddServices = new ImageIcon("src/main/java/Assets/docs_add_on_25dp_000000_FILL0_wght400_GRAD0_opsz24.png");
        btnaddSer.setIcon(AddServices);
        ImageIcon deleteservice = new ImageIcon("src/main/java/Assets/delete_24dp_000000_FILL0_wght400_GRAD0_opsz24.png");
        btndeleteSer.setIcon(deleteservice);

        serviceList = serviceController.service.getService(patientReport.getPatientID());
        if(serviceList == null){ return;}
        String[] headers ={"o","o","o","o"};
        //Table without header
        SerList = new CustomComponents.CustomTableServiceUI(new Object[][]{},headers);
        SerList.setTableHeader(null);
        SerSPane.setViewportView(SerList);


        //dynamic loader call
        loadServiceData(patientReport);



        //list model add Jlist to form and name as SerList
        /*
        DefaultListModel<Service> listModel = new DefaultListModel<>();
        for (Service s : serviceList) {
            listModel.addElement(s); // store Service objects directly
        }
        SerList.setModel(listModel);

        // Set custom cell renderer to look like cards
        SerList.setCellRenderer(new ListCellRenderer<Service>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends Service> list,
                                                          Service value,
                                                          int index,
                                                          boolean isSelected,
                                                          boolean cellHasFocus) {
                JPanel card = new JPanel();
                card.setLayout(new BoxLayout(card, BoxLayout.X_AXIS));
                card.setBackground(Color.WHITE);
                card.setBorder(new EmptyBorder(8, 12, 8, 12));
                card.setOpaque(true);

                // --- Format date nicely ---
                String dateStr = value.getCheckedTime();
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr); // adjust if your format differs
                    dateStr = new SimpleDateFormat("d MMM yyyy").format(date);     // 20 Jan 2025
                } catch (Exception e) {
                    // fallback: keep original string
                }

                // --- Date label ---
                JLabel dateLabel = new JLabel(dateStr);
                dateLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
                dateLabel.setForeground(new Color(40, 40, 40));

                // --- Service name label ---
                JLabel serviceLabel = new JLabel(value.getServiceName());
                serviceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                serviceLabel.setForeground(new Color(70, 70, 70));

                // --- Doctor name label ---
                JLabel doctorLabel = new JLabel("Dr. " + value.getDoctor());
                doctorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                doctorLabel.setForeground(new Color(90, 90, 90));

                // --- Add with spacing ---
                card.add(dateLabel);
                card.add(Box.createHorizontalStrut(30)); // space between date & service
                card.add(serviceLabel);
                card.add(Box.createHorizontalStrut(30)); // space between service & doctor
                card.add(doctorLabel);


                // --- Highlight selection ---
                if (isSelected) {
                    card.setBackground(new Color(200, 230, 255));
                }

                return card;
            }
        });
        */

    }


    private void loadServiceData(PatientReport patientReport){
        serviceList = serviceController.service.getService(patientReport.getPatientID());
        if (serviceList == null) return;
        //service list loader
        DefaultTableModel model = (DefaultTableModel) SerList.getModel();
        model.setRowCount(0); // clear old data

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");   // adjust to your stored format
        SimpleDateFormat outputFormat = new SimpleDateFormat("d MMM yyyy");  // e.g. 20 Jan 2025

        for (Service service : serviceList) {
            String formattedDate = service.getCheckedTime();
            try {
                Date date = inputFormat.parse(service.getCheckedTime());
                formattedDate = outputFormat.format(date);
            } catch (Exception e) {
                // fallback: leave original string
            }

            model.addRow(new Object[]{
                    service.getServiceID(),
                    formattedDate,
                    service.getServiceName(),
                    service.getDoctor()
            });
        }

    }

    private void refreshDocumentList(PatientReport patientReport) {
        documentList = serviceController.service.getDocuments(patientReport.getPatientID());
        if (documentList == null) return;

        DefaultListModel<Service> model = (DefaultListModel<Service>) DocList.getModel();
        model.clear();

        for (Service s : documentList) {
            model.addElement(s);
        }
    }
    //pdf viewer chatgpt
    private void openPDF(String filePath) {
        try {
            File pdfFile = new File(filePath);
            if (pdfFile.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile); // opens with default system PDF viewer
                } else {
                    JOptionPane.showMessageDialog(contentPane, "Desktop not supported.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(contentPane, "File not found: " + filePath, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(contentPane, "Failed to open PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void createUIComponents() {
        // TODO: place custom component creation code here
        //Top Panel Customization
        ProfilePanel = new CustomComponents.RoundedPanel(20);
        DetailPanel = new CustomComponents.RoundedPanel(20);
        DocumentPanel= new CustomComponents.RoundedPanel(20);
        NotePanel = new CustomComponents.RoundedPanel(20);
        Bottom = new CustomComponents.RoundedPanel(20);
    }


}
