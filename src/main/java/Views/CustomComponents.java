package Views;



import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class CustomComponents {

    static class CustomButton extends JButton {

        private Color targetColor;
        private Color currentColor;
        private Timer colorTransitionTimer;

        // Constructor
        public CustomButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);

            currentColor = new Color(255, 255, 255);
            targetColor = currentColor;

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {

                    targetColor = new Color(89, 226, 89);
                    setForeground(Color.WHITE);
                    startColorTransition();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    targetColor = new Color(255, 255, 255);
                    setForeground(Color.BLACK);
                    startColorTransition();
                }
            });
        }

        private void startColorTransition() {
            if (colorTransitionTimer != null && colorTransitionTimer.isRunning()) {
                colorTransitionTimer.stop();
            }

            colorTransitionTimer = new Timer(10, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int r = currentColor.getRed() + (targetColor.getRed() - currentColor.getRed()) / 10;
                    int g = currentColor.getGreen() + (targetColor.getGreen() - currentColor.getGreen()) / 10;
                    int b = currentColor.getBlue() + (targetColor.getBlue() - currentColor.getBlue()) / 10;

                    currentColor = new Color(r, g, b);
                    setBackground(currentColor);

                    if (currentColor.equals(targetColor)) {
                        colorTransitionTimer.stop();
                    }
                }
            });
            colorTransitionTimer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (getModel().isArmed()) {
                g2d.setColor(new Color(1, 93, 139));
            } else if (getModel().isRollover()) {
                g2d.setColor(currentColor);
            } else {
                g2d.setColor(currentColor);
            }

            int arcSize = 15;
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcSize, arcSize);

            g2d.setColor(new Color(0, 0, 0));
            g2d.setFont(new Font("Arial", Font.BOLD, 15));
            FontMetrics metrics = g2d.getFontMetrics();

            int x = (getWidth() - metrics.stringWidth(getText())) / 2;
            int y = (getHeight() + metrics.getAscent()) / 2;


            super.paintComponent(g);
        }
    }

    static public class CustomTableServiceUI extends JTable{

            public CustomTableServiceUI(Object[][]data, String[] headers){
                super(new javax.swing.table.DefaultTableModel(data, headers));
                setRowHeight(50);
                setFillsViewportHeight(true);
                setDefaultRenderer(Object.class, new CustomCellRenderer());
                //setSelectionBackground(new java.awt.Color(51, 78, 172)); blue color hover
                setSelectionBackground(new java.awt.Color(24, 27, 30));
                setSelectionForeground(new java.awt.Color(255, 255, 255, 255));
                setIntercellSpacing(new java.awt.Dimension(0, 0));
                setAutoCreateRowSorter(true);

            }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Prevent editing
        }

        static class CustomCellRenderer extends DefaultTableCellRenderer {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    setBackground(table.getSelectionBackground());
                    setForeground(table.getSelectionForeground());
                } else {
                    setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                    setForeground(Color.BLACK);
                }

                setHorizontalAlignment(CENTER);
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
                return this;
            }
        }
    }
}
