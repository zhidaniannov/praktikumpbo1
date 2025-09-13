package com.example.ppbo;

public class OperatorAssignment {
    public static void main(String[] args) {
        int num = 10;

        num += 5; // num = num + 5; // 15
        num -= 3; // num = num - 3; // 12
        num *= 2; // num = num * 2; // 24
        num /= 4; // num = num / 4; // 6
        num %= 4; // num = num % 4; // 2

        System.out.println("Hasil akhir: " + num);
    }
}