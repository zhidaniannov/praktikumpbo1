package com.example.ppbo;

public class Main {
    public static void main(String[] args) {
        // Test Mobil
        System.out.println("=== Test Mobil 1 ===");
        Mobil mobil1 = new Mobil();
        mobil1.nyalakanMesin();
        mobil1.matikanMesin();

        System.out.println("\n=== Test Mobil 2 ===");
        Mobil2 mobil2 = new Mobil2();
        System.out.println("Merek: " + mobil2.getMerek() + ", Warna: " + mobil2.getWarna());

        System.out.println("\n=== Test Mobil 3 ===");
        Mobil3 mobil3 = new Mobil3("Toyota", "Hitam", 2022);
        System.out.println("Mobil3 dibuat dengan constructor berparameter.");

        System.out.println("\n=== Test Mobil 4 ===");
        Mobil4 mobil4 = new Mobil4(); // otomatis buat 2 objek di dalamnya
        System.out.println("Mobil4 berisi instansiasi mobil1 & mobil2.");

        System.out.println("\n=== Test Mobil 5 ===");
        Mobil5 mobil5a = new Mobil5();
        Mobil5 mobil5b = new Mobil5("Honda");
        System.out.println("Mobil5a dan Mobil5b dibuat dengan constructor berbeda.");

        // Test Kalkulator
        System.out.println("\n=== Test Kalkulator 1 ===");
        Kalkulator k1 = new Kalkulator();
        System.out.println("2 + 3 = " + k1.tambah(2, 3));
        System.out.println("1 + 2 + 3 = " + k1.tambah(1, 2, 3));
        System.out.println("1 + 2 + 3 + 4 = " + k1.tambah(1, 2, 3, 4));

        System.out.println("\n=== Test Kalkulator 2 ===");
        Kalkulator2 k2 = new Kalkulator2();
        System.out.println("Tambah int: " + k2.tambah(5, 7));
        System.out.println("Tambah double: " + k2.tambah(2.5, 3.5));
        System.out.println("Tambah string: " + k2.tambah("Hello ", "World"));

        // Test Hewan
        System.out.println("\n=== Test Hewan ===");
        Hewan kucing = new Kucing("Kitty");
        Hewan anjing = new Anjing("Buddy");

        kucing.info();
        kucing.suara();

        anjing.info();
        anjing.suara();
    }
}
