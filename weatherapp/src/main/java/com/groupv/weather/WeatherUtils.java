package com.groupv.weather;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
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
     * Sekarang: mencoba beberapa nama file, dan menyediakan placeholder jika tidak ada file.
     */
    public static ImageIcon getWeatherIcon(String condition, int size) {
        if (condition == null) condition = "";
        condition = condition.toLowerCase();

        // Prioritas nama file/varian yang dicoba (urutkan kemungkinan)
        String[] candidates = new String[] {
            // basic mapping
            "cerah.png", "clear.png",
            "berawan.png", "clouds.png", "mendung.png",
            "hujan_ringan.png", "hujan.png", "rain.png",
            "badai.png", "petir.png", "thunder.png",
            "gerimis.png", "drizzle.png",
            // fallback generic
            "weather_default.png"
        };

        // Pilih candidate berdasarkan kondisi
        String chosen = null;
        if (condition.contains("cerah") || condition.contains("clear")) {
            chosen = "cerah.png";
        } else if (condition.contains("hujan") || condition.contains("gerimis") || condition.contains("rain")) {
            // coba beberapa varian hujan
            chosen = "hujan_ringan.png";
        } else if (condition.contains("petir") || condition.contains("badai") || condition.contains("thunder")) {
            chosen = "badai.png";
        } else if (condition.contains("berawan") || condition.contains("mendung") || condition.contains("cloud")) {
            chosen = "berawan.png";
        } else {
            chosen = "weather_default.png";
        }

        // Urutan percobaan: chosen pertama, lalu semua kandidat utk lebih robust
        String[] tryOrder = new String[candidates.length + 1];
        tryOrder[0] = chosen;
        System.arraycopy(candidates, 0, tryOrder, 1, candidates.length);

        // Coba load varian yang ada
        for (String filename : tryOrder) {
            if (filename == null) continue;
            ImageIcon ic = loadResizedIcon(filename, size, size);
            if (ic != null && ic.getIconWidth() > 0 && ic.getIconHeight() > 0) {
                return ic;
            }
        }

        // Jika semua gagal, kembalikan placeholder yang di-generate
        return createPlaceholderIcon(size, size, condition);
    }

    // Helper untuk memuat dan resize gambar, tapi tidak menampilkan error fatal
    private static ImageIcon loadResizedIcon(String filename, int width, int height) {
        try {
            URL imgUrl = WeatherUtils.class.getResource(ICON_PATH + filename);
            if (imgUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imgUrl);
                Image originalImage = originalIcon.getImage();
                Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(resizedImage);
            } else {
                // tidak ditemukan, kembalikan empty icon (caller akan handle fallback)
                // tapi log agar mudah debugging
                System.err.println("[WeatherUtils] ikon tidak ditemukan: " + ICON_PATH + filename);
                return new ImageIcon(); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ImageIcon();
        }
    }

    // Buat placeholder sederhana (lingkaran + huruf awal kondisi) supaya UI tidak kosong
    private static ImageIcon createPlaceholderIcon(int width, int height, String condition) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // background semi translucent
        g.setColor(new Color(255,255,255,30));
        g.fillRoundRect(0, 0, width, height, width/6, height/6);

        // circle center
        g.setColor(new Color(255,255,255,60));
        int r = Math.min(width, height)/2 - 6;
        g.fillOval((width/2)-r, (height/2)-r, 2*r, 2*r);

        // initial letter
        String txt = condition.isEmpty() ? "?" : (condition.substring(0,1).toUpperCase());
        g.setFont(new Font("Segoe UI", Font.BOLD, Math.max(12, width/3)));
        FontMetrics fm = g.getFontMetrics();
        int tx = (width - fm.stringWidth(txt)) / 2;
        int ty = (height - fm.getHeight()) / 2 + fm.getAscent();
        g.setColor(Color.WHITE);
        g.drawString(txt, tx, ty);

        g.dispose();
        return new ImageIcon(img);
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
