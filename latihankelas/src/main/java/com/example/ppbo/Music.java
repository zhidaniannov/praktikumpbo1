// package com.example.ppbo;

public class Music {
    // Atribut 
    String judul;
    String artis;
    String genre;

    // Constructor Default 
    public Music() {
        this.judul = "Unknown";
        this.artis = "Unknown";
        this.genre = "Unknown";
    }

    // Constructor dengan Parameter 
    public Music(String judul, String artis, String genre) {
        this.judul = judul;
        this.artis = artis;
        this.genre = genre;
    }

    // Method Overloading 
    
    // Versi 1: Tanpa parameter
    public void tampilkanInfo() {
        System.out.println("Judul: " + this.judul + " | Artis: " + this.artis + " | Genre: " + this.genre);
    }
    
    // Versi 2: Dengan parameter boolean
    public void tampilkanInfo(boolean simple) {
        if (simple) { // Jika true: tampilkan judul + genre
            System.out.println("Judul: " + this.judul + " | Genre: " + this.genre);
        } else { // Jika false: tampilkan judul + artis 
            System.out.println("Judul: " + this.judul + " | Artis: " + this.artis);
        }
    }
}
