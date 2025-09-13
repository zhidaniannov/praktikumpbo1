package com.example.ppbo;

public class Enumerasi {
    // Mendefinisikan enumerasi
    enum Hari {
        SENIN, SELASA, RABU, KAMIS, JUMAT, SABTU, MINGGU
    }
    
    public static void main(String[] args) {
        Hari hariIni = Hari.MINGGU;
        
        if (hariIni == Hari.MINGGU) {
            System.out.println("Hari ini adalah Hari minggu.");
        }
        
        System.out.println("Besok adalah " + Hari.SENIN);
    }
}
