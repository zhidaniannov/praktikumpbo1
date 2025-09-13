package com.example.ppbo;

import java.util.Scanner;

public class MultipleIfElse {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Masukkan nilai : ");
        int nilai = scanner.nextInt();
        if (nilai > 75) {

            System.out.println("Anda lulus ujian ");

        } else if (nilai > 65) {

            System.out.println("Nilai anda berada di ambang batas kelulusan");

        } else {

            System.out.println("Anda harus mengulang ujian ");
        }
        scanner.close();
    }
}
