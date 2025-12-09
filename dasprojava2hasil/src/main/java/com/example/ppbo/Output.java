package com.example.ppbo;

public class Output {
    public static void main(String[] args) {
        // Output menggunakan System.out.println
        System.out.println("Hello, World!");
        System.out.println("Baris kedua");

        // Output menggunakan System.out.print
        System.out.print("Hello ");
        System.out.print("World!");
        System.out.println();

        String nama = "John";
        int umur = 25;
        double tinggi = 175.5;

        // Output dengan format
        System.out.printf("Nama: %s, Umur: %d, Tinggi: %.1f cm%n", nama, umur, tinggi);
    }
}
