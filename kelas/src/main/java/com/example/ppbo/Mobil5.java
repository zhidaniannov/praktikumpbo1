package com.example.ppbo;

public class Mobil5 {
    private String merek;
    private String warna;
    private int tahun;
    private double harga;

    // Constructor 1: Default
    public Mobil5() {
        this.merek = "Unknown";
        this.warna = "Putih";
        this.tahun = 2023;
        this.harga = 0.0;
    }

    // Constructor 2: Dengan merek saja
    public Mobil5(String merek) {
        this.merek = merek;
        this.warna = "Putih";
        this.tahun = 2023;
        this.harga = 0.0;
    }
}
