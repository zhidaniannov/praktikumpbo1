package com.groupv.weather;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class WeatherChartPanel extends JPanel {
    private List<String[]> dataPoints = new ArrayList<>();
    private boolean isCelsius = true;

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
        if (maxTemp == minTemp) maxTemp++;

        int xStep = (dataPoints.size() > 1) ? (w - 2 * padding) / (dataPoints.size() - 1) : 0;
        List<Point> graphPoints = new ArrayList<>();
        
        for (int i = 0; i < dataPoints.size(); i++) {
            double scale = (double)(h - 2 * padding) / (maxTemp - minTemp);
            int y = h - padding - (int)((temps[i] - minTemp) * scale);
            graphPoints.add(new Point(padding + i * xStep, y));
        }

        // --- BUAT PATH KURVA HALUS (Smooth Curve) ---
        Path2D.Double smoothPath = createSmoothPath(graphPoints);
        
        // 1. Gambar Area Gradient di Bawah Kurva
        Path2D.Double areaPath = (Path2D.Double) smoothPath.clone();
        areaPath.lineTo(graphPoints.get(graphPoints.size()-1).x, h - padding);
        areaPath.lineTo(graphPoints.get(0).x, h - padding);
        areaPath.closePath();
        
        GradientPaint gradient = new GradientPaint(0, 0, new Color(255, 255, 255, 120), 0, h, new Color(255, 255, 255, 0));
        g2.setPaint(gradient);
        g2.fill(areaPath);

        // 2. Gambar Garis Kurva
        g2.setColor(new Color(255, 255, 255, 220));
        g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.draw(smoothPath);

        // 3. Gambar Titik dan Label
        for (int i = 0; i < graphPoints.size(); i++) {
            Point p = graphPoints.get(i);
            String label = temps[i] + "°";
            String time = dataPoints.get(i)[0];
            
            g2.setColor(Color.WHITE);
            g2.fillOval(p.x - 5, p.y - 5, 10, 10);
            
            g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(label, p.x - fm.stringWidth(label)/2, p.y - 12);
            
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            fm = g2.getFontMetrics();
            g2.drawString(time, p.x - fm.stringWidth(time)/2, h - padding + 20);
        }
    }

    // Algoritma sederhana untuk membuat kurva Bezier melalui titik-titik
    private Path2D.Double createSmoothPath(List<Point> points) {
        Path2D.Double path = new Path2D.Double();
        if (points.isEmpty()) return path;

        path.moveTo(points.get(0).x, points.get(0).y);

        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            
            // Kontrol point sederhana: setengah jarak horizontal, tinggi sama dengan titik sebelumnya/sesudahnya
            int cx1 = p1.x + (p2.x - p1.x) / 2;
            int cy1 = p1.y;
            int cx2 = p2.x - (p2.x - p1.x) / 2;
            int cy2 = p2.y;

            path.curveTo(cx1, cy1, cx2, cy2, p2.x, p2.y);
        }
        return path;
    }
}