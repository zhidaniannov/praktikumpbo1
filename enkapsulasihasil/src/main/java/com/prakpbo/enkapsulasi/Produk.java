package com.prakpbo.enkapsulasi;

public class Produk {
    public String nama;
    private double harga;
    protected int stok;
    private String namaSupplier = "Zhidan";

    static int jumlahProduk = 0;

    public Produk(String nama, double harga, int stok) {
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
        jumlahProduk++;
    }

    public static void infoJumlahProduk() {
        System.out.println("Total produk yang telah dibuat: " + jumlahProduk);
    }

    private void namaSupplier() {
        System.out.println("Nama Supplier: " + this.namaSupplier);
    }

    public void namaSupplierFix() {
        this.namaSupplier();
    }

    public double getHarga() {
        return this.harga;
    }

    public void setHarga(double harga) {
        if (harga > 0) {
            this.harga = harga;
        } else if (harga == 0) {
            this.harga = harga;
            System.out.println("Produk ini gratis");
        } else {
            System.out.println("Harga tidak boleh negatif");
        }
    }

    public void tampilkanInfo() {
        System.out.println("Nama Produk: " + this.nama);
        System.out.println("Harga: " + this.harga);
        System.out.println("Stok: " + this.stok);
    }
}