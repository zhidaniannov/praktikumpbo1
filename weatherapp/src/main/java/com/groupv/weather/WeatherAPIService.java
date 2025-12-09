package com.groupv.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherAPIService {

    private static final String API_KEY = "770af8e90f7f32eefc4e7f64094421ff"; 
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

    public static Map<String, String> getWeatherData(String city, boolean useDummy) {
        if (useDummy) {
            return generateDummyData(city);
        } else {
            return getRealData(city);
        }
    }

    private static Map<String, String> getRealData(String cityName) {
        StringBuilder result = new StringBuilder();
        Map<String, String> weatherData = new HashMap<>();

        try {
            String urlString = BASE_URL + cityName.replace(" ", "%20") + "&appid=" + API_KEY + "&units=metric&lang=id";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);

            if (conn.getResponseCode() != 200) {
                weatherData.put("error", "Error: " + conn.getResponseCode());
                return weatherData;
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) result.append(line);
            rd.close();

            return parseJson(result.toString());

        } catch (Exception e) {
            weatherData.put("error", "Koneksi Gagal");
            return weatherData;
        }
    }
    
    // --- GENERATOR DATA DUMMY 24 JAM ---
    private static Map<String, String> generateDummyData(String city) {
        Map<String, String> data = new HashMap<>();
        Random r = new Random();
        String[] types = {"Cerah", "Berawan", "Hujan", "Mendung"};
        String type = types[r.nextInt(types.length)];
        int temp = 25 + r.nextInt(10); 
        
        try { Thread.sleep(300); } catch(Exception e){} 

        data.put("kota", city + " (Dummy)");
        data.put("suhu", temp + "°C");
        data.put("cuaca", type);
        data.put("deskripsi", "Data simulasi UAS");
        data.put("kelembaban", (50 + r.nextInt(40)) + "%");
        data.put("rekomendasi", "Mode Demo: Data tidak akurat.");
        
        data.put("f1_hari", "Besok"); data.put("f1_suhu", (temp+1)+"°C"); data.put("f1_cuaca", type);
        data.put("f2_hari", "Lusa"); data.put("f2_suhu", (temp-1)+"°C"); data.put("f2_cuaca", "Cerah");
        
        // 24 Jam (00:00 - 22:00, Step 2 Jam -> 12 Titik)
        StringBuilder hourly = new StringBuilder();
        // Pola suhu 24 jam: Dingin pagi, Panas Siang, Dingin Malam
        int[] dailyPattern = {-2, -3, -2, 0, 3, 5, 4, 2, 1, 0, -1, -2}; 
        
        for(int i=0; i<12; i++) {
             int h = i * 2; // 0, 2, 4, ... 22
             int t = temp + dailyPattern[i] + r.nextInt(2);
             String timeStr = String.format("%02d:00", h);
             hourly.append(timeStr).append(",").append(t).append(",").append(type);
             if (i < 11) hourly.append(";");
        }
        data.put("hourly", hourly.toString());
        
        return data;
    }

    // --- PARSER JSON + SIMULASI 24 JAM ---
    private static Map<String, String> parseJson(String json) {
        Map<String, String> map = new HashMap<>();
        map.put("kota", extract(json, "name"));
        String tempMain = extractNum(json, "temp").split("\\.")[0];
        map.put("suhu", tempMain + "°C");
        
        String mainWeather = translate(extract(json, "main"));
        map.put("cuaca", mainWeather);
        map.put("deskripsi", extract(json, "description"));
        map.put("kelembaban", extractNum(json, "humidity") + "%");
        map.put("rekomendasi", "Cek cuaca berkala.");
        
        map.put("f1_hari", "Besok"); map.put("f1_suhu", (Integer.parseInt(tempMain)+1)+"°C"); map.put("f1_cuaca", mainWeather);
        map.put("f2_hari", "Lusa"); map.put("f2_suhu", (Integer.parseInt(tempMain)-1)+"°C"); map.put("f2_cuaca", "Cerah");
        
        // Simulasi 24 Jam berdasarkan suhu saat ini
        int baseTemp = Integer.parseInt(tempMain);
        StringBuilder hourly = new StringBuilder();
        int[] dailyPattern = {-3, -4, -3, -1, 2, 4, 3, 1, 0, -1, -2, -3}; 
        
        for(int i=0; i<12; i++) {
            int h = i * 2; // 00:00, 02:00 ...
            int t = baseTemp + dailyPattern[i];
            String timeStr = String.format("%02d:00", h);
            hourly.append(timeStr).append(",").append(t).append(",").append(mainWeather);
            if (i < 11) hourly.append(";");
        }
        map.put("hourly", hourly.toString());
        
        return map;
    }

    private static String extract(String json, String key) {
        Matcher m = Pattern.compile("\"" + key + "\":\\s*\"([^\"]+)\"").matcher(json);
        return m.find() ? m.group(1) : "-";
    }
    
    private static String extractNum(String json, String key) {
        Matcher m = Pattern.compile("\"" + key + "\":\\s*([0-9.]+)").matcher(json);
        return m.find() ? m.group(1) : "0";
    }
    
    private static String translate(String en) {
        if(en.contains("Rain")) return "Hujan";
        if(en.contains("Cloud")) return "Berawan";
        if(en.contains("Clear")) return "Cerah";
        if(en.contains("Thunder")) return "Petir";
        if(en.contains("Drizzle")) return "Gerimis";
        return en;
    }
}