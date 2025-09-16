package com.example.ppbo;

import java.util.Scanner;

public class WhileLoop {
    public static void main(String[] args) 
    {
        int a = 10;
        while (a <= 5) {
            System.out.println("While loop ke " + a);
        }
        Scanner scanner = new Scanner(System.in);
        String password = "";
        while (!password.equals("java123")) {
            System.out.println("Masukkan Password: ");
            password = scanner.nextLine();
        }
        System.out.println("Login berhasil");
        scanner.close();
    }
}
