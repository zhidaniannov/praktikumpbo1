package com.groupv.weather; // Package diperbarui

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Import diperbarui
import com.groupv.weather.Icons.*;

public class SettingsDialog extends JDialog {

    private boolean saved = false;
    private boolean isCelsius;
    private boolean isDummyData;

    private JToggleButton celsiusBtn, fahrenheitBtn;
    private JRadioButton realApiRadio, dummyDataRadio;

    public SettingsDialog(Frame parent, boolean currentCelsius, boolean currentDummy) {
        super(parent, "Pengaturan", true);
        this.isCelsius = currentCelsius;
        this.isDummyData = currentDummy;

        setupUI();
    }

    private void setupUI() {
        setUndecorated(true);
        setSize(400, 350);
        setLocationRelativeTo(getParent());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(20, 20, 10, 20));
        JLabel title = new JLabel("Preferensi");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        JButton closeBtn = new JButton("×");
        closeBtn.setFont(new Font("Arial", Font.BOLD, 24));
        closeBtn.setBorderPainted(false); closeBtn.setContentAreaFilled(false);
        closeBtn.addActionListener(e -> dispose());
        header.add(title, BorderLayout.WEST); header.add(closeBtn, BorderLayout.EAST);

        // Body
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(Color.WHITE);
        body.setBorder(new EmptyBorder(10, 20, 20, 20));

        // Section 1: Temperature Unit
        JLabel lblTemp = new JLabel("Satuan Suhu");
        lblTemp.setAlignmentX(LEFT_ALIGNMENT);
        lblTemp.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JPanel tempPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        tempPanel.setBackground(Color.WHITE);
        tempPanel.setAlignmentX(LEFT_ALIGNMENT);
        tempPanel.setMaximumSize(new Dimension(1000, 60));
        
        celsiusBtn = createToggle("Celcius", "°C", isCelsius);
        fahrenheitBtn = createToggle("Fahrenheit", "°F", !isCelsius);
        ButtonGroup bgTemp = new ButtonGroup();
        bgTemp.add(celsiusBtn); bgTemp.add(fahrenheitBtn);
        tempPanel.add(celsiusBtn); tempPanel.add(fahrenheitBtn);

        // Section 2: Data Source
        JLabel lblSrc = new JLabel("Sumber Data");
        lblSrc.setAlignmentX(LEFT_ALIGNMENT);
        lblSrc.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JPanel srcPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        srcPanel.setBackground(Color.WHITE);
        srcPanel.setAlignmentX(LEFT_ALIGNMENT);
        
        realApiRadio = new JRadioButton("Realtime API (Online)");
        dummyDataRadio = new JRadioButton("Dummy Data (Offline/Demo)");
        realApiRadio.setBackground(Color.WHITE); dummyDataRadio.setBackground(Color.WHITE);
        ButtonGroup bgSrc = new ButtonGroup();
        bgSrc.add(realApiRadio); bgSrc.add(dummyDataRadio);
        
        if(isDummyData) dummyDataRadio.setSelected(true); else realApiRadio.setSelected(true);
        srcPanel.add(realApiRadio); srcPanel.add(dummyDataRadio);

        body.add(lblTemp); body.add(Box.createVerticalStrut(5));
        body.add(tempPanel); body.add(Box.createVerticalStrut(20));
        body.add(lblSrc); body.add(Box.createVerticalStrut(5));
        body.add(srcPanel);

        // Footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(Color.WHITE);
        JButton saveBtn = new JButton("Simpan");
        saveBtn.setBackground(new Color(52, 152, 219));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setPreferredSize(new Dimension(100, 40));
        saveBtn.setFocusPainted(false);
        saveBtn.addActionListener(e -> {
            saved = true;
            isCelsius = celsiusBtn.isSelected();
            isDummyData = dummyDataRadio.isSelected();
            dispose();
        });
        footer.add(saveBtn);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(body, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JToggleButton createToggle(String text, String symbol, boolean selected) {
        JToggleButton btn = new JToggleButton("<html><center><font size='5'>" + symbol + "</font><br>" + text + "</center></html>");
        btn.setSelected(selected);
        btn.setFocusPainted(false);
        btn.setBackground(selected ? new Color(235, 245, 255) : Color.WHITE);
        return btn;
    }

    public boolean isSaved() { return saved; }
    public boolean isCelsiusSelected() { return isCelsius; }
    public boolean isDummyDataSelected() { return isDummyData; }
}