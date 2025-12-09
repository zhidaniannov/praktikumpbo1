package com.ppbo.enkapsulasi;

public class Product {
    public String nama;
    private double harga;
    protected int stok;
    private String namaSupplier;

    public Product(String nama, double harga, int stok, String namaSupplier) {
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
        this.namaSupplier = namaSupplier;
    }

    private void infoSupplier(){
        System.out.println("Supplier: " + namaSupplier);
    }

    public void tampilkanInfo() {
        System.out.println("Nama: " + nama);
        System.out.println("Harga: " + harga);
        System.out.println("Stok: " + stok);
        infoSupplier();
    }
    
}
