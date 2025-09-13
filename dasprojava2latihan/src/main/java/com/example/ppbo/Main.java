package com.example.ppbo;
import java.util.Scanner;

public class Main 
{
    public static final double PI = 3.14;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("============ Kalkulator Bola ============");
        System.out.print("Masukkan jari-jari: ");
        double jariJari = input.nextDouble();
        System.out.println("=========== Hasil Perhitungan ===========");

        // Perhitungan volume dan luas permukaan bola
        double volume = (4.0 / 3.0) * PI * jariJari * jariJari * jariJari;
        double luasPermukaan = 4 * PI * jariJari * jariJari;

        // Menampilkan hasil
        System.out.println("Volume bola: " + volume);
        System.out.println("Luas permukaan bola: " + luasPermukaan);

        input.close();
    }
}
