package com.groupv.weather;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class WeatherUtils {
    
    // Path ke folder icon
    private static final String ICON_PATH = "/icon/";

    public static String formatTemp(String raw, boolean isCelsius) {
        if(raw == null) return "-";
        if(isCelsius) return raw;
        try {
            int c = Integer.parseInt(raw.replace("°C", "").trim());
            return ((int)(c * 1.8 + 32)) + "°F";
        } catch(Exception e) { return raw; }
    }

    /**
     * Mengambil ImageIcon berdasarkan kondisi cuaca.
     * Menggantikan metode getWeatherEmoji yang lama.
     */
    public static ImageIcon getWeatherIcon(String condition, int size) {
        if (condition == null) condition = "";
        condition = condition.toLowerCase();

        String filename = "berawan.png"; // Default fallback

        // Logika pemetaan kondisi cuaca ke nama file
        if (condition.contains("cerah")) {
            filename = "cerah.png";
        } else if (condition.contains("hujan") || condition.contains("gerimis")) {
            filename = "hujan_ringan.png";
        } else if (condition.contains("badai") || condition.contains("petir") || condition.contains("deras")) {
            filename = "badai.png";
        } else if (condition.contains("berawan") || condition.contains("mendung")) {
            filename = "berawan.png";
        }

        return loadResizedIcon(filename, size, size);
    }

    // Helper untuk memuat dan resize gambar
    private static ImageIcon loadResizedIcon(String filename, int width, int height) {
        try {
            URL imgUrl = WeatherUtils.class.getResource(ICON_PATH + filename);
            if (imgUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imgUrl);
                Image originalImage = originalIcon.getImage();
                Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(resizedImage);
            } else {
                System.err.println("Gagal memuat ikon: " + filename);
                return new ImageIcon();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ImageIcon();
        }
    }

    public static JLabel createShadowLabel(String text, Font font) {
        JLabel l = new JLabel(text) {
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(0,0,0,50));
                g.drawString(getText(), 2, getHeight()-2);
                super.paintComponent(g);
            }
        };
        l.setFont(font); l.setForeground(Color.WHITE); l.setHorizontalAlignment(SwingConstants.CENTER);
        return l;
    }
}