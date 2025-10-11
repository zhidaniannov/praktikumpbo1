package com.ppbo.inheritance;

public class Produk {
    protected String nama;
    protected int harga;

    public Produk(String nama, int harga) {
        this.nama = nama;
        this.harga = harga;
    }

    public void tampilkanInfo() {
        System.out.println("Nama produk : " + nama);
        System.out.println("Harga satuan: " + harga);
    }
}