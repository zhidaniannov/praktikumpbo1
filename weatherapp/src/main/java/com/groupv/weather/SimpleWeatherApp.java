package com.groupv.weather;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.Timer; 

import com.groupv.weather.UIComponents.*;

public class SimpleWeatherApp {

    // --- STATE ---
    private static List<String> searchHistory = new ArrayList<>(); 
    private static boolean isCelsius = true; 
    private static boolean useDummyData = false; 
    private static String currentCity = "Jakarta"; 
    
    private static final List<String> CITY_SUGGESTIONS = Arrays.asList(
        "Jakarta", "Bandung", "Surabaya", "Yogyakarta", "Semarang", 
        "Medan", "Makassar", "Denpasar", "Palembang", "Balikpapan", 
        "Malang", "Bogor", "Depok", "Bekasi", "Tangerang", "Aceh", "Papua",
        "Tokyo", "London", "New York", "Singapore", "Pekanbaru"
    );

    // --- ANIMATION STATE ---
    private static float currentAlpha = 1.0f;
    private static Timer animationTimer;

    // --- UI COLORS ---
    public static Color topBackgroundColor = new Color(74, 144, 226);
    public static Color bottomBackgroundColor = new Color(0, 82, 212);
    
    // --- UI COMPONENTS ---
    private static JPanel mainPanel;
    private static JPanel bodyPanel;
    private static JLabel timeLabel;
    private static JLabel dateLabel;
    private static WeatherChartPanel chartPanel; 
    private static JFrame mainFrame; 
    private static RoundedTextField citySearchField;
    private static JPopupMenu suggestionPopupMenu;
    
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));

    // Komponen UI Global
    private static JLabel cityNameLabel, currentTempLabel, weatherConditionLabel, weatherDescriptionLabel, recommendationLabel, humidityLabel, weatherIconLabel;
    private static JLabel forecastDay1Label, forecastIcon1Label, forecastTemp1Label, forecastDay2Label, forecastIcon2Label, forecastTemp2Label;
    
    // Label Judul Grafik
    private static JLabel chartTitleLabel;

    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        showSplashScreen();
    }

    private static void showSplashScreen() {
        JWindow splashWindow = new JWindow();
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(74, 144, 226));
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        JLabel titleLabel = new JLabel("Weathering", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(30, 20, 10, 20));

        JLabel subtitleLabel = new JLabel("Memuat Komponen...", SwingConstants.CENTER);
        subtitleLabel.setForeground(new Color(230, 230, 230));

        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setBorder(new EmptyBorder(0, 60, 40, 60));
        progressBar.setBackground(new Color(74, 144, 226));
        progressBar.setForeground(Color.WHITE);

        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(subtitleLabel, BorderLayout.CENTER);
        contentPanel.add(progressBar, BorderLayout.SOUTH);

        splashWindow.setContentPane(contentPanel);
        splashWindow.setSize(500, 300);
        splashWindow.setLocationRelativeTo(null);
        splashWindow.setVisible(true);

        new Thread(() -> {
            try {
                Thread.sleep(1500); 
                SwingUtilities.invokeLater(() -> {
                    splashWindow.dispose();
                    createAndShowGUI();
                });
            } catch (Exception e) { e.printStackTrace(); }
        }).start();
    }

    private static void createAndShowGUI() {
        mainFrame = new JFrame("Info Cuaca - Weathering");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, topBackgroundColor, 0, getHeight(), bottomBackgroundColor);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // --- TOP BAR ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(35, 50, 10, 50));

        JPanel searchContainerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        searchContainerPanel.setOpaque(false);

        citySearchField = new RoundedTextField(25);
        citySearchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        citySearchField.setText("Cari kota...");
        citySearchField.setForeground(Color.WHITE);
        citySearchField.setCaretColor(Color.WHITE);
        citySearchField.setPreferredSize(new Dimension(320, 50));
        
        suggestionPopupMenu = new JPopupMenu();
        suggestionPopupMenu.setBackground(Color.WHITE);
        suggestionPopupMenu.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));

        citySearchField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (citySearchField.getText().equals("Cari kota...")) citySearchField.setText("");
            }
            public void focusLost(FocusEvent e) {
                if (citySearchField.getText().isEmpty()) citySearchField.setText("Cari kota...");
            }
        });

        citySearchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = citySearchField.getText().trim();
                if (text.length() > 0 && !text.equals("Cari kota...")) {
                    showSuggestions(text);
                } else {
                    suggestionPopupMenu.setVisible(false);
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    suggestionPopupMenu.setVisible(false);
                }
            }
        });

        ModernButton searchButton = new ModernButton(Icons.getSearchIcon(24), new Color(255, 255, 255, 40), new Color(255,255,255,80));
        searchButton.setToolTipText("Cari Data Cuaca");

        ModernButton settingsButton = new ModernButton(Icons.getSettingsIcon(26), new Color(255, 255, 255, 0), new Color(255,255,255,40));
        settingsButton.setToolTipText("Pengaturan Aplikasi");

        searchContainerPanel.add(citySearchField);
        searchContainerPanel.add(searchButton);
        searchContainerPanel.add(settingsButton);

        JPanel timePanel = new JPanel(new GridLayout(2, 1));
        timePanel.setOpaque(false);

        timeLabel = new JLabel(timeFormat.format(new Date()));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        dateLabel = new JLabel(dateFormat.format(new Date()));
        dateLabel.setForeground(new Color(230, 230, 230));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        timePanel.add(timeLabel);
        timePanel.add(dateLabel);

        Timer clockTimer = new Timer(1000, e -> {
            Date now = new Date();
            timeLabel.setText(timeFormat.format(now));
            dateLabel.setText(dateFormat.format(now));
        });
        clockTimer.start();

        topPanel.add(searchContainerPanel, BorderLayout.WEST);
        topPanel.add(timePanel, BorderLayout.EAST);

        // --- BODY PANEL ---
        bodyPanel = new JPanel(new BorderLayout()) {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currentAlpha));
                super.paint(g2);
                g2.dispose();
            }
        };
        bodyPanel.setOpaque(false);

        // --- CENTER INFO ---
        JPanel centerContainerPanel = new JPanel(new GridBagLayout());
        centerContainerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Nama Kota - Ukuran optimal untuk 1080p
        cityNameLabel = WeatherUtils.createShadowLabel(currentCity, new Font("Segoe UI", Font.BOLD, 48));
        
        // Ikon Cuaca - PERBAIKAN: Ukuran optimal 128px (bukan 150px)
        weatherIconLabel = new JLabel();
        weatherIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        weatherIconLabel.setBorder(new EmptyBorder(15, 0, 10, 0));

        // Suhu
        currentTempLabel = WeatherUtils.createShadowLabel("--°C", new Font("Segoe UI", Font.BOLD, 96));
        
        // Kondisi
        weatherConditionLabel = WeatherUtils.createShadowLabel("--", new Font("Segoe UI", Font.BOLD, 28));

        weatherDescriptionLabel = new JLabel("--");
        weatherDescriptionLabel.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        weatherDescriptionLabel.setForeground(new Color(240, 240, 240));
        weatherDescriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Box Humidity
        JPanel detailsPanel = new GlassPanel(20);
        detailsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 12));
        detailsPanel.setPreferredSize(new Dimension(240, 65));
        
        humidityLabel = new JLabel(" -%");
        humidityLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        humidityLabel.setForeground(Color.WHITE);
        humidityLabel.setIcon(Icons.getHumidityIcon(28));
        
        detailsPanel.add(humidityLabel);

        recommendationLabel = new JLabel("<html><center>-</center></html>");
        recommendationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        recommendationLabel.setForeground(new Color(255, 255, 255, 220));
        recommendationLabel.setPreferredSize(new Dimension(800, 40));
        recommendationLabel.setHorizontalAlignment(SwingConstants.CENTER);

        centerContainerPanel.add(cityNameLabel, gbc);
        centerContainerPanel.add(weatherIconLabel, gbc);
        centerContainerPanel.add(currentTempLabel, gbc);
        centerContainerPanel.add(weatherConditionLabel, gbc);
        
        gbc.insets = new Insets(15, 0, 15, 0);
        centerContainerPanel.add(detailsPanel, gbc);
        
        gbc.insets = new Insets(5, 0, 5, 0);
        centerContainerPanel.add(weatherDescriptionLabel, gbc);
        centerContainerPanel.add(recommendationLabel, gbc);

        // --- BOTTOM ---
        JPanel bottomContainerPanel = new JPanel();
        bottomContainerPanel.setLayout(new BoxLayout(bottomContainerPanel, BoxLayout.Y_AXIS));
        bottomContainerPanel.setOpaque(false);
        bottomContainerPanel.setBorder(new EmptyBorder(10, 80, 40, 80));

        chartTitleLabel = new JLabel("Perkembangan Suhu");
        chartTitleLabel.setForeground(new Color(255, 255, 255, 200));
        chartTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        chartTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        chartPanel = new WeatherChartPanel();
        chartPanel.setPreferredSize(new Dimension(1000, 210));
        chartPanel.setMaximumSize(new Dimension(1400, 210));
        chartPanel.setOpaque(false);
        chartPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel forecastPanel = new JPanel(new GridLayout(1, 2, 60, 0));
        forecastPanel.setOpaque(false);
        forecastPanel.setMaximumSize(new Dimension(1000, 240));

        forecastDay1Label = new JLabel("Besok");
        forecastIcon1Label = new JLabel();
        forecastIcon1Label.setHorizontalAlignment(SwingConstants.CENTER);
        forecastTemp1Label = new JLabel("-");
        JPanel forecastCard1 = createForecastCard(forecastDay1Label, forecastIcon1Label, forecastTemp1Label);
        
        forecastDay2Label = new JLabel("Lusa");
        forecastIcon2Label = new JLabel();
        forecastIcon2Label.setHorizontalAlignment(SwingConstants.CENTER);
        forecastTemp2Label = new JLabel("-");
        JPanel forecastCard2 = createForecastCard(forecastDay2Label, forecastIcon2Label, forecastTemp2Label);

        forecastPanel.add(forecastCard1);
        forecastPanel.add(forecastCard2);

        bottomContainerPanel.add(chartTitleLabel);
        bottomContainerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        bottomContainerPanel.add(chartPanel);
        bottomContainerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        bottomContainerPanel.add(forecastPanel);

        bodyPanel.add(centerContainerPanel, BorderLayout.CENTER);
        bodyPanel.add(bottomContainerPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        
        // --- LOGIC SEARCH ---
        ActionListener searchAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (suggestionPopupMenu != null) suggestionPopupMenu.setVisible(false);
                
                String q = citySearchField.getText().trim();
                if (q.equals("Cari kota...") || q.isEmpty()) {
                    if (currentCity != null && !currentCity.isEmpty()) {
                        q = currentCity;
                    } else {
                        return;
                    }
                }

                final String query = q;
                
                mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                citySearchField.setEnabled(false);

                new Thread(() -> {
                    Map<String, String> data = WeatherAPIService.getWeatherData(query, useDummyData);
                    
                    SwingUtilities.invokeLater(() -> {
                        mainFrame.setCursor(Cursor.getDefaultCursor());
                        citySearchField.setEnabled(true);
                        
                        if (!citySearchField.getText().equals(query)) {
                             citySearchField.setText("");
                             citySearchField.requestFocus();
                        }

                        if (data != null && !data.containsKey("error")) {
                            currentCity = data.get("kota");
                            updateUIWithData(data);
                            if(!searchHistory.contains(currentCity)) searchHistory.add(currentCity);
                        } else {
                            String errorMsg = (data != null && data.containsKey("error")) 
                                            ? data.get("error") 
                                            : "Koneksi Gagal / Kota Tidak Ditemukan";
                                            
                            JOptionPane.showMessageDialog(mainFrame, 
                                "Gagal mengambil data untuk: " + query + "\n\nPenyebab: " + errorMsg + "\n\nSolusi: Cek API Key atau ganti ke Mode Dummy di Pengaturan.", 
                                "Gagal Memuat Data", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                }).start();
            }
        };

        searchButton.addActionListener(searchAction);
        citySearchField.addActionListener(searchAction);
        
        // --- LOGIC SETTINGS ---
        settingsButton.addActionListener(e -> {
            SettingsDialog dialog = new SettingsDialog(mainFrame, isCelsius, useDummyData);
            dialog.setVisible(true);
            
            if (dialog.isSaved()) {
                boolean prevCelsius = isCelsius;
                boolean prevDummy = useDummyData;

                isCelsius = dialog.isCelsiusSelected();
                useDummyData = dialog.isDummyDataSelected();
                
                if (prevCelsius != isCelsius || prevDummy != useDummyData) {
                    citySearchField.setText(currentCity);
                    searchAction.actionPerformed(null);
                }
            }
        });

        SwingUtilities.invokeLater(() -> {
            citySearchField.setText("Jakarta");
            searchAction.actionPerformed(null);
        });
        
        mainFrame.setContentPane(mainPanel);
        mainFrame.setVisible(true);
    }
    
    private static void showSuggestions(String text) {
        suggestionPopupMenu.removeAll();
        String query = text.toLowerCase();
        
        List<String> matches = CITY_SUGGESTIONS.stream()
                .filter(city -> city.toLowerCase().contains(query))
                .limit(5)
                .collect(Collectors.toList());

        if (matches.isEmpty()) {
            suggestionPopupMenu.setVisible(false);
            return;
        }

        for (String city : matches) {
            JMenuItem item = new JMenuItem(city);
            item.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            item.setBackground(Color.WHITE);
            item.addActionListener(e -> {
                citySearchField.setText(city);
                suggestionPopupMenu.setVisible(false);
            });
            suggestionPopupMenu.add(item);
        }

        suggestionPopupMenu.show(citySearchField, 0, citySearchField.getHeight());
        citySearchField.requestFocus();
    }

    private static void updateUIWithData(Map<String, String> data) {
        String city = data.get("kota");
        
        // PERBAIKAN: Pastikan nama kota selalu ada
        if (city == null || city.trim().isEmpty()) {
            city = currentCity;
        }
        
        cityNameLabel.setText(city);
        
        if (chartTitleLabel != null) {
            String cleanName = city.contains("(") ? city.split("\\(")[0].trim() : city;
            chartTitleLabel.setText("Perkembangan Suhu di " + cleanName + " (Hari Ini)");
        }

        String rawSuhu = data.get("suhu");
        currentTempLabel.setText(WeatherUtils.formatTemp(rawSuhu, isCelsius));
        
        String weatherCond = data.get("cuaca");
        // PERBAIKAN: Default value jika cuaca null
        if (weatherCond == null || weatherCond.trim().isEmpty()) {
            weatherCond = "Berawan";
        }
        
        weatherConditionLabel.setText(weatherCond);
        weatherDescriptionLabel.setText(data.get("deskripsi"));
        recommendationLabel.setText("<html><center>Rekomendasi: " + data.getOrDefault("rekomendasi", "-") + "</center></html>");
        
        // PERBAIKAN UTAMA: Ikon ukuran optimal 128px dan SELALU set
        ImageIcon weatherIcon = WeatherUtils.getWeatherIcon(weatherCond, 128);
        weatherIconLabel.setIcon(weatherIcon);
        weatherIconLabel.setText(""); // Hapus teks
        
        // Debug log
        System.out.println("=== UPDATE UI ===");
        System.out.println("Kota: " + city);
        System.out.println("Cuaca: " + weatherCond);
        System.out.println("Icon loaded: " + (weatherIcon != null && weatherIcon.getIconWidth() > 0));
        
        // Humidity
        humidityLabel.setText(" " + data.getOrDefault("kelembaban", "-"));
        
        // Forecast 1
        forecastDay1Label.setText(data.getOrDefault("f1_hari", "Besok"));
        forecastTemp1Label.setText(WeatherUtils.formatTemp(data.getOrDefault("f1_suhu", "-"), isCelsius));
        ImageIcon f1Icon = WeatherUtils.getWeatherIcon(data.getOrDefault("f1_cuaca", ""), 64);
        forecastIcon1Label.setIcon(f1Icon);
        forecastIcon1Label.setText("");
        
        // Forecast 2
        forecastDay2Label.setText(data.getOrDefault("f2_hari", "Lusa"));
        forecastTemp2Label.setText(WeatherUtils.formatTemp(data.getOrDefault("f2_suhu", "-"), isCelsius));
        ImageIcon f2Icon = WeatherUtils.getWeatherIcon(data.getOrDefault("f2_cuaca", ""), 64);
        forecastIcon2Label.setIcon(f2Icon);
        forecastIcon2Label.setText("");
        
        if (chartPanel != null) chartPanel.setData(data.get("hourly"), isCelsius);
        
        updateThemeColor(weatherCond);
        triggerAnimation();
    }

    private static void triggerAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        currentAlpha = 0.0f;
        bodyPanel.repaint();

        animationTimer = new Timer(25, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentAlpha += 0.08f;
                if (currentAlpha >= 1.0f) {
                    currentAlpha = 1.0f;
                    animationTimer.stop();
                }
                bodyPanel.repaint();
            }
        });
        animationTimer.start();
    }

    private static JPanel createForecastCard(JLabel dayLabel, JLabel iconLabel, JLabel tempLabel) {
        JPanel card = new GlassPanel(25);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        dayLabel.setForeground(Color.WHITE);
        dayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        tempLabel.setForeground(Color.WHITE);
        tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tempLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        card.add(dayLabel);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(iconLabel);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(tempLabel);
        return card;
    }

    private static void updateThemeColor(String condition) {
        int hour = LocalTime.now().getHour();
        boolean isNight = (hour >= 18 || hour < 6);
        String c = condition.toLowerCase();
        
        if (c.contains("petir") || c.contains("badai") || c.contains("thunder")) {
            topBackgroundColor = new Color(48, 43, 99);
            bottomBackgroundColor = new Color(36, 36, 62);
        }
        else if (c.contains("hujan") || c.contains("rain")) {
            topBackgroundColor = new Color(66, 85, 105);
            bottomBackgroundColor = new Color(30, 45, 60);
        }
        else if (c.contains("gerimis") || c.contains("drizzle")) {
             topBackgroundColor = new Color(95, 110, 130);
             bottomBackgroundColor = new Color(60, 75, 90);
        }
        else if (c.contains("cerah") || c.contains("clear")) {
            if (isNight) {
                topBackgroundColor = new Color(20, 30, 48);
                bottomBackgroundColor = new Color(36, 59, 85);
            } else {
                topBackgroundColor = new Color(86, 204, 242);
                bottomBackgroundColor = new Color(47, 128, 237);
            }
        }
        else if (c.contains("berawan") || c.contains("mendung") || c.contains("clouds")) {
            if (isNight) {
                topBackgroundColor = new Color(50, 60, 75);
                bottomBackgroundColor = new Color(30, 40, 50);
            } else {
                topBackgroundColor = new Color(110, 130, 150);
                bottomBackgroundColor = new Color(70, 90, 110);
            }
        }
        else {
            if (isNight) {
                topBackgroundColor = new Color(20, 30, 48);
                bottomBackgroundColor = new Color(36, 59, 85);
            } else {
                topBackgroundColor = new Color(74, 144, 226);
                bottomBackgroundColor = new Color(0, 82, 212);
            }
        }
        
        if (mainPanel != null) mainPanel.repaint();
    }
}