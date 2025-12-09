package com.prakpbo.enkapsulasi;

public class Rekening {
    private String namaPemilik;
    private int saldo;
    private static int jumlahRekening;

    public Rekening(String namaPemilik, int saldo) {
        this.namaPemilik = namaPemilik;
        this.saldo = saldo;
        jumlahRekening++;
    }

    public String getnamaPemilik() {
        return namaPemilik;
    }

    public void setnamaPemilik(String namaPemilik) {
        this.namaPemilik = namaPemilik;
    }

    public int getsaldo() {
        return saldo;
    }

    public void setsaldo(int saldo) {
        this.saldo = saldo;
    }

    public static int getjumlahRekening() {
        return jumlahRekening;
    }

    public void tampilkanInfoRekening() {
        System.out.println("Nama pemilik rekening: " + namaPemilik);
        System.out.println("Jumlah saldo: " + saldo);
    }
    
}
