package Views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ServiceProfileUI {
    private JPanel BackPanel;
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
    private JButton addServiceBTN;

    ServiceProfileUI(){
        setProfilePanel();
        setGeneralInformationPanel();
        setNotePanel();
        setFileList();
        setServicesPanel();
    }

    public void setProfilePanel(){
        profiledetailpanel.setPreferredSize(new Dimension(150, 150));
        profiledetailpanel.setMinimumSize(new Dimension(150, 150));
        profiledetailpanel.setMaximumSize(new Dimension(150, 150));

        profiledetailpanel.setBackground(new Color(220, 220, 220));
        profiledetailpanel.setLayout(new BoxLayout(this.profiledetailpanel, BoxLayout.Y_AXIS));

        ImageIcon imageIcon = new ImageIcon("C:\\Users\\isum\\OneDrive\\Desktop\\Y02 Sem02\\PPA\\ServiceManagementSystem\\clinic-management-system\\src\\main\\java\\Views\\account_circle_45dp_000000_FILL0_wght400_GRAD0_opsz48.png");
        ImageCenterRounded = new JLabel(imageIcon);
        ImageCenterRounded.setPreferredSize(new Dimension(60,60));
        ImageCenterRounded.setAlignmentX(Component.CENTER_ALIGNMENT);


        lblName.setText("Isum Hansana");
        lblName.setFont(new Font("Arial", Font.BOLD, 14));
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblgmail.setText("isum@gmail.com");
        lblgmail.setFont(new Font("Arial", Font.PLAIN, 12));
        lblgmail.setAlignmentX(Component.CENTER_ALIGNMENT);


        profiledetailpanel.add(Box.createVerticalGlue());
        profiledetailpanel.add(ImageCenterRounded);
        profiledetailpanel.add(lblName);
        profiledetailpanel.add(lblgmail);
        profiledetailpanel.add(Box.createVerticalGlue());

    }

    public void setGeneralInformationPanel(){
        generalInformationPanel.setPreferredSize(new Dimension(300, 150));
        generalInformationPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        generalInformationPanel.setBackground(new Color(220, 220, 220));
        lblgender.setText("Male");
        lblPhone.setText("071-892-1130");
        lblRegDate.setText("2015-11-10");
        lblillness.setText("Virus");
        lblAllergies.setText("Rice");
        lblblood.setText("O+");
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

    public void setServicesPanel(){
        servicesPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        servicesPanel.setBackground(new Color(220, 220, 220));

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Service Profile");
        frame.setContentPane(new ServiceProfileUI().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



}
