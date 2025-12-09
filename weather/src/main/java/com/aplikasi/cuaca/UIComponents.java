package com.aplikasi.cuaca;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIComponents {

    // --- GLASS PANEL (Panel Transparan Kaca) ---
    public static class GlassPanel extends JPanel {
        private int arc;
        public GlassPanel(int arc) {
            this.arc = arc;
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(255, 255, 255, 30)); // Background transparan
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
            g2.setColor(new Color(255, 255, 255, 100)); // Border tipis
            g2.setStroke(new BasicStroke(1f));
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, arc, arc);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // --- ROUNDED TEXT FIELD ---
    public static class RoundedTextField extends JTextField {
        private int arc;
        public RoundedTextField(int arc) { 
            this.arc = arc; 
            setOpaque(false); 
            setBorder(new EmptyBorder(5, 15, 5, 15)); 
        }
        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(new Color(255,255,255, 50));
            g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, arc, arc);
            super.paintComponent(g);
        }
        @Override
        protected void paintBorder(Graphics g) {
            g.setColor(new Color(255, 255, 255, 100));
            g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, arc, arc);
        }
    }

    // --- MODERN BUTTON (FIXED) ---
    public static class ModernButton extends JButton {
        private Color normalColor; 
        private Color hoverColor;
        private boolean isHovered = false; // Melacak status hover sendiri

        public ModernButton(Icon icon, Color normal, Color hover) {
            super(icon); 
            this.normalColor = normal; 
            this.hoverColor = hover;
            
            // Matikan semua dekorasi bawaan tombol agar tidak glitch saat hover
            setContentAreaFilled(false); 
            setFocusPainted(false);
            setBorderPainted(false); 
            setOpaque(false); // Penting untuk custom painting
            
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(50, 45));
            
            // Pastikan layout manager tombol menengahkan ikon
            setHorizontalAlignment(SwingConstants.CENTER);
            setVerticalAlignment(SwingConstants.CENTER);
            setMargin(new Insets(0,0,0,0)); 
            
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { 
                    isHovered = true;
                    repaint(); // Gambar ulang manual
                }
                public void mouseExited(MouseEvent e) { 
                    isHovered = false;
                    repaint(); // Gambar ulang manual
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Gambar background manual (Lingkaran/Kotak tumpul) sesuai status hover
            if (isHovered) {
                g2.setColor(hoverColor);
            } else {
                g2.setColor(normalColor);
            }
            
            // Gambar background (Rounded Rectangle halus)
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.dispose();

            // Panggil super untuk menggambar ikon di atas background yang sudah kita gambar
            super.paintComponent(g); 
        }
    }
}