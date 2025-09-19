package com.example.ppbo;

class Music {
    String judul;
    String artis;
    String genre;

    // Constructor default
    public Music() {
        this.judul = "Unknown";
        this.artis = "Unknown";
        this.genre = "Unknown";
    }

    // Constructor dengan parameter
    public Music(String judul, String artis, String genre) {
        this.judul = judul;
        this.artis = artis;
        this.genre = genre;
    }

    // Overloading
    // Versi tanpa parameter
    public void tampilkanInfo() {
        System.out.println("Judul: " + judul + " | Artis: " + artis + " | Genre: " + genre);
    }

    // Versi overloading dengan parameter
    public void tampilkanInfo(boolean simple) {
        if (simple) {
            System.out.println("Judul: " + judul + " | Genre: " + genre);
        } else {
            System.out.println("Judul: " + judul + " | Artis: " + artis + " | Genre: " + genre);
        }
    }
}
