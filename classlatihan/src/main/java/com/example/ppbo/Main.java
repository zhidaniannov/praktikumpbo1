package com.example.ppbo;

public class Main {
    public static void main(String[] args) {
        // Object 1 - pakai constructor default
        Music lagu1 = new Music();
        lagu1.tampilkanInfo();

        // Object 2 dengan parameter
        Music lagu2 = new Music("Lagu Cinta", "Artis A", "Pop");
        lagu2.tampilkanInfo(true);

        // Object 3 dengan parameter
        Music lagu3 = new Music("Lagu Bahagia", "Artis B", "Jazz");
        lagu3.tampilkanInfo(false);
    }
}