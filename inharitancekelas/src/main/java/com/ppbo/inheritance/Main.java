package com.ppbo.inheritance;

public class Main {

    public static void main(String[] args) {
        BarangElektronik laptop = new BarangElektronik("Laptop", 15000000, 24);
        laptop.tampilkanInfo();
        laptop.tampilkanGaransi();
        
        Produk p1 = new Produk("Buku", 60000);
        BarangElektronik p2 = new BarangElektronik("Mouse", 50000,6);

        p1.tampilkanInfo();
        System.out.println("Pajak produk umum: "+p1.hitungPajak());
        System.out.println("Harga akhir Produk: "+p1.hitungHarga());
        System.out.println("---------------");
        p2.tampilkanInfo();
        p2.tampilkanGaransi();
        System.out.println("Pajak barang elektronik: "+p2.hitungPajak());
        System.out.println("Harga akhir Produk: "+p2.hitungHarga());
    }
}