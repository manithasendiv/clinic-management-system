package Views;

import Controllers.ServiceController;
import Models.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NotesPanel {
    private JPanel ContentPane;
    private JList NoteList;
    private JPanel NotesPanel;
    private JScrollPane scrollpane;
    private JButton btndeletenote;

    public JPanel getContentPane() {
        return ContentPane;
    }
    ServiceController serviceController;
    ArrayList<Service> noteList;

    NotesPanel(int id,ServiceController sControllerObject){
        serviceController = sControllerObject;

        //setting delete btn
        ImageIcon deleteicon = new ImageIcon("src/main/java/Assets/delete_24dp_000000_FILL0_wght400_GRAD0_opsz24.png");
        btndeletenote.setIcon(deleteicon);

        noteList = serviceController.service.getNotes(id);
        DefaultListModel<Service> listModel = new DefaultListModel<>();

        for (Service s : noteList) {
            listModel.addElement(s); // store Service objects directly
        }

        NoteList.setModel(listModel);
        NoteList.setCellRenderer(new ListCellRenderer<Service>() {
            @Override
            public Component getListCellRendererComponent(
                    JList<? extends Service> list, Service value, int index,
                    boolean isSelected, boolean cellHasFocus) {

                JPanel panel = new JPanel(new BorderLayout(5, 5));
                panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                panel.setBackground(isSelected ? new Color(220, 235, 255) : Color.WHITE);

                // Top row (date left, serviceId right)
                JPanel topRow = new JPanel(new BorderLayout());
                topRow.setOpaque(false);

                JLabel dateLabel = new JLabel(value.getCheckedTime());
                dateLabel.setFont(new Font("Arial", Font.BOLD, 12));

                JLabel serviceIdLabel = new JLabel(String.valueOf(value.getServiceID()));
                serviceIdLabel.setFont(new Font("Arial", Font.PLAIN, 12));
                serviceIdLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                topRow.add(dateLabel, BorderLayout.WEST);
                topRow.add(serviceIdLabel, BorderLayout.EAST);

                // Note text below
                JLabel noteLabel = new JLabel("<html>" + value.getSpecialNotes() + "</html>");
                noteLabel.setFont(new Font("Arial", Font.PLAIN, 13));

                panel.add(topRow, BorderLayout.NORTH);
                panel.add(noteLabel, BorderLayout.CENTER);

                return panel;
            }
        });
        scrollpane.setBorder(BorderFactory.createEmptyBorder());

        btndeletenote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = NoteList.getSelectedIndex(); // get the selected row
                if (selectedIndex != -1) { // make sure something is selected
                    Service noteToRemove = noteList.get(selectedIndex); // get service object
                    serviceController.service.deleteNote(noteToRemove.getNoteID());
                    System.out.println(noteToRemove.getNoteID());
                    DefaultListModel<String> model = (DefaultListModel<String>) NoteList.getModel();
                    model.remove(selectedIndex);
                }
            }
        });
    }



    private void createUIComponents() {
        // TODO: place custom component creation code here
        NotesPanel = new CustomComponents.RoundedPanel(20);
    }
}
