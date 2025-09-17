package Views;

import Controllers.ServiceController;
import Models.Patient;
import com.formdev.flatlaf.FlatLightLaf;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class NurseDashboardGUI {
    private JPanel ViewPanel;
    private JPanel BackPanel;
    private JLabel logoArea;
    private JButton signoutButton;
    private JPanel sidePanel2;
    private JPanel sidepanel1;
    private JPanel logoutArea;
    private JButton reportsButton;
    private JPanel LogoPanel;
    private JButton btnBillManagement;
    private JButton btnServiceEntries;
    private JTextField searchTXT;
    private JScrollPane scrollPane;
    private JTable PatientsList;
    private JComboBox comboBoxFilter;
    private JPanel MainStage;


    //Table row initialization and sorter
    private int selectedRow = -1;
    private final TableRowSorter<DefaultTableModel> sorter;

    //controller attributes
    ServiceController serviceController;

    //model attributes
    Patient patient;

    public NurseDashboardGUI(){
    /*
        CardLayout cardLayout = new CardLayout();
        ViewPanel.setLayout(cardLayout);
        ViewPanel.setMinimumSize(new Dimension(400, 1000));*/

        logoArea.setOpaque(false);
        sidepanel1.setOpaque(false);
        LogoPanel.setOpaque(false);
        logoutArea.setOpaque(false);

        btnServiceEntries.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // chenura

            }
        });

        btnBillManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // menura
            }
        });
        //-------------------------------------------------------------------------------------------------------------------///
        // creating object in the constructor
        serviceController = new ServiceController();

        //construct table load

        String[] headers = {"PatientID", "Name", "Age", "PhoneNumber"};
        PatientsList = new CustomComponents.CustomTableServiceUI(new Object[][]{}, headers);
        DefaultTableModel model = (DefaultTableModel) PatientsList.getModel();
        sorter = new TableRowSorter<>(model);
        PatientsList.setRowSorter(sorter);
        scrollPane.setViewportView(PatientsList);

        //load data to table
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
                    patient=serviceController.service.getPatientDetails(selectedRow);
                    System.out.println(patient);
                    //MainStage.setVisible(false);

                    MainStage.removeAll();
                    MainStage.setLayout(new BorderLayout());

                    MainStage.add(new Views.ServiceProfileUI(patient).BackPanel, BorderLayout.CENTER);
                    MainStage.revalidate();
                    MainStage.repaint();


                   // frame.setContentPane(new Views.ServiceProfileUI(patient).BackPanel);
                   // frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    //frame.pack();
                    //frame.setVisible(true);
                }
            }
        });
        searchTXT.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                performSearch();
            }
        });
//-------------------------------------------------------------------------------------------------------------------///
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

        JFrame frame = new JFrame("CDC");
        frame.setContentPane(new NurseDashboardGUI().BackPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        frame.setVisible(true);
    }

    private void createUIComponents(){

        sidePanel2 = new JPanel() {
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D g2d) {

                    // Define the start and end points of the gradient
                    float startX = 0.0f;
                    float startY = 0.0f;
                    float endX = 0.1f;
                    float endY = getHeight();

                    float[] fractions = {0.5f, 1.0f};
                    Color[] colors = {
                            new Color(22, 37, 84, 255),
                            new Color(49, 82, 186, 255)

                    };

                    // Create a LinearGradientPaint object
                    LinearGradientPaint p = new LinearGradientPaint(startX, startY, endX, endY, fractions, colors);

                    g2d.setPaint(p);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    super.paintComponent(g);
                }
            }
        };

        ImageIcon logo = new ImageIcon("src/main/java/assets/logo.png");
        Image logoImage = logo.getImage();
        Image newLogo = logoImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logo = new ImageIcon(newLogo);
        logoArea = new JLabel(logo);


        btnServiceEntries = new CustomComponents.CustomButton("btnServiceEntries");
        btnServiceEntries.setMinimumSize(new Dimension(40, 35));

        btnBillManagement = new CustomComponents.CustomButton("Button2");
        btnBillManagement.setMinimumSize(new Dimension(40, 35));

        reportsButton = new CustomComponents.CustomButton("Reports");
        reportsButton.setMinimumSize(new Dimension(50, 40));

        signoutButton = new CustomComponents.CustomButton("Sign Out");
        signoutButton.setMinimumSize(new Dimension(0, 40));
        signoutButton.setFont(new Font("Arial", Font.PLAIN, 18));
    }
}
