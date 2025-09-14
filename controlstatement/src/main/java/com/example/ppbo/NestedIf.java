package com.example.ppbo;

import java.util.Scanner;

public class NestedIf {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Masukkan angka: ");
        int angka = scanner.nextInt();
        if (angka >= 0) {
            System.out.println("Bilangan bernilai positif");
            if (angka % 2 == 0) {
                System.out.println("dan genap");
            } else {
                System.out.println("dan ganjil");
            }
        } else if (angka == 0) {
            System.out.println("Bilangan bernilai netral ");
        } else {
            System.out.println("Bilangan bernilai negatif ");

            if (angka % 2 == 0) {
                System.out.println("dan genap");
            } else {
                System.out.println("dan ganjil");
            }
        }
        scanner.close();
    }
}
