package com.example.ppbo;
import java.util.Scanner;

public class Input {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input String
        System.out.print("Masukkan nama: ");
        String nama = scanner.nextLine();

        // Input Integer
        System.out.print("Masukkan umur: ");
        int umur = scanner.nextInt();

        // Input Double
        System.out.print("Masukkan tinggi badan: ");
        double tinggi = scanner.nextDouble();

        // Menampilkan hasil input
        System.out.println("Nama: " + nama);
        System.out.println("Umur: " + umur);
        System.out.println("Tinggi: " + tinggi + " cm");
    }
}
