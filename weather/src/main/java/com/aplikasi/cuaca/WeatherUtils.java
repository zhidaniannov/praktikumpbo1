package com.aplikasi.cuaca;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class WeatherUtils {

    public static List<Map<String, String>> loadWeatherData() {
        List<Map<String, String>> list = new ArrayList<>();
        try {
            // Pastikan file weather_data.json ada di root folder project
            String content = new String(Files.readAllBytes(Paths.get("weather_data.json")), StandardCharsets.UTF_8);
            content = content.trim();
            if (content.startsWith("[")) content = content.substring(1);
            if (content.endsWith("]")) content = content.substring(0, content.length() - 1);
            
            String[] objects = content.split("\\},\\s*\\{");
            for (String objStr : objects) {
                Map<String, String> map = new HashMap<>();
                objStr = objStr.replace("{", "").replace("}", "");
                String[] rawPairs = objStr.split("\",\\s*\""); 
                for (String pair : rawPairs) {
                    String cleanPair = pair.replace("\"", "");
                    String[] entry = cleanPair.split(":", 2); 
                    if (entry.length == 2) map.put(entry[0].trim(), entry[1].trim());
                }
                list.add(map);
            }
        } catch (IOException e) { 
            System.err.println("Gagal memuat data: " + e.getMessage());
        }
        return list;
    }

    public static String formatTemp(String raw, boolean isCelsius) {
        if(raw == null || raw.equals("-")) return "-";
        if(isCelsius) return raw; 
        try {
            String digits = raw.replaceAll("[^0-9-]", ""); 
            int c = Integer.parseInt(digits);
            int f = (int) ((c * 9.0/5.0) + 32);
            return f + "°F";
        } catch (Exception e) { return raw; }
    }

    public static String getWeatherEmoji(String condition) {
        if (condition == null) return "❓";
        String c = condition.toLowerCase();
        if (c.contains("petir")) return "⛈️";
        if (c.contains("hujan")) return "🌧️";
        if (c.contains("cerah") && c.contains("berawan")) return "⛅";
        if (c.contains("cerah")) return "☀️";
        if (c.contains("berawan")) return "☁️";
        if (c.contains("kabut") || c.contains("mendung") || c.contains("asap")) return "🌫️";
        if (c.contains("panas")) return "🔥";
        if (c.contains("sejuk")) return "🍃";
        return "🌡️";
    }

    // Helper untuk membuat Label dengan efek bayangan
    public static JLabel createShadowLabel(String text, Font font) {
        JLabel label = new JLabel(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(new Color(0,0,0,60));
                g2.drawString(getText(), 2, getHeight() - 4); 
                super.paintComponent(g);
            }
        };
        label.setFont(font);
        label.setForeground(Color.WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }
}