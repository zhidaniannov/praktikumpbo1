package com.groupv.weather; // Package diperbarui

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIComponents {

    public static class GlassPanel extends JPanel {
        private int arc;
        public GlassPanel(int arc) { this.arc = arc; setOpaque(false); }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(255, 255, 255, 30));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static class RoundedTextField extends JTextField {
        private int arc;
        public RoundedTextField(int arc) { this.arc = arc; setOpaque(false); setBorder(new EmptyBorder(5, 15, 5, 15)); }
        protected void paintComponent(Graphics g) {
            g.setColor(new Color(255,255,255, 50));
            g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, arc, arc);
            super.paintComponent(g);
        }
        protected void paintBorder(Graphics g) {
            g.setColor(new Color(255, 255, 255, 100));
            g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, arc, arc);
        }
    }

    public static class ModernButton extends JButton {
        private Color normal, hover;
        private boolean isHovered = false;
        public ModernButton(Icon icon, Color normal, Color hover) {
            super(icon); this.normal = normal; this.hover = hover;
            setContentAreaFilled(false); setBorderPainted(false); setFocusPainted(false);
            setPreferredSize(new Dimension(50, 45)); setCursor(new Cursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { isHovered = true; repaint(); }
                public void mouseExited(MouseEvent e) { isHovered = false; repaint(); }
            });
        }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(isHovered ? hover : normal);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}