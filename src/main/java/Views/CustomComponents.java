package Views;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.plaf.basic.BasicComboBoxUI;
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

            currentColor = new Color(89, 226, 89); //
            targetColor = currentColor;

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {

                    targetColor = new Color(255, 255, 255);
                    setForeground(Color.BLACK);
                    startColorTransition();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    targetColor = new Color(89, 226, 89);
                    setForeground(Color.WHITE);
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

    static class RoundedJTextField extends JTextField {
        private Shape shape;

        public RoundedJTextField(int size) {
            super(size);
            setBounds(520, 284, 190, 25);
            setBackground(new Color(0, 0, 0, 0));
            setOpaque(false);
            setMargin(new Insets(5, 20, 5, 20));
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(0, 0, 0, 0));
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);

            super.paintComponent(g);
            g2.dispose();
        }

        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Color.DARK_GRAY);

            float borderWidth = 1.0f;
            g2.setStroke(new BasicStroke(borderWidth));

            g2.drawRoundRect(
                    0,
                    0,
                    getWidth() - (int) borderWidth,
                    getHeight() - (int) borderWidth,
                    30, 30
            );

            g2.dispose();
        }
    }

    static class RoundedJPasswordField extends JPasswordField {
        private Shape shape;

        public RoundedJPasswordField(int size) {
            super(size);
            setBounds(520, 284, 190, 25);
            setBackground(new Color(0, 0, 0, 0));
            setOpaque(false);
            setMargin(new Insets(5, 20, 5, 20));
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw background
            g2.setColor(new Color(0, 0, 0, 0));
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);

            super.paintComponent(g);
            g2.dispose();
        }

        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Set border color
            g2.setColor(Color.DARK_GRAY);

            float borderWidth = 1.0f;
            g2.setStroke(new BasicStroke(borderWidth));

            g2.drawRoundRect(
                    0,
                    0,
                    getWidth() - (int) borderWidth,
                    getHeight() - (int) borderWidth,
                    30, 30
            );

            g2.dispose();
        }
    }

    static class RoundedPanel extends JPanel {
        private int cornerRadius;
        private Color borderColor = Color.BLACK; // default

        public RoundedPanel(int radius) {
            super();
            this.cornerRadius = radius;
            setOpaque(false);
        }

        public void setBorderColor(Color color) {
            this.borderColor = color;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // background
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, arcs.width, arcs.height);

            // border
            g2.setColor(borderColor);
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, arcs.width, arcs.height);

            g2.dispose();
        }
    }


    static class CustomTextArea extends JTextArea {
        public CustomTextArea(int rows, int columns) {
            super(rows, columns);

            setPreferredSize(new Dimension(150, 100));
            setMinimumSize(new Dimension(150, 100));
            setMaximumSize(new Dimension(150, 100));

            setOpaque(false);
            setFont(new Font("Arial", Font.PLAIN, 14));
            setLineWrap(true);
            setWrapStyleWord(true);
            setMargin(new Insets(10, 10, 10, 10));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(240, 240, 240));
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Color.GRAY);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

            g2.dispose();
        }
    }

    static class CustomComboBox extends JComboBox<String> {

        public CustomComboBox() {
            super();
            setUI(new CustomComboBoxUI());
            setRenderer(new CustomComboBoxRenderer());
            setPreferredSize(new Dimension(200, 30));
        }

        private static class CustomComboBoxUI extends BasicComboBoxUI {
            @Override
            protected JButton createArrowButton() {
                // Create a custom arrow button
                JButton button = new JButton("▼");
                button.setFont(new Font("Arial", Font.BOLD, 12));
                button.setFocusPainted(false);
                button.setBorderPainted(false);
                button.setContentAreaFilled(false);
                button.setForeground(new Color(100, 100, 100));
                return button;
            }

            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(240, 240, 240)); // Light gray for the background
                g2d.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 20, 20);
            }

            @Override
            protected void installDefaults() {
                super.installDefaults();
                comboBox.setOpaque(false);
                comboBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
            }
        }

        private static class CustomComboBoxRenderer extends BasicComboBoxRenderer {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setOpaque(true);

                if (isSelected) {
                    label.setBackground(new Color(0, 119, 182));
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(Color.WHITE);
                    label.setForeground(new Color(50, 50, 50));
                }
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return label;
            }
        }
    }


}
