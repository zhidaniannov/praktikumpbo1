package com.example.ppbo;

public class Main {

    // Konstanta golongan darah
    public static final String GOLDARAH = "B";

    public static void main(String[] args) {
        // Biodata dasar
        String nama = "Selvia Ramadani";
        String nim = "2407110647 ";
        String alamat = "Jl. Kutilang Sakti, Pekanbaru";
        int umur = 19;

        // IP tiap semester disimpan dalam array (semester 1 dan 2)
        double[] ips = {3.67, 3.86};

        // Konversi IP semester 1 ke tipe byte
        byte ipSemester1Byte = (byte) ips[0];

        // Tampilkan biodata
        System.out.println("Nama                        : " + nama);
        System.out.println("NIM                         : " + nim);
        System.out.println("Alamat                      : " + alamat);
        System.out.println("Umur                        : " + umur);
        System.out.println("Golongan Darah              : " + GOLDARAH);
        System.out.println("IP Semester 1               : " + ips[0]);
        System.out.println("IP Semester 2               : " + ips[1]);
        System.out.println("IP Semester 1 (konversi byte): " + ipSemester1Byte);
    }
}