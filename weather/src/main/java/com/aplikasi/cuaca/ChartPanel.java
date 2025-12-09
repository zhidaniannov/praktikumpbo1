package com.aplikasi.cuaca;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class ChartPanel extends JPanel {
    private List<String[]> dataPoints = new ArrayList<>();
    private boolean isCelsius = true; // State untuk chart

    public void setData(String hourlyDataStr, boolean isCelsius) {
        this.isCelsius = isCelsius;
        dataPoints.clear();
        if (hourlyDataStr != null && !hourlyDataStr.isEmpty()) {
            String[] items = hourlyDataStr.split(";");
            for (String item : items) {
                String[] parts = item.split(",");
                if (parts.length == 3) dataPoints.add(parts);
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dataPoints.isEmpty()) return;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int w = getWidth(); int h = getHeight(); int padding = 40;
        int minTemp = 1000, maxTemp = -1000;
        int[] temps = new int[dataPoints.size()];
        
        for (int i=0; i<dataPoints.size(); i++) {
            int raw = Integer.parseInt(dataPoints.get(i)[1]);
            if(!isCelsius) raw = (int)((raw * 9.0/5.0)+32); 
            temps[i] = raw;
            if(raw < minTemp) minTemp = raw;
            if(raw > maxTemp) maxTemp = raw;
        }
        minTemp -= 2; maxTemp += 2;
        
        int xStep = (w - 2 * padding) / (dataPoints.size() - 1);
        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < dataPoints.size(); i++) {
            double scale = (double)(h - 2 * padding) / (maxTemp - minTemp);
            int y = h - padding - (int)((temps[i] - minTemp) * scale);
            graphPoints.add(new Point(padding + i * xStep, y));
        }

        // 1. Draw Area (Gradient)
        Path2D.Double areaPath = new Path2D.Double();
        areaPath.moveTo(graphPoints.get(0).x, graphPoints.get(0).y);
        for (int i = 1; i < graphPoints.size(); i++) {
            areaPath.lineTo(graphPoints.get(i).x, graphPoints.get(i).y);
        }
        areaPath.lineTo(graphPoints.get(graphPoints.size()-1).x, h - padding);
        areaPath.lineTo(graphPoints.get(0).x, h - padding);
        areaPath.closePath();

        GradientPaint fillPaint = new GradientPaint(0, 0, new Color(255, 255, 255, 100), 0, h, new Color(255, 255, 255, 0));
        g2.setPaint(fillPaint);
        g2.fill(areaPath);

        // 2. Draw Line
        g2.setColor(new Color(255, 255, 255, 200));
        g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        Path2D.Double linePath = new Path2D.Double();
        linePath.moveTo(graphPoints.get(0).x, graphPoints.get(0).y);
        for (int i = 1; i < graphPoints.size(); i++) {
            linePath.lineTo(graphPoints.get(i).x, graphPoints.get(i).y);
        }
        g2.draw(linePath);

        // 3. Draw Points & Text
        for (int i = 0; i < graphPoints.size(); i++) {
            Point p = graphPoints.get(i);
            String jam = dataPoints.get(i)[0];
            String suhu = temps[i] + "°";
            String cuaca = dataPoints.get(i)[2];
            
            g2.setColor(Color.WHITE);
            g2.fillOval(p.x - 5, p.y - 5, 10, 10);
            g2.setColor(new Color(52, 152, 219));
            g2.drawOval(p.x - 5, p.y - 5, 10, 10);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(suhu, p.x - fm.stringWidth(suhu)/2, p.y - 15);
            
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            fm = g2.getFontMetrics();
            g2.drawString(jam, p.x - fm.stringWidth(jam)/2, h - padding + 20);
            
            // PERBAIKAN: Menengahkan Ikon Cuaca
            String icon = WeatherUtils.getWeatherEmoji(cuaca);
            g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
            fm = g2.getFontMetrics(); // Hitung lebar emoji yang spesifik
            // Gunakan fm.stringWidth() agar posisi x dinamis sesuai lebar emoji
            g2.drawString(icon, p.x - fm.stringWidth(icon)/2, h - padding + 40);
        }
    }
}