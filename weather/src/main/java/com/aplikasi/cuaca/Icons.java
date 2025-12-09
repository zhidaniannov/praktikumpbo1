package com.aplikasi.cuaca;

import javax.swing.*;
import java.awt.*;

public class Icons {

    /**
     * Ikon Kaca Pembesar (Search)
     * Perbaikan: Rumus centering diperbaiki agar tidak terlalu ke kanan bawah.
     */
    public static class SearchIcon implements Icon {
        private int size;
        private Color color;

        public SearchIcon(int size, Color color) {
            this.size = size;
            this.color = color;
        }

        public int getIconWidth() { return size; }
        public int getIconHeight() { return size; }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);

            int padding = 2;
            int drawSize = size - (2 * padding);
            
            int circleDiameter = (int) (drawSize * 0.7); 
            int strokeWidth = Math.max(2, size / 10);
            g2.setStroke(new BasicStroke(strokeWidth));

            int circleX = x + padding + (drawSize - circleDiameter) / 2 - 1; 
            int circleY = y + padding + (drawSize - circleDiameter) / 2 - 1;

            g2.drawOval(circleX, circleY, circleDiameter, circleDiameter);

            double centerX = circleX + circleDiameter / 2.0;
            double centerY = circleY + circleDiameter / 2.0;
            double radius = circleDiameter / 2.0;
            double angle = Math.toRadians(45);
            
            int x1 = (int) (centerX + Math.cos(angle) * radius);
            int y1 = (int) (centerY + Math.sin(angle) * radius);
            
            int handleLen = (int)(drawSize * 0.35);
            int x2 = (int) (x1 + Math.cos(angle) * handleLen);
            int y2 = (int) (y1 + Math.sin(angle) * handleLen);

            g2.drawLine(x1, y1, x2, y2);
            g2.dispose();
        }
    }

    /**
     * Ikon Gerigi (Settings)
     */
    public static class SettingsIcon implements Icon {
        private int size;
        private Color color;

        public SettingsIcon(int size, Color color) {
            this.size = size;
            this.color = color;
        }

        public int getIconWidth() { return size; }
        public int getIconHeight() { return size; }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);

            g2.translate(x + size / 2.0, y + size / 2.0);

            int rInner = (int)(size * 0.25);
            int rOuter = (int)(size * 0.45);
            int toothW = (int)(size * 0.12);
            int toothH = (int)(size * 0.15);

            g2.setStroke(new BasicStroke(Math.max(2, size / 8)));
            
            g2.drawOval(-rInner, -rInner, rInner * 2, rInner * 2);

            for (int i = 0; i < 8; i++) {
                g2.rotate(Math.PI / 4);
                g2.fillRect(-toothW / 2, -rOuter, toothW, toothH);
            }

            g2.dispose();
        }
    }

    /**
     * Ikon Radio Button Modern
     */
    public static class ModernRadioIcon implements Icon {
        private boolean isSelected;
        private int size = 20;

        public ModernRadioIcon(boolean isSelected) {
            this.isSelected = isSelected;
        }

        public int getIconWidth() { return size; }
        public int getIconHeight() { return size; }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int centerX = x + size / 2;
            int centerY = y + size / 2;
            int radius = (size - 4) / 2;

            if (isSelected) {
                g2.setColor(new Color(52, 152, 219));
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
                int innerR = radius - 3;
                g2.fillOval(centerX - innerR, centerY - innerR, innerR * 2, innerR * 2);
            } else {
                g2.setColor(Color.LIGHT_GRAY);
                g2.setStroke(new BasicStroke(2));
                g2.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
            }
            g2.dispose();
        }
    }

    /**
     * Ikon Centang (Checklist) untuk pilihan aktif
     */
    public static class CheckIcon implements Icon {
        private int size;
        private Color color;

        public CheckIcon(int size, Color color) {
            this.size = size;
            this.color = color;
        }

        public int getIconWidth() { return size; }
        public int getIconHeight() { return size; }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(Math.max(2, size/6), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(color);

            // Koordinat garis centang
            int p1x = x + (int)(size * 0.2);
            int p1y = y + (int)(size * 0.5);
            int p2x = x + (int)(size * 0.45);
            int p2y = y + (int)(size * 0.75);
            int p3x = x + (int)(size * 0.8);
            int p3y = y + (int)(size * 0.25);

            g2.drawLine(p1x, p1y, p2x, p2y);
            g2.drawLine(p2x, p2y, p3x, p3y);
            g2.dispose();
        }
    }
}