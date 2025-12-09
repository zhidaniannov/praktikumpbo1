package com.prakpbo.enkapsulasi;

public class Main {
    public static void main(String[] args) {
        Bank fakultas1 = new Bank("Bank Central Asia", "BCA");
        Bank fakultas2 = new Bank("Bank Rakyat Indonesia", "BRI");
        System.out.println("==================================");
        fakultas1.tampilkanInfoBank();
        System.out.println("==================================");

        fakultas2.tampilkanInfoBank();
        System.out.println("==================================");

        fakultas1.jumlahBank();
        System.out.println("==================================\n");
        System.out.println("==================================");

        Rekening r1 = new Rekening("Sarkawi", 20000);
        Rekening r2 = new Rekening("Arifin", 50000);
        r1.tampilkanInfoRekening();
        System.out.println("==================================");
        r2.tampilkanInfoRekening();

        System.out.println("==================================");
        System.out.println("Jumlah Rekening: " + Rekening.getjumlahRekening());
        System.out.println("==================================");
    }
}