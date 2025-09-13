package com.example.ppbo;

public class Output {
    public static void main(String[] args) {
        // Output menggunakan System.out.println
        System.out.println("Hello, Paul!");
        System.out.println("Baris pertama");
        
        // Output menggunakan System.out.print
        System.out.print("Hello ");
        System.out.print("Paul!");
        System.out.println();
        
        String nama = "Paul";
        int umur = 19;
        double tinggi = 175;
        // Output dengan format
        System.out.printf("Nama: %s, Umur: %d, Tinggi: %.1f cm\n", nama, umur, tinggi);
    }
}
