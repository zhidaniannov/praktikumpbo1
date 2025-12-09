package com.example.ppbo;

/**
 * Author: Kelompok 5
 * Hasil dari praktikum Dasar Pemrograman Java II
 */
public class OperatorAritmatika {
    public static void main(String[] args) {
        int a = 10, b = 3;

        System.out.println("a + b = " + (a + b)); // Penjumlahan
        System.out.println("a - b = " + (a - b)); // Pengurangan
        System.out.println("a * b = " + (a * b)); // Perkalian
        System.out.println("a / b = " + (a / b)); // Pembagian
        System.out.println("a & b = " + (a % b)); // Modulus

        // Increment dan Decrement
        System.out.println("++a = " + (++a)); // Pre-increment
        System.out.println("b-- = " + (b--)); // Post-decrement
    }
}