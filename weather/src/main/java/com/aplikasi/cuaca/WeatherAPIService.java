package com.aplikasi.cuaca;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherAPIService {

    // PASTIKAN API KEY INI SUDAH BENAR DAN AKTIF
    private static final String API_KEY = "770af8e90f7f32eefc4e7f64094421ff"; 
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

    public static Map<String, String> getWeatherFromAPI(String cityName) {
        StringBuilder result = new StringBuilder();
        Map<String, String> weatherData = new HashMap<>();

        try {
            // 1. Buat URL Request
            String urlString = BASE_URL + cityName.replace(" ", "%20") + "&appid=" + API_KEY + "&units=metric&lang=id";
            URL url = new URL(urlString);
            
            // 2. Buka Koneksi
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000); // Timeout 5 detik
            conn.setReadTimeout(5000);
            
            // Cek jika request sukses (Kode 200)
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                // Analisa Error
                String msg = "Gagal";
                if (responseCode == 401) msg = "API Key Belum Aktif / Salah";
                else if (responseCode == 404) msg = "Kota Tidak Ditemukan";
                else if (responseCode == 429) msg = "Terlalu Banyak Request";
                else msg = "Server Error: " + responseCode;
                
                System.out.println("Error dari Server: " + responseCode);
                weatherData.put("error", msg);
                return weatherData;
            }

            // 3. Baca Response JSON
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            // 4. Parsing Manual JSON
            String json = result.toString();
            
            // Cek validitas JSON sederhana
            if (!json.contains("\"name\"")) {
                 weatherData.put("error", "Data Server Kosong");
                 return weatherData;
            }

            weatherData.put("kota", extractJsonValue(json, "name"));
            
            String rawTemp = extractJsonNumber(json, "temp");
            if(rawTemp.contains(".")) rawTemp = rawTemp.substring(0, rawTemp.indexOf(".")); 
            weatherData.put("suhu", rawTemp + "°C");

            String mainWeather = extractJsonString(json, "main"); // Clouds, Rain, etc
            weatherData.put("cuaca", translateWeather(mainWeather));

            String desc = extractJsonString(json, "description");
            weatherData.put("deskripsi", capitalize(desc));
            
            String humidity = extractJsonNumber(json, "humidity");
            weatherData.put("kelembaban", humidity + "%");
            
            String wind = extractJsonNumber(json, "speed"); // Kecepatan angin
            weatherData.put("angin", wind + " km/h");

            weatherData.put("rekomendasi", generateRecommendation(mainWeather));

            // Data Dummy Pelengkap
            weatherData.put("f1_hari", "Besok"); weatherData.put("f1_cuaca", "Berawan"); weatherData.put("f1_suhu", "30°C");
            weatherData.put("f2_hari", "Lusa"); weatherData.put("f2_cuaca", "Cerah"); weatherData.put("f2_suhu", "31°C");
            weatherData.put("hourly", "08:00,28,Cerah;10:00,30,Cerah;12:00,32,Panas;14:00,31,Berawan;16:00,29,Hujan;18:00,28,Mendung");

        } catch (java.net.UnknownHostException e) {
            weatherData.put("error", "Tidak Ada Koneksi Internet");
            return weatherData;
        } catch (Exception e) {
            e.printStackTrace();
            weatherData.put("error", "Error Aplikasi: " + e.getMessage());
            return weatherData;
        }

        return weatherData;
    }

    // --- HELPER PARSING ---
    
    private static String extractJsonValue(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\":\"(.*?)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) return matcher.group(1);
        return "-";
    }

    private static String extractJsonString(String json, String key) {
        // Mencari nilai string, misalnya "main":"Clouds"
        Pattern pattern = Pattern.compile("\"" + key + "\":\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) return matcher.group(1);
        return "-";
    }

    private static String extractJsonNumber(String json, String key) {
        // Mencari angka
        Pattern pattern = Pattern.compile("\"" + key + "\":\\s*([0-9.]+)");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) return matcher.group(1);
        return "0";
    }

    private static String translateWeather(String english) {
        if (english.equalsIgnoreCase("Clouds")) return "Berawan";
        if (english.equalsIgnoreCase("Rain")) return "Hujan";
        if (english.equalsIgnoreCase("Clear")) return "Cerah";
        if (english.equalsIgnoreCase("Thunderstorm")) return "Hujan Petir";
        if (english.equalsIgnoreCase("Drizzle")) return "Gerimis";
        if (english.equalsIgnoreCase("Mist") || english.equalsIgnoreCase("Haze")) return "Kabut";
        return english;
    }
    
    private static String capitalize(String str) {
        if(str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static String generateRecommendation(String weather) {
        if (weather.contains("Rain") || weather.contains("Drizzle")) return "Sediakan payung sebelum hujan.";
        if (weather.contains("Thunder")) return "Hindari berteduh di bawah pohon.";
        if (weather.contains("Clear")) return "Gunakan sunscreen, matahari cukup terik.";
        if (weather.contains("Clouds")) return "Cuaca nyaman untuk aktivitas luar.";
        return "Tetap jaga kesehatan.";
    }
}