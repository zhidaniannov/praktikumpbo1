package com.example.ppbo;

public class Enumerasi {
    // Mendefinisikan enumerasi
    enum Hari {
        SENIN, SELASA, RABU, KAMIS, JUMAT, SABTU, MINGGU
    }
    
    public static void main(String[] args) {

        Hari hariIni = Hari.SENIN;

        if (hariIni == Hari.SENIN) {
            System.out.println("Hari ini adalah Senin.");
        }

        System.out.println("Besok adalah " + Hari.SELASA);
    }
}
