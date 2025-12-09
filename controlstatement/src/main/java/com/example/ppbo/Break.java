package com.example.ppbo;

public class Break {
    public static void main(String[] args) {
        for (int d = 1; d <= 10; d++) {
            if (d == 5) {
                System.out.println("Break di angka- " + d);
                break;
            }
            System.out.println("Angka: " + d);
        }
    }
}
