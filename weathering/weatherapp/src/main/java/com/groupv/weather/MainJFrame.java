package com.groupv.weather;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.time.LocalTime;
import javax.swing.Timer;

import com.groupv.weather.UIComponents.*;

public class MainJFrame extends JFrame {

    // --- STATE ---
    private boolean isCelsius = true; 
    private boolean useDummyData = false; 
    private String currentCity = "Jakarta"; 
    
    // Data untuk Autocomplete
    private final List<String> citySuggestions = Arrays.asList(
        "Jakarta", "Bandung", "Surabaya", "Yogyakarta", "Semarang", 
        "Medan", "Makassar", "Denpasar", "Palembang", "Balikpapan", 
        "Malang", "Bogor", "Depok", "Bekasi", "Tangerang", "Aceh", "Jayapura",
        "Tokyo", "London", "New York", "Singapore", "Seoul"
    );

    // --- UI COMPONENTS ---
    private JPanel mainPanel;
    private JPanel bodyPanel;
    
    // Header Components
    private JLabel timeLabel, dateLabel, headerCityLabel; 
    
    private WeatherChartPanel chartPanel; 
    private RoundedTextField citySearchField;
    private JPopupMenu suggestionPopupMenu;
    
    // Label Output Body
    private JLabel cityNameLabel, currentTempLabel, weatherConditionLabel, weatherDescriptionLabel, recommendationLabel, humidityLabel, weatherIconLabel;
    private JLabel forecastDay1Label, forecastIcon1Label, forecastTemp1Label, forecastDay2Label, forecastIcon2Label, forecastTemp2Label;
    
    // Label Judul Grafik
    private JLabel chartTitleLabel;

    // Colors & Formats
    public Color topBackgroundColor = new Color(74, 144, 226);
    public Color bottomBackgroundColor = new Color(0, 82, 212);
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
    private float currentAlpha = 1.0f;
    private Timer animationTimer;

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new MainJFrame().setVisible(true));
    }

    public MainJFrame() {
        initComponents();
        startClock();
        loadDefaultWeather();
    }

    private void initComponents() {
        setTitle("Weather App UAS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

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

        setupTopPanel();
        setupBodyPanel();

        setContentPane(mainPanel);
    }

    private void setupTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(30, 40, 10, 40));

        // --- SEARCH SECTION (KIRI) ---
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
        suggestionPopupMenu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        citySearchField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (citySearchField.getText().equals("Cari kota...")) citySearchField.setText("");
            }
            public void focusLost(FocusEvent e) {
                if (citySearchField.getText().isEmpty()) citySearchField.setText("Cari kota...");
            }
        });
        
        citySearchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    suggestionPopupMenu.setVisible(false);
                    performSearch();
                } else {
                    showSearchSuggestions(citySearchField.getText());
                }
            }
        });

        ModernButton searchButton = new ModernButton(Icons.getSearchIcon(24), new Color(255,255,255,40), new Color(255,255,255,80));
        searchButton.addActionListener(e -> performSearch());

        ModernButton settingsButton = new ModernButton(Icons.getSettingsIcon(26), new Color(255,255,255,0), new Color(255,255,255,40));
        
        settingsButton.addActionListener(e -> {
            SettingsDialog dialog = new SettingsDialog(this, isCelsius, useDummyData);
            dialog.setVisible(true);
            
            if (dialog.isSaved()) {
                boolean prevCelsius = isCelsius;
                boolean prevDummy = useDummyData;
                
                this.isCelsius = dialog.isCelsiusSelected();
                this.useDummyData = dialog.isDummyDataSelected();
                
                if (prevCelsius != isCelsius || prevDummy != useDummyData) {
                    performSearch(); 
                }
            }
        });

        searchContainerPanel.add(citySearchField);
        searchContainerPanel.add(searchButton);
        searchContainerPanel.add(settingsButton);

        // --- INFO SECTION (KANAN: Kota, Jam, Tanggal) ---
        JPanel rightInfoPanel = new JPanel(new GridLayout(3, 1));
        rightInfoPanel.setOpaque(false);
        
        headerCityLabel = new JLabel(currentCity);
        headerCityLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerCityLabel.setForeground(new Color(255, 255, 255, 220));
        headerCityLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        timeLabel = new JLabel(); 
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28)); 
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        dateLabel = new JLabel(); 
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14)); 
        dateLabel.setForeground(new Color(230,230,230));
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        rightInfoPanel.add(headerCityLabel);
        rightInfoPanel.add(timeLabel);
        rightInfoPanel.add(dateLabel);
        
        topPanel.add(searchContainerPanel, BorderLayout.WEST);
        topPanel.add(rightInfoPanel, BorderLayout.EAST);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
    }

    private void setupBodyPanel() {
        bodyPanel = new JPanel(new BorderLayout()) {
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currentAlpha));
                super.paint(g2);
                g2.dispose();
            }
        };
        bodyPanel.setOpaque(false);

        // Center Info
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = GridBagConstraints.RELATIVE; gbc.insets = new Insets(5,0,5,0);

        cityNameLabel = WeatherUtils.createShadowLabel("-", new Font("Segoe UI", Font.BOLD, 46));
        
        // --- PERUBAHAN ICON UTAMA DI SINI ---
        weatherIconLabel = new JLabel(); 
        weatherIconLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        // Kita hapus setFont emoji karena akan diganti image
        
        currentTempLabel = WeatherUtils.createShadowLabel("-", new Font("Segoe UI", Font.BOLD, 90));
        weatherConditionLabel = WeatherUtils.createShadowLabel("-", new Font("Segoe UI", Font.BOLD, 32));
        
        JPanel detailBox = new GlassPanel(20);
        detailBox.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 12)); 
        detailBox.setPreferredSize(new Dimension(380, 60)); 
        
        humidityLabel = new JLabel("💧 -%"); 
        humidityLabel.setForeground(Color.WHITE); 
        humidityLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JLabel windLabel = new JLabel("🌬️ - km/h"); 
        windLabel.setForeground(Color.WHITE); 
        windLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JSeparator sep = new JSeparator(JSeparator.VERTICAL);
        sep.setPreferredSize(new Dimension(2, 25)); 

        detailBox.add(humidityLabel); 
        detailBox.add(sep); 
        detailBox.add(windLabel);

        weatherDescriptionLabel = new JLabel("-"); weatherDescriptionLabel.setForeground(Color.WHITE); weatherDescriptionLabel.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        recommendationLabel = new JLabel("-"); recommendationLabel.setForeground(Color.WHITE); recommendationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        centerPanel.add(cityNameLabel, gbc);
        centerPanel.add(weatherIconLabel, gbc);
        centerPanel.add(currentTempLabel, gbc);
        centerPanel.add(weatherConditionLabel, gbc);
        gbc.insets = new Insets(15,0,15,0); centerPanel.add(detailBox, gbc);
        gbc.insets = new Insets(5,0,5,0); centerPanel.add(weatherDescriptionLabel, gbc); centerPanel.add(recommendationLabel, gbc);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(new EmptyBorder(10, 60, 50, 60));

        chartTitleLabel = new JLabel("Perkembangan Suhu");
        chartTitleLabel.setForeground(new Color(255, 255, 255, 200));
        chartTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        chartTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        chartPanel = new WeatherChartPanel();
        chartPanel.setPreferredSize(new Dimension(800, 180));
        chartPanel.setMaximumSize(new Dimension(1100, 180));
        chartPanel.setOpaque(false);
        chartPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel forecastContainer = new JPanel(new GridLayout(1, 2, 40, 0));
        forecastContainer.setOpaque(false);
        forecastContainer.setMaximumSize(new Dimension(900, 140));
        
        // --- PERUBAHAN ICON FORECAST DI SINI ---
        forecastDay1Label = new JLabel("-"); forecastIcon1Label = new JLabel(); forecastTemp1Label = new JLabel("-");
        forecastDay2Label = new JLabel("-"); forecastIcon2Label = new JLabel(); forecastTemp2Label = new JLabel("-");
        
        forecastContainer.add(createForecastCard(forecastDay1Label, forecastIcon1Label, forecastTemp1Label));
        forecastContainer.add(createForecastCard(forecastDay2Label, forecastIcon2Label, forecastTemp2Label));

        bottomPanel.add(chartTitleLabel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        bottomPanel.add(chartPanel);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        bottomPanel.add(forecastContainer);

        bodyPanel.add(centerPanel, BorderLayout.CENTER);
        bodyPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
    }

    // --- LOGIC METHODS ---

    private void performSearch() {
        String q = citySearchField.getText().trim();
        if (q.isEmpty() || q.equals("Cari kota...")) {
            if (currentCity != null && !currentCity.isEmpty()) {
                q = currentCity;
            } else {
                return;
            }
        }
        
        final String query = q; 
        
        suggestionPopupMenu.setVisible(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        citySearchField.setEnabled(false);

        new Thread(() -> {
            Map<String, String> data = WeatherAPIService.getWeatherData(query, useDummyData);
            
            SwingUtilities.invokeLater(() -> {
                setCursor(Cursor.getDefaultCursor());
                citySearchField.setEnabled(true);
                citySearchField.setText("Cari kota...");
                mainPanel.requestFocusInWindow(); 

                if (data != null && !data.containsKey("error")) {
                    updateUI(data);
                } else {
                    String msg = data != null ? data.get("error") : "Unknown Error";
                    JOptionPane.showMessageDialog(this, "Gagal: " + msg, "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }).start();
    }

    private void updateUI(Map<String, String> data) {
        currentCity = data.get("kota");
        cityNameLabel.setText(currentCity);
        
        if (headerCityLabel != null) {
            headerCityLabel.setText(currentCity);
        }

        if (chartTitleLabel != null) {
            String cleanName = currentCity.contains("(") ? currentCity.split("\\(")[0].trim() : currentCity;
            chartTitleLabel.setText("Perkembangan Suhu di " + cleanName + " (24 Jam)");
        }

        currentTempLabel.setText(WeatherUtils.formatTemp(data.get("suhu"), isCelsius));
        weatherConditionLabel.setText(data.get("cuaca"));
        weatherDescriptionLabel.setText(data.get("deskripsi"));
        recommendationLabel.setText(data.get("rekomendasi"));
        
        // --- MENGGUNAKAN GAMBAR ICON SEKARANG ---
        // Ukuran besar untuk ikon utama (160px)
        weatherIconLabel.setIcon(WeatherUtils.getWeatherIcon(data.get("cuaca"), 160));
        weatherIconLabel.setText(""); // Hapus emoji teks jika ada

        humidityLabel.setText("💧 " + data.get("kelembaban"));

        forecastDay1Label.setText(data.get("f1_hari"));
        forecastTemp1Label.setText(WeatherUtils.formatTemp(data.get("f1_suhu"), isCelsius));
        
        // Ukuran kecil untuk ikon forecast (60px)
        forecastIcon1Label.setIcon(WeatherUtils.getWeatherIcon(data.get("f1_cuaca"), 60));
        forecastIcon1Label.setText("");
        
        forecastDay2Label.setText(data.get("f2_hari"));
        forecastTemp2Label.setText(WeatherUtils.formatTemp(data.get("f2_suhu"), isCelsius));
        
        // Ukuran kecil untuk ikon forecast (60px)
        forecastIcon2Label.setIcon(WeatherUtils.getWeatherIcon(data.get("f2_cuaca"), 60));
        forecastIcon2Label.setText("");

        chartPanel.setData(data.get("hourly"), isCelsius);
        updateTheme(data.get("cuaca"));
        triggerFadeAnimation();
    }

    private void showSearchSuggestions(String text) {
        if (text.isEmpty() || text.equals("Cari kota...")) {
            suggestionPopupMenu.setVisible(false);
            return;
        }
        
        suggestionPopupMenu.removeAll();
        int count = 0;
        for (String city : citySuggestions) {
            if (city.toLowerCase().contains(text.toLowerCase())) {
                JMenuItem item = new JMenuItem(city);
                item.setBackground(Color.WHITE);
                item.addActionListener(e -> {
                    citySearchField.setText(city);
                    suggestionPopupMenu.setVisible(false);
                    performSearch();
                });
                suggestionPopupMenu.add(item);
                count++;
            }
        }
        
        if (count > 0) {
            suggestionPopupMenu.show(citySearchField, 0, citySearchField.getHeight());
            citySearchField.requestFocus();
        } else {
            suggestionPopupMenu.setVisible(false);
        }
    }

    private void startClock() {
        new Timer(1000, e -> {
            Date now = new Date();
            timeLabel.setText(timeFormat.format(now));
            dateLabel.setText(dateFormat.format(now));
        }).start();
    }
    
    private void loadDefaultWeather() {
        SwingUtilities.invokeLater(() -> {
            citySearchField.setText("Jakarta");
            performSearch();
        });
    }
    
    private void triggerFadeAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) animationTimer.stop();
        currentAlpha = 0.0f;
        bodyPanel.repaint();
        animationTimer = new Timer(25, e -> {
            currentAlpha += 0.05f;
            if (currentAlpha >= 1.0f) { currentAlpha = 1.0f; ((Timer)e.getSource()).stop(); }
            bodyPanel.repaint();
        });
        animationTimer.start();
    }

    private void updateTheme(String condition) {
        String c = condition.toLowerCase();
        int hour = LocalTime.now().getHour();
        boolean isNight = hour >= 18 || hour < 6;

        if (isNight) {
            topBackgroundColor = new Color(20, 30, 48); bottomBackgroundColor = new Color(36, 59, 85);
        } else if (c.contains("hujan")) {
            topBackgroundColor = new Color(66, 99, 132); bottomBackgroundColor = new Color(30, 50, 70);
        } else if (c.contains("cerah")) {
            topBackgroundColor = new Color(86, 204, 242); bottomBackgroundColor = new Color(47, 128, 237);
        } else {
            topBackgroundColor = new Color(74, 144, 226); bottomBackgroundColor = new Color(0, 82, 212);
        }
        mainPanel.repaint();
    }

    private JPanel createForecastCard(JLabel day, JLabel icon, JLabel temp) {
        JPanel card = new GlassPanel(20);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        day.setForeground(Color.WHITE); day.setAlignmentX(CENTER_ALIGNMENT); day.setFont(new Font("Segoe UI", Font.BOLD, 16));
        icon.setForeground(Color.WHITE); icon.setAlignmentX(CENTER_ALIGNMENT); 
        // Font tidak lagi diperlukan untuk icon karena menggunakan gambar
        
        temp.setForeground(Color.WHITE); temp.setAlignmentX(CENTER_ALIGNMENT); temp.setFont(new Font("Segoe UI", Font.BOLD, 20));
        
        card.add(day); card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(icon); card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(temp);
        return card;
    }
}