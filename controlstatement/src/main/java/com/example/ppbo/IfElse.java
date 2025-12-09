package com.example.ppbo;
import java.util.Scanner;

public class IfElse {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Masukkan nilai = ");
        int nilai = scanner.nextInt();
        if (nilai > 75) {

            System.out.println("Anda lulus ujian ");

        } else {

            System.out.println("Anda harus mengulang ujian ");
        }
        scanner.close();
    }
}