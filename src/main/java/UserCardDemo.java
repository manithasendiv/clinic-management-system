import javax.swing.*;
import java.awt.*;

public class UserCardDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Freelancer Catalog");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);

            // Main catalog panel with GridLayout
            JPanel catalogPanel = new JPanel(new GridLayout(0, 3, 20, 20)); // 3 cards per row
            catalogPanel.setBackground(new Color(240, 242, 245));
            catalogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Add sample user cards
            catalogPanel.add(createUserCard("Michael Spitz", "Los Angeles, CA", "Available", "$80/hr", "12 Projects"));
            catalogPanel.add(createUserCard("Marco Coppeto", "New York, NY", "Not Available", "$100/hr", "20 Projects"));
            catalogPanel.add(createUserCard("Gene Ross", "Los Angeles, CA", "Available", "$75/hr", "8 Projects"));

            // Scroll pane
            JScrollPane scrollPane = new JScrollPane(catalogPanel);
            frame.add(scrollPane);

            frame.setVisible(true);
        });
    }

    private static JPanel createUserCard(String name, String location, String status, String rate, String projects) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(250, 250));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));

        // Top row: avatar + name + location + status
        JLabel avatar = new JLabel("IMG", SwingConstants.CENTER);
        avatar.setOpaque(true);
        avatar.setBackground(Color.GRAY);
        avatar.setPreferredSize(new Dimension(60, 60));

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setOpaque(false);
        infoPanel.add(new JLabel(name));
        infoPanel.add(new JLabel(location));
        JLabel statusLabel = new JLabel(status);
        statusLabel.setForeground(status.equals("Available") ? Color.GREEN.darker() : Color.RED);
        infoPanel.add(statusLabel);

        topRow.add(avatar, BorderLayout.WEST);
        topRow.add(infoPanel, BorderLayout.CENTER);

        // Description
        JTextArea desc = new JTextArea("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accus...");
        desc.setWrapStyleWord(true);
        desc.setLineWrap(true);
        desc.setEditable(false);
        desc.setOpaque(false);

        // Footer: projects + rate + button
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footer.setOpaque(false);
        footer.add(new JLabel(projects));
        footer.add(new JLabel(rate));

        JButton inviteBtn = new JButton("Invite for Job");
        inviteBtn.setBackground(new Color(66, 103, 255));
        inviteBtn.setForeground(Color.WHITE);
        inviteBtn.setFocusPainted(false);
        footer.add(inviteBtn);

        // Add all parts
        card.add(topRow);
        card.add(Box.createVerticalStrut(10));
        card.add(desc);
        card.add(Box.createVerticalGlue());
        card.add(footer);

        return card;
    }
}
