package com.example.ppbo;
import java.util.Scanner;

public class Kalkulator {
    public static final double PI = 3.14;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean lanjut = true;
        while (lanjut) {
            System.out.println("\nMenu Kalkulator Luas Bangun Datar:");
            System.out.println("1. Persegi");
            System.out.println("2. Persegi Panjang");
            System.out.println("3. Segitiga");
            System.out.println("4. Lingkaran");
            System.out.println("5. Keluar");
            System.out.print("Pilih (1-5): ");
            int pilihan = scanner.nextInt();
            double luas = 0;
            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan sisi persegi: ");
                    double sisi = scanner.nextDouble();
                    luas = sisi * sisi;
                    System.out.println("Luas Persegi: " + luas);
                    System.out.println("\nTekan Enter untuk kembali ke menu utama...");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;
                                case 2:
                    System.out.print("Masukkan panjang: ");
                    double panjang = scanner.nextDouble();
                    System.out.print("Masukkan lebar: ");
                    double lebar = scanner.nextDouble();
                    luas = panjang * lebar;
                    System.out.println("Luas Persegi Panjang: " + luas);
                    System.out.println("\nTekan Enter untuk kembali ke menu utama...");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;
                case 3:
                    System.out.print("Masukkan alas: ");
                    double alas = scanner.nextDouble();
                    System.out.print("Masukkan tinggi: ");
                    double tinggi = scanner.nextDouble();
                    luas = 0.5 * alas * tinggi;
                    System.out.println("Luas Segitiga: " + luas);
                    System.out.println("\nTekan Enter untuk kembali ke menu utama...");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;
                case 4:
                    System.out.print("Masukkan jari-jari: ");
                    double jariJari = scanner.nextDouble();
                    luas = PI * jariJari * jariJari;
                    System.out.println("Luas Lingkaran: " + luas);
                    System.out.println("\nTekan Enter untuk kembali ke menu utama...");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;
                case 5:
                    lanjut = false;
                    System.out.println("Terima kasih telah menggunakan kalkulator ini!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
                    System.out.println("\nTekan Enter untuk kembali ke menu utama...");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;
            }
        }
        scanner.close();
    }
}
