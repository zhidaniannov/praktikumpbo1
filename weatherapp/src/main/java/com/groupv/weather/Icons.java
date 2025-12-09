package com.groupv.weather;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Icons {

    // Path ke folder resources di dalam classpath.
    private static final String ICON_PATH = "/icon/";

    public static ImageIcon getSearchIcon(int size) {
        return loadResizedIcon("search.png", size, size);
    }

    public static ImageIcon getSettingsIcon(int size) {
        return loadResizedIcon("gear.jpg", size, size);
    }

    // --- Tambahkan method ini agar error di SimpleWeatherApp hilang ---
    public static ImageIcon getHumidityIcon(int size) {
        // Pastikan file humidity.png ada di folder resources/icon/
        return loadResizedIcon("humidity.png", size, size);
    }

    private static ImageIcon loadResizedIcon(String filename, int width, int height) {
        try {
            URL imgUrl = Icons.class.getResource(ICON_PATH + filename);

            if (imgUrl == null) {
                System.err.println("[Icons] ikon tidak ditemukan: " + ICON_PATH + filename);
                return new ImageIcon(); // kosong — caller bisa menampilkan fallback text/icon
            }

            ImageIcon originalIcon = new ImageIcon(imgUrl);
            Image originalImage = originalIcon.getImage();
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            return new ImageIcon(resizedImage);
        } catch (Exception e) {
            e.printStackTrace();
            return new ImageIcon();
        }
    }

}