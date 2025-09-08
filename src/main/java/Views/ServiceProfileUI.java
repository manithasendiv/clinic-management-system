package Views;

import Controllers.ServiceController;
import Models.Patient;
import Models.Service;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Stack;

public class ServiceProfileUI {
    JPanel BackPanel;
    private JScrollPane ScrollPane1;
    private JButton addButton;
    private JTextArea textAreaNotes;
    private JButton addFilesBTN;
    private JList FileLIST;
    private JLabel ImageCenterRounded;
    private JLabel lblName;
    private JLabel lblgmail;
    private JPanel profiledetailpanel;
    private JPanel generalInformationPanel;
    private JLabel lblgender;
    private JLabel lblPhone;
    private JLabel lblRegDate;
    private JLabel lblillness;
    private JLabel lblblood;
    private JLabel lblAllergies;
    private JPanel notePanel;
    private JPanel filesPanel;
    private JPanel servicesPanel;
    private JList serviceList;
    JButton addServiceBTN;
    JScrollPane servicescroll;
    Service service;
    ServiceController serviceController;


    ServiceProfileUI(Patient patient){
        serviceController = new ServiceController();
        setProfilePanel(patient);
        setGeneralInformationPanel(patient);
        setNotePanel();
        setFileList();
        setServicesPanel(patient);
        int serviceID = patient.getPatientID();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String note = textAreaNotes.getText();
                    if(note.isEmpty()){
                        JOptionPane.showMessageDialog(BackPanel,"Please Write Note","Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    service = serviceController.addNote(serviceID,note);
                    if(serviceController.addNoteToDatabase()){
                        JOptionPane.showMessageDialog(BackPanel, "Note added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(BackPanel,"UNDEFINED","Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public void setProfilePanel(Patient patient){
        profiledetailpanel.setPreferredSize(new Dimension(150, 150));
        profiledetailpanel.setMinimumSize(new Dimension(150, 150));
        profiledetailpanel.setMaximumSize(new Dimension(150, 150));

        profiledetailpanel.setBackground(new Color(220, 220, 220));
        profiledetailpanel.setLayout(new BoxLayout(this.profiledetailpanel, BoxLayout.Y_AXIS));

        ImageIcon imageIcon = new ImageIcon("C:\\Users\\isum\\OneDrive\\Desktop\\Y02 Sem02\\PPA\\ServiceManagementSystem\\" +
                "clinic-management-system\\src\\main\\java\\Views\\account_circle_45dp_000000_FILL0_wght400_GRAD0_opsz48.png");
        ImageCenterRounded = new JLabel(imageIcon);
        ImageCenterRounded.setPreferredSize(new Dimension(60,60));
        ImageCenterRounded.setAlignmentX(Component.CENTER_ALIGNMENT);


        lblName.setText(patient.getName());
        lblName.setFont(new Font("Arial", Font.BOLD, 14));
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblgmail.setText(patient.getPhoneNumber());
        lblgmail.setFont(new Font("Arial", Font.PLAIN, 12));
        lblgmail.setAlignmentX(Component.CENTER_ALIGNMENT);


        profiledetailpanel.add(Box.createVerticalGlue());
        profiledetailpanel.add(ImageCenterRounded);
        profiledetailpanel.add(lblName);
        profiledetailpanel.add(lblgmail);
        profiledetailpanel.add(Box.createVerticalGlue());

    }

    public void setGeneralInformationPanel(Patient patient){
        generalInformationPanel.setPreferredSize(new Dimension(300, 150));
        generalInformationPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        generalInformationPanel.setBackground(new Color(220, 220, 220));
        lblgender.setText(patient.getGender());
        lblPhone.setText(patient.getPhoneNumber());
        lblRegDate.setText(patient.getRegDate());
        lblillness.setText(patient.getIllness());
        lblAllergies.setText(patient.getAllergies());
        lblblood.setText(patient.getBloodType());
    }

    public void setNotePanel(){
        notePanel.setPreferredSize(new Dimension(150, 150));
        notePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        notePanel.setBackground(new Color(220, 220, 220));
    }

    public void setFileList(){
        filesPanel.setPreferredSize(new Dimension(300, 150));
        filesPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        filesPanel.setBackground(new Color(220, 220, 220));
    }

    public void setServicesPanel(Patient patient){
        Stack<Service> servicelistStack = serviceController.service.getService(patient.getPatientID());
        if(servicelistStack == null){return;}

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Service s : servicelistStack) {
            listModel.addElement(s.getCheckedTime() + " - " + s.getServiceName() + " (" + s.getDoctor() + ")");
        }
        serviceList = new JList<>(listModel);
        servicesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        servicesPanel.setBackground(new Color(220, 220, 220));
        servicescroll.setViewportView(serviceList);
        servicescroll.revalidate();
        servicescroll.repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Service Profile");
       // frame.setContentPane(new ServiceProfileUI().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



}
