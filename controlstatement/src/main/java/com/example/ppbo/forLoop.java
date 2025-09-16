package com.example.ppbo;

public class forLoop {
    public static void main(String[] args) {
        for (int i = 1; i < 5; i++) {
            System.out.println("perulangan ke " + i);
        }
        String[] mahasiswa = { "Andi", "Budi", "Citra", "Dewi", "Eko" };
        for (int a = 0; a < mahasiswa.length; a++) {
            System.out.println("Mahasiswa ke " + (a + 1) + ": " + mahasiswa[a]);
        }
    }
}
