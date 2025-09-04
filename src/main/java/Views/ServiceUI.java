package Views;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class ServiceUI {

    private JPanel mainPanel;
    private JPanel fixedTop;
    private JLabel Count;
    private JTextField searchTXT;
    private JButton Search;
    private JButton Filter;
    private JTable PatientsList;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Doctor Dashboard");
        frame.setContentPane(new ServiceUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //flatlaf dependency execution code
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
    }
}
