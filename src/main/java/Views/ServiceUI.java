package Views;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class ServiceUI {

    private JPanel mainPanel;
    private JPanel fixedTop;
    private JLabel Count;
    private JTextField searchTXT;
    private JButton Search;
    private JButton Filter;
    private JTable PatientsList;
    private JScrollPane scrollPane;



    public ServiceUI() {
        // Sample data
        String[] columns = {"ID", "Name", "Age"};
        Object[][] data = {
                {1, "Alice", 25},
                {2, "Bob", 30},
                {3, "Charlie", 28},
                {1, "Alice", 25},
                {2, "Bob", 30},
                {3, "Charlie", 28},
                {1, "Alice", 25},
                {2, "Bob", 30},
                {3, "Charlie", 28},
                {1, "Alice", 25},
                {2, "Bob", 30},
                {3, "Charlie", 28}
        };

        PatientsList = new CustomComponents.CustomTableServiceUI(data, columns);
        scrollPane.setViewportView(PatientsList);
    }

    public static void main(String[] args) {
        UIManager.put("ScrollBar.trackInsets", new Insets(2, 4, 2, 4));
        UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        UIManager.put("ScrollBar.track", new Color(0xe0e0e0));
        UIManager.put("TableHeader.font", new Font("Segoe UI", Font.ITALIC, 14));
        UIManager.put("TableHeader.foreground", new java.awt.Color(255, 255, 255, 255));
        UIManager.put("TableHeader.background", new java.awt.Color(169, 169, 169, 255));
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
