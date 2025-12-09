package com.groupv.weather;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class SettingsDialog extends JDialog {

    private boolean saved = false;
    private boolean isCelsius;
    private boolean isDummyData;

    // Komponen UI
    private JToggleButton celsiusBtn, fahrenheitBtn;
    private JRadioButton realApiRadio, dummyDataRadio;

    // Warna Tema Gelap
    private final Color BG_COLOR = new Color(30, 35, 45, 240); // Gelap transparan
    private final Color TEXT_COLOR = new Color(240, 240, 240);
    private final Color ACCENT_COLOR = new Color(52, 152, 219); // Biru
    private final Color BUTTON_BG = new Color(255, 255, 255, 20);

    public SettingsDialog(Frame parent, boolean currentCelsius, boolean currentDummy) {
        super(parent, "Pengaturan", true);
        this.isCelsius = currentCelsius;
        this.isDummyData = currentDummy;

        setupUI();
    }

    private void setupUI() {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0)); // Transparan untuk rounded corners
        setSize(420, 450);
        setLocationRelativeTo(getParent());

        // Panel Utama dengan Rounded Corners & Background Gelap
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background Gelap
                g2.setColor(BG_COLOR);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                
                // Border Halus
                g2.setColor(new Color(255, 255, 255, 30));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);
                
                g2.dispose();
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 25, 25, 25));
        mainPanel.setOpaque(false);

        // --- HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        
        JLabel title = new JLabel("Preferensi");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(TEXT_COLOR);
        
        // Tombol Close Custom
        JButton closeBtn = new JButton("X");
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        closeBtn.setForeground(new Color(150, 150, 150));
        closeBtn.setBorderPainted(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { closeBtn.setForeground(Color.WHITE); }
            public void mouseExited(MouseEvent e) { closeBtn.setForeground(new Color(150, 150, 150)); }
        });
        closeBtn.addActionListener(e -> dispose());
        
        header.add(title, BorderLayout.WEST);
        header.add(closeBtn, BorderLayout.EAST);

        // --- BODY ---
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(20, 0, 20, 0));

        // 1. Satuan Suhu
        JLabel lblTemp = new JLabel("Satuan Suhu");
        lblTemp.setAlignmentX(LEFT_ALIGNMENT);
        lblTemp.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTemp.setForeground(new Color(200, 200, 200));
        
        JPanel tempPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        tempPanel.setOpaque(false);
        tempPanel.setAlignmentX(LEFT_ALIGNMENT);
        tempPanel.setMaximumSize(new Dimension(1000, 70)); // Tinggi tombol diperbesar
        
        celsiusBtn = createCustomToggle("Celcius", "°C", isCelsius);
        fahrenheitBtn = createCustomToggle("Fahrenheit", "°F", !isCelsius);
        
        // Logic agar tombol saling bergantian (Radio behavior manual)
        celsiusBtn.addActionListener(e -> {
            celsiusBtn.setSelected(true);
            fahrenheitBtn.setSelected(false);
            updateToggleVisual(celsiusBtn, true);
            updateToggleVisual(fahrenheitBtn, false);
        });
        fahrenheitBtn.addActionListener(e -> {
            fahrenheitBtn.setSelected(true);
            celsiusBtn.setSelected(false);
            updateToggleVisual(fahrenheitBtn, true);
            updateToggleVisual(celsiusBtn, false);
        });
        
        tempPanel.add(celsiusBtn);
        tempPanel.add(fahrenheitBtn);

        // 2. Sumber Data
        JLabel lblSrc = new JLabel("Sumber Data");
        lblSrc.setAlignmentX(LEFT_ALIGNMENT);
        lblSrc.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblSrc.setForeground(new Color(200, 200, 200));
        
        JPanel srcPanel = new JPanel();
        srcPanel.setLayout(new BoxLayout(srcPanel, BoxLayout.Y_AXIS));
        srcPanel.setOpaque(false);
        srcPanel.setAlignmentX(LEFT_ALIGNMENT);
        
        realApiRadio = createCustomRadio("Realtime API (Online)", "Membutuhkan koneksi internet");
        dummyDataRadio = createCustomRadio("Dummy Data (Offline)", "Data simulasi untuk demo");
        
        ButtonGroup bgSrc = new ButtonGroup();
        bgSrc.add(realApiRadio); bgSrc.add(dummyDataRadio);
        
        if(isDummyData) dummyDataRadio.setSelected(true); else realApiRadio.setSelected(true);
        
        srcPanel.add(realApiRadio);
        srcPanel.add(Box.createVerticalStrut(10));
        srcPanel.add(dummyDataRadio);

        // Menambahkan komponen ke Body
        body.add(lblTemp); body.add(Box.createVerticalStrut(10));
        body.add(tempPanel); body.add(Box.createVerticalStrut(25));
        body.add(lblSrc); body.add(Box.createVerticalStrut(10));
        body.add(srcPanel);

        // --- FOOTER (Tombol Simpan) ---
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        JButton saveBtn = new JButton("Simpan") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(ACCENT_COLOR.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(ACCENT_COLOR.brighter());
                } else {
                    g2.setColor(ACCENT_COLOR);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Text Centering
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveBtn.setPreferredSize(new Dimension(100, 45));
        saveBtn.setBorderPainted(false);
        saveBtn.setContentAreaFilled(false);
        saveBtn.setFocusPainted(false);
        saveBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        saveBtn.addActionListener(e -> {
            saved = true;
            isCelsius = celsiusBtn.isSelected();
            isDummyData = dummyDataRadio.isSelected();
            dispose();
        });
        
        // Tombol Batal
        JButton cancelBtn = new JButton("Batal");
        cancelBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cancelBtn.setForeground(new Color(180, 180, 180));
        cancelBtn.setBorderPainted(false);
        cancelBtn.setContentAreaFilled(false);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelBtn.addActionListener(e -> dispose());
        cancelBtn.addMouseListener(new MouseAdapter() {
             public void mouseEntered(MouseEvent e) { cancelBtn.setForeground(Color.WHITE); }
             public void mouseExited(MouseEvent e) { cancelBtn.setForeground(new Color(180, 180, 180)); }
        });

        footer.add(cancelBtn, BorderLayout.WEST);
        footer.add(saveBtn, BorderLayout.EAST);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(body, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);
        
        add(mainPanel);
    }

    // --- Helper UI Methods ---

    private JToggleButton createCustomToggle(String text, String symbol, boolean selected) {
        JToggleButton btn = new JToggleButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (isSelected()) {
                    g2.setColor(ACCENT_COLOR);
                } else {
                    g2.setColor(BUTTON_BG);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                if (!isSelected()) {
                    g2.setColor(new Color(255, 255, 255, 30));
                    g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
                }

                // Gambar Teks
                g2.setColor(isSelected() ? Color.WHITE : new Color(200, 200, 200));
                
                // Symbol (Besar)
                g2.setFont(new Font("Segoe UI", Font.BOLD, 24));
                FontMetrics fm = g2.getFontMetrics();
                int xSym = (getWidth() - fm.stringWidth(symbol)) / 2;
                int ySym = (getHeight() / 2) - 5;
                g2.drawString(symbol, xSym, ySym);
                
                // Text (Kecil)
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                fm = g2.getFontMetrics();
                int xTxt = (getWidth() - fm.stringWidth(text)) / 2;
                int yTxt = (getHeight() / 2) + 15;
                g2.drawString(text, xTxt, yTxt);
                
                g2.dispose();
            }
        };
        btn.setSelected(selected);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        return btn;
    }
    
    private void updateToggleVisual(JToggleButton btn, boolean isSelected) {
        btn.repaint();
    }

    private JRadioButton createCustomRadio(String title, String subtitle) {
        JRadioButton radio = new JRadioButton("<html><font color='#ffffff' size='4'><b>" + title + "</b></font><br><font color='#aaaaaa' size='3'>" + subtitle + "</font></html>");
        radio.setOpaque(false);
        radio.setFocusPainted(false);
        radio.setIcon(new RadioIcon(false));
        radio.setSelectedIcon(new RadioIcon(true));
        radio.setBorder(new EmptyBorder(5, 0, 5, 0));
        radio.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return radio;
    }
    
    // Icon Custom untuk Radio Button agar sesuai tema
    private class RadioIcon implements Icon {
        private boolean selected;
        public RadioIcon(boolean selected) { this.selected = selected; }
        public int getIconWidth() { return 20; }
        public int getIconHeight() { return 20; }
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Outer Circle
            g2.setColor(new Color(255,255,255, 30));
            g2.drawOval(x, y, 18, 18);
            
            if (selected) {
                g2.setColor(ACCENT_COLOR);
                g2.fillOval(x + 4, y + 4, 11, 11);
            }
            g2.dispose();
        }
    }

    public boolean isSaved() { return saved; }
    public boolean isCelsiusSelected() { return isCelsius; }
    public boolean isDummyDataSelected() { return isDummyData; }
}