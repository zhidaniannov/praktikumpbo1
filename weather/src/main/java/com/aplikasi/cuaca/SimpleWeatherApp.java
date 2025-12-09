package com.aplikasi.cuaca;

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

// Import komponen UI & Ikon
import com.aplikasi.cuaca.UIComponents.*;
import com.aplikasi.cuaca.Icons.*;

/**
 * CATATAN UNTUK UAS:
 * Jika dosen meminta penamaan file spesifik, silakan Rename file ini dan Class ini
 * menjadi "WeatherMainJFrame" atau "MainJFrame".
 */
public class SimpleWeatherApp {

    // --- STATE ---
    private static List<String> searchHistory = new ArrayList<>(); 
    private static boolean isCelsius = true; 
    private static boolean useDummyData = false; // Toggle Data Real vs Dummy
    private static String currentCity = "Jakarta"; 
    
    // Daftar kota untuk Autocomplete / Suggestion
    private static final List<String> CITY_SUGGESTIONS = Arrays.asList(
        "Jakarta", "Bandung", "Surabaya", "Yogyakarta", "Semarang", 
        "Medan", "Makassar", "Denpasar", "Palembang", "Balikpapan", 
        "Malang", "Bogor", "Depok", "Bekasi", "Tangerang", "Aceh", "Papua",
        "Tokyo", "London", "New York", "Singapore"
    );

    // --- ANIMATION STATE ---
    private static float currentAlpha = 1.0f;
    private static Timer animationTimer;

    // --- UI COLORS ---
    public static Color topBackgroundColor = new Color(74, 144, 226);
    public static Color bottomBackgroundColor = new Color(0, 82, 212);
    private static final Color ACCENT_COLOR = new Color(52, 152, 219); 
    
    // --- UI COMPONENTS ---
    private static JPanel mainPanel;
    private static JPanel bodyPanel;
    private static JLabel timeLabel;
    private static JLabel dateLabel;
    private static ChartPanel chartPanel;
    private static JFrame mainFrame; // Bisa diubah jadi MainJFrame
    private static RoundedTextField citySearchField;
    private static JPopupMenu suggestionPopupMenu;
    
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));

    // Komponen UI Global
    private static JLabel cityNameLabel, currentTempLabel, weatherConditionLabel, weatherDescriptionLabel, recommendationLabel, humidityLabel, weatherIconLabel;
    private static JLabel forecastDay1Label, forecastIcon1Label, forecastTemp1Label, forecastDay2Label, forecastIcon2Label, forecastTemp2Label;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        showSplashScreen();
    }

    private static void showSplashScreen() {
        JWindow splashWindow = new JWindow();
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(74, 144, 226));
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        JLabel titleLabel = new JLabel("Weather App UAS", SwingConstants.CENTER);
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
        mainFrame = new JFrame("Info Cuaca - Project UAS");
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
        topPanel.setBorder(new EmptyBorder(30, 40, 10, 40));

        JPanel searchContainerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        searchContainerPanel.setOpaque(false);

        // SETUP SEARCH FIELD & SUGGESTIONS
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

        // Key Listener untuk Autocomplete
        citySearchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = citySearchField.getText().trim();
                if (text.length() > 0 && !text.equals("Cari kota...")) {
                    showSuggestions(text);
                } else {
                    suggestionPopupMenu.setVisible(false);
                }
                
                // Jika tekan enter, sembunyikan popup
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    suggestionPopupMenu.setVisible(false);
                }
            }
        });

        ModernButton searchButton = new ModernButton(new Icons.SearchIcon(22, Color.WHITE), new Color(255, 255, 255, 40), new Color(255,255,255,80));
        searchButton.setToolTipText("Cari Data Cuaca");

        ModernButton settingsButton = new ModernButton(new Icons.SettingsIcon(24, Color.WHITE), new Color(255, 255, 255, 0), new Color(255,255,255,40));
        settingsButton.setToolTipText("Pengaturan Aplikasi");

        searchContainerPanel.add(citySearchField);
        searchContainerPanel.add(searchButton);
        searchContainerPanel.add(settingsButton);

        // Panel Kanan (Jam & Tanggal)
        JPanel timePanel = new JPanel(new GridLayout(2, 1));
        timePanel.setOpaque(false);

        timeLabel = new JLabel(timeFormat.format(new Date()));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        dateLabel = new JLabel(dateFormat.format(new Date()));
        dateLabel.setForeground(new Color(230, 230, 230));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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

        cityNameLabel = WeatherUtils.createShadowLabel(currentCity, new Font("Segoe UI", Font.BOLD, 46));
        
        weatherIconLabel = new JLabel("☁️");
        weatherIconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 160));
        weatherIconLabel.setForeground(Color.WHITE);
        weatherIconLabel.setBorder(new EmptyBorder(20, 0, 10, 0));

        currentTempLabel = WeatherUtils.createShadowLabel("--°C", new Font("Segoe UI", Font.BOLD, 90));
        weatherConditionLabel = WeatherUtils.createShadowLabel("--", new Font("Segoe UI", Font.BOLD, 32));

        weatherDescriptionLabel = new JLabel("--");
        weatherDescriptionLabel.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        weatherDescriptionLabel.setForeground(new Color(240, 240, 240));
        
        JPanel detailsPanel = new GlassPanel(20);
        detailsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        detailsPanel.setPreferredSize(new Dimension(300, 50));
        
        humidityLabel = new JLabel("💧 -%");
        humidityLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        humidityLabel.setForeground(Color.WHITE);
        
        JLabel windLabel = new JLabel("🌬️ - km/h"); 
        windLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        windLabel.setForeground(Color.WHITE);
        
        detailsPanel.add(humidityLabel);
        detailsPanel.add(new JSeparator(JSeparator.VERTICAL));
        detailsPanel.add(windLabel);

        recommendationLabel = new JLabel("<html><center>-</center></html>");
        recommendationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        recommendationLabel.setForeground(new Color(255, 255, 255, 220)); 
        recommendationLabel.setPreferredSize(new Dimension(700, 45));
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
        bottomContainerPanel.setBorder(new EmptyBorder(10, 60, 50, 60));

        JLabel chartTitleLabel = new JLabel("Perkembangan Suhu (Hari Ini)");
        chartTitleLabel.setForeground(new Color(255, 255, 255, 200));
        chartTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        chartTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        chartPanel = new ChartPanel();
        chartPanel.setPreferredSize(new Dimension(800, 180));
        chartPanel.setMaximumSize(new Dimension(1100, 180));
        chartPanel.setOpaque(false);
        chartPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel forecastPanel = new JPanel(new GridLayout(1, 2, 40, 0));
        forecastPanel.setOpaque(false);
        forecastPanel.setMaximumSize(new Dimension(900, 140));

        forecastDay1Label = new JLabel("Besok"); forecastIcon1Label = new JLabel("☁️"); forecastTemp1Label = new JLabel("-");
        JPanel forecastCard1 = createForecastCard(forecastDay1Label, forecastIcon1Label, forecastTemp1Label);
        
        forecastDay2Label = new JLabel("Lusa"); forecastIcon2Label = new JLabel("☁️"); forecastTemp2Label = new JLabel("-");
        JPanel forecastCard2 = createForecastCard(forecastDay2Label, forecastIcon2Label, forecastTemp2Label);

        forecastPanel.add(forecastCard1);
        forecastPanel.add(forecastCard2);

        bottomContainerPanel.add(chartTitleLabel);
        bottomContainerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        bottomContainerPanel.add(chartPanel);
        bottomContainerPanel.add(Box.createRigidArea(new Dimension(0, 35)));
        bottomContainerPanel.add(forecastPanel);

        bodyPanel.add(centerContainerPanel, BorderLayout.CENTER);
        bodyPanel.add(bottomContainerPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(bodyPanel, BorderLayout.CENTER); 
        
        // --- LOGIC SEARCH ---
        
        ActionListener searchAction = e -> {
            if (suggestionPopupMenu != null) suggestionPopupMenu.setVisible(false);
            String q = citySearchField.getText().trim();
            if (q.equals("Cari kota...") || q.isEmpty()) return;

            mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            citySearchField.setEnabled(false);

            new Thread(() -> {
                Map<String, String> data;
                
                if (useDummyData) {
                    // MENGGUNAKAN DUMMY DATA (Tanpa Internet)
                    try { Thread.sleep(800); } catch (Exception ex) {} // Simulasi delay
                    data = getDummyWeatherData(q);
                } else {
                    // MENGGUNAKAN REAL API
                    data = WeatherAPIService.getWeatherFromAPI(q);
                }
                
                SwingUtilities.invokeLater(() -> {
                    mainFrame.setCursor(Cursor.getDefaultCursor());
                    citySearchField.setEnabled(true);
                    citySearchField.setText("");
                    citySearchField.requestFocus();

                    if (data != null && !data.containsKey("error")) {
                        currentCity = data.get("kota");
                        updateUIWithData(data);
                        if(!searchHistory.contains(currentCity)) searchHistory.add(currentCity);
                    } else {
                        String errorMsg = (data != null && data.containsKey("error")) 
                                        ? data.get("error") 
                                        : "Koneksi Gagal / Kota Tidak Ditemukan";
                                        
                        JOptionPane.showMessageDialog(mainFrame, 
                            "Gagal mengambil data untuk: " + q + "\n\nPenyebab: " + errorMsg + "\n\nSolusi: Cek API Key atau ganti ke Mode Dummy di Pengaturan.", 
                            "Gagal Memuat Data", JOptionPane.ERROR_MESSAGE);
                    }
                });
            }).start();
        };

        searchButton.addActionListener(searchAction);
        citySearchField.addActionListener(searchAction);
        
        // Membuka Settings Dialog
        settingsButton.addActionListener(e -> {
            SettingsDialog settings = new SettingsDialog(mainFrame, () -> {
                citySearchField.setText(currentCity);
                searchAction.actionPerformed(null);
            });
            settings.setVisible(true);
        });

        // Load Default City saat start
        SwingUtilities.invokeLater(() -> {
            citySearchField.setText("Jakarta");
            searchAction.actionPerformed(null);
        });
        
        mainFrame.setContentPane(mainPanel);
        mainFrame.setVisible(true);
    }
    
    // --- HELPER UNTUK SEARCH SUGGESTION ---
    private static void showSuggestions(String text) {
        suggestionPopupMenu.removeAll();
        String query = text.toLowerCase();
        
        // Filter kota yang cocok
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

    // --- LOGIC DUMMY DATA (Untuk Keperluan Presentasi/Offline) ---
    private static Map<String, String> getDummyWeatherData(String city) {
        Map<String, String> data = new HashMap<>();
        Random rand = new Random();
        
        // Buat data acak agar terlihat dinamis
        int baseTemp = 28 + rand.nextInt(5);
        String[] conditions = {"Cerah", "Berawan", "Hujan", "Mendung"};
        String condition = conditions[rand.nextInt(conditions.length)];
        
        data.put("kota", city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase() + " (Dummy)");
        data.put("suhu", baseTemp + "°C");
        data.put("cuaca", condition);
        data.put("deskripsi", "Simulasi data cuaca offline");
        data.put("kelembaban", (60 + rand.nextInt(30)) + "%");
        data.put("rekomendasi", "Ini adalah data dummy untuk presentasi UAS.");
        
        // Data Forecast Dummy
        data.put("f1_hari", "Besok");
        data.put("f1_suhu", (baseTemp-1) + "°C");
        data.put("f1_cuaca", conditions[rand.nextInt(conditions.length)]);
        
        data.put("f2_hari", "Lusa");
        data.put("f2_suhu", (baseTemp+1) + "°C");
        data.put("f2_cuaca", conditions[rand.nextInt(conditions.length)]);
        
        // Hourly Data Dummy: "08:00,28,Cerah;..."
        StringBuilder hourly = new StringBuilder();
        int h = 8;
        for(int i=0; i<6; i++) {
            hourly.append(String.format("%02d:00", h)).append(",");
            hourly.append(baseTemp + rand.nextInt(3) - 1).append(",");
            hourly.append(conditions[rand.nextInt(conditions.length)]);
            if(i < 5) hourly.append(";");
            h += 2;
        }
        data.put("hourly", hourly.toString());
        
        return data;
    }

    // Update UI Helper
    private static void updateUIWithData(Map<String, String> data) {
        cityNameLabel.setText(data.get("kota"));
        
        // Konversi jika setting Fahrenheit
        String rawSuhu = data.get("suhu"); // Format "30°C"
        currentTempLabel.setText(WeatherUtils.formatTemp(rawSuhu, isCelsius)); 
        
        String weatherCond = data.get("cuaca");
        weatherConditionLabel.setText(weatherCond);
        weatherDescriptionLabel.setText(data.get("deskripsi"));
        recommendationLabel.setText("<html><center>Rekomendasi: " + data.getOrDefault("rekomendasi", "-") + "</center></html>");
        weatherIconLabel.setText(WeatherUtils.getWeatherEmoji(weatherCond));
        humidityLabel.setText("💧 " + data.getOrDefault("kelembaban", "-"));
        
        // Data Forecast Dummy
        forecastDay1Label.setText(data.getOrDefault("f1_hari", "Besok"));
        forecastTemp1Label.setText(WeatherUtils.formatTemp(data.getOrDefault("f1_suhu", "-"), isCelsius));
        forecastIcon1Label.setText(WeatherUtils.getWeatherEmoji(data.getOrDefault("f1_cuaca", "")));
        
        forecastDay2Label.setText(data.getOrDefault("f2_hari", "Lusa"));
        forecastTemp2Label.setText(WeatherUtils.formatTemp(data.getOrDefault("f2_suhu", "-"), isCelsius));
        forecastIcon2Label.setText(WeatherUtils.getWeatherEmoji(data.getOrDefault("f2_cuaca", "")));
        
        if (chartPanel != null) chartPanel.setData(data.get("hourly"), isCelsius);
        
        updateThemeColor(weatherCond);
        triggerAnimation();
    }

    // --- ANIMATION LOGIC ---
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
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        dayLabel.setForeground(Color.WHITE); dayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        iconLabel.setForeground(Color.WHITE); iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        
        tempLabel.setForeground(Color.WHITE); tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tempLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        
        card.add(dayLabel); card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(iconLabel); card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(tempLabel);
        return card;
    }

    // --- THEME COLOR LOGIC ---
    private static void updateThemeColor(String condition) {
        int hour = LocalTime.now().getHour();
        boolean isNight = (hour >= 18 || hour < 6);
        String c = condition.toLowerCase();
        
        if (isNight) {
            if (c.contains("hujan") || c.contains("petir")) {
                topBackgroundColor = new Color(44, 62, 80);    
                bottomBackgroundColor = new Color(0, 0, 0); 
            } else if (c.contains("cerah")) {
                topBackgroundColor = new Color(20, 30, 48);    
                bottomBackgroundColor = new Color(36, 59, 85);
            } else {
                topBackgroundColor = new Color(15, 32, 39);    
                bottomBackgroundColor = new Color(32, 58, 67);
            }
        } else {
            if (c.contains("petir") || c.contains("badai")) {
                topBackgroundColor = new Color(72, 85, 99); 
                bottomBackgroundColor = new Color(41, 50, 60); 
            } else if (c.contains("hujan")) {
                topBackgroundColor = new Color(66, 99, 132);   
                bottomBackgroundColor = new Color(30, 50, 70); 
            } else if (c.contains("cerah") && !c.contains("berawan")) {
                topBackgroundColor = new Color(86, 204, 242);  
                bottomBackgroundColor = new Color(47, 128, 237); 
            } else if (c.contains("berawan") || c.contains("mendung")) {
                topBackgroundColor = new Color(168, 192, 255); 
                bottomBackgroundColor = new Color(63, 43, 150); 
            } else if (c.contains("sejuk")) {
                topBackgroundColor = new Color(17, 153, 142);  
                bottomBackgroundColor = new Color(56, 239, 125); 
            } else {
                topBackgroundColor = new Color(74, 144, 226); 
                bottomBackgroundColor = new Color(0, 82, 212);
            }
        }
        if (mainPanel != null) mainPanel.repaint();
    }

    // --- SETTINGS DIALOG (Bisa dipisah jadi SettingsDialog.java) ---
    public static class SettingsDialog extends JDialog {
        private Runnable updateCallback;

        public SettingsDialog(JFrame parent, Runnable onSave) {
            super(parent, "Pengaturan", true);
            this.updateCallback = onSave;
            
            setSize(450, 380);
            setLocationRelativeTo(parent);
            setUndecorated(true);
            setLayout(new BorderLayout());
            
            // Background Glass Effect
            JPanel contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBackground(Color.WHITE);
            contentPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
            
            // Header
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(Color.WHITE);
            headerPanel.setBorder(new EmptyBorder(25, 30, 10, 30));
            JLabel titleLabel = new JLabel("Preferensi Aplikasi");
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
            
            JButton closeButton = new JButton("×");
            closeButton.setFont(new Font("Arial", Font.PLAIN, 28));
            closeButton.setForeground(Color.GRAY);
            closeButton.setBorderPainted(false); closeButton.setContentAreaFilled(false);
            closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            closeButton.addActionListener(ev -> dispose());
            headerPanel.add(titleLabel, BorderLayout.WEST);
            headerPanel.add(closeButton, BorderLayout.EAST);

            // Body
            JPanel bodyPanel = new JPanel();
            bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
            bodyPanel.setBackground(Color.WHITE);
            bodyPanel.setBorder(new EmptyBorder(10, 30, 30, 30));

            // Section 1: Satuan Suhu
            JLabel lblUnit = new JLabel("Satuan Suhu");
            lblUnit.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblUnit.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JPanel unitPanel = new JPanel(new GridLayout(1, 2, 10, 0));
            unitPanel.setBackground(Color.WHITE);
            unitPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            unitPanel.setMaximumSize(new Dimension(1000, 80));
            
            JToggleButton celsiusCard = createSelectionCard("Celcius", "°C", isCelsius);
            JToggleButton fahrenheitCard = createSelectionCard("Fahrenheit", "°F", !isCelsius);
            ButtonGroup unitGroup = new ButtonGroup();
            unitGroup.add(celsiusCard); unitGroup.add(fahrenheitCard);
            unitPanel.add(celsiusCard); unitPanel.add(fahrenheitCard);

            // Section 2: Sumber Data (Real vs Dummy)
            JLabel lblSource = new JLabel("Sumber Data (Mode Demo)");
            lblSource.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblSource.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JPanel sourcePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            sourcePanel.setBackground(Color.WHITE);
            sourcePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JRadioButton rbReal = new JRadioButton("Realtime API (Online)");
            JRadioButton rbDummy = new JRadioButton("Dummy Data (Offline/Demo)");
            rbReal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            rbDummy.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            rbReal.setBackground(Color.WHITE); rbDummy.setBackground(Color.WHITE);
            
            ButtonGroup sourceGroup = new ButtonGroup();
            sourceGroup.add(rbReal); sourceGroup.add(rbDummy);
            
            if (useDummyData) rbDummy.setSelected(true); else rbReal.setSelected(true);
            
            sourcePanel.add(rbReal); sourcePanel.add(Box.createHorizontalStrut(20)); sourcePanel.add(rbDummy);

            // Add to Body
            bodyPanel.add(lblUnit);
            bodyPanel.add(Box.createVerticalStrut(10));
            bodyPanel.add(unitPanel);
            bodyPanel.add(Box.createVerticalStrut(20));
            bodyPanel.add(lblSource);
            bodyPanel.add(Box.createVerticalStrut(5));
            bodyPanel.add(sourcePanel);

            // Footer
            JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            footerPanel.setBackground(Color.WHITE);
            footerPanel.setBorder(new EmptyBorder(0, 30, 25, 30));
            
            JButton saveButton = new JButton("Simpan Perubahan");
            saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
            saveButton.setForeground(Color.WHITE); saveButton.setBackground(ACCENT_COLOR); 
            saveButton.setPreferredSize(new Dimension(180, 45));
            saveButton.setBorderPainted(false); saveButton.setContentAreaFilled(false);
            saveButton.setFocusPainted(false); saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            saveButton.addActionListener(ev -> {
                // Simpan State
                isCelsius = celsiusCard.isSelected();
                useDummyData = rbDummy.isSelected();
                
                // Jalankan Callback Update UI
                updateCallback.run();
                dispose();
            });
            
            // Custom Paint Button agar rounded
            saveButton.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
                public void paint(Graphics g, JComponent c) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(c.getBackground());
                    g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 15, 15);
                    super.paint(g2, c);
                    g2.dispose();
                }
            });

            footerPanel.add(saveButton);

            add(headerPanel, BorderLayout.NORTH);
            add(bodyPanel, BorderLayout.CENTER);
            add(footerPanel, BorderLayout.SOUTH);
        }

        private JToggleButton createSelectionCard(String title, String symbol, boolean selected) {
            JToggleButton toggleButton = new JToggleButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    if (isSelected()) {
                        g2.setColor(new Color(235, 245, 255));
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                        g2.setColor(ACCENT_COLOR);
                        g2.setStroke(new BasicStroke(2));
                        g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 15, 15);
                    } else {
                        g2.setColor(new Color(250, 250, 250));
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                        g2.setColor(new Color(220, 220, 220));
                        g2.setStroke(new BasicStroke(1));
                        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
                    }
                    
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 28));
                    g2.setColor(isSelected() ? ACCENT_COLOR : Color.GRAY);
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(symbol, (getWidth() - fm.stringWidth(symbol))/2, getHeight()/2 + 5);
                    
                    if(isSelected()) {
                        Icon checkIcon = new Icons.CheckIcon(16, ACCENT_COLOR);
                        checkIcon.paintIcon(this, g2, getWidth() - 25, 10);
                    }
                    
                    g2.dispose();
                }
            };
            toggleButton.setSelected(selected);
            toggleButton.setFocusPainted(false);
            toggleButton.setBorderPainted(false);
            toggleButton.setContentAreaFilled(false);
            toggleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            return toggleButton;
        }
    }
}