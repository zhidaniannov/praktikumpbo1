package com.example.ppbo;

/**
 * Program sederhana menampilkan biodata.
 * Syarat:
 * - Nama, NIM, IP tiap semester (array), Alamat, Umur, Golongan Darah
 * - Konversi 1 IP ke tipe byte
 * - Ada 1 konstanta (GOLDARAH)
 */
public class Main {
    // konstanta golongan darah
    public static final String GOLONGAN_DARAH = "A";

    public static void main(String[] args) {
        // Biodata dasar
        String nama = "Zhidan Iannov Saaba";
        String nim = "2407134924";
        String alamat = "Jl. Swakarya, Pekanbaru";
        int umur = 20;

        // IP tiap semester disimpan dalam array (semester 1 dan 2)
        double[] ips = {3.64, 3.73};

        // Konversi IP semester 1 ke tipe byte
        byte ipSemester1Byte = (byte) ips[0];

        // Tampilkan biodata
        System.out.println("Nama                        : " + nama);
        System.out.println("NIM                         : " + nim);
        System.out.println("Alamat                      : " + alamat);
        System.out.println("Umur                        : " + umur);
        System.out.println("Golongan Darah              : " + GOLONGAN_DARAH);
        System.out.println("IP Semester 1               : " + ips[0]);
        System.out.println("IP Semester 2               : " + ips[1]);
        System.out.println("IP Semester 1 (konversi byte): " + ipSemester1Byte);
    }
}