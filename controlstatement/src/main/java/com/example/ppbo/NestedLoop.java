package com.example.ppbo;

public class NestedLoop 
{
    public static void main(String[] args) {

        System.out.println("---Mencetak nomor kursi di bioskop---");
        char kursi = 'A';
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j <= 5; j++) {
                System.out.println(kursi + "" + j + "");
            }
            System.out.println();
            kursi++;
        }
    }
}
