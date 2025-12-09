package com.example.ppbo;

import java.util.Scanner;

public class Switch {
        public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Masukkan warna (RGB)= ");
        String warna = scanner.nextLine();

        switch (warna) {
            case "R":
                System.out.println("Anda memasukkan warna merah");
                break;

            case "G":
                System.out.println("Anda memasukkan warna hijau");
                break;

            case "B":
                System.out.println("Anda memasukkan warna biru");
                break;
            default:
                System.out.println("Warna tidak tersedia");
                break;
        }
        scanner.close();
    }
}
