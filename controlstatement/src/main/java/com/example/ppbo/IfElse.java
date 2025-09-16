package com.example.ppbo;

import java.util.Scanner;

    public class IfElse {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("Masukkan nilai = ");
        int nilai = input.nextInt();
        if (nilai > 75) {

            System.out.println("Anda lulus ujian ");

        } else {

            System.out.println("Anda harus mengulang ujian ");
        }
        input.close();
    }
}
