package com.example.ppbo;

class Roblox {
    String namaGame;
    String publisher;
    String genre;

    // Constructor default
    public Roblox() {
        this.namaGame = "Unknown";
        this.publisher = "Unknown";
        this.genre = "Unknown";
    }

    // Constructor dengan parameter
    public Roblox(String namaGame, String publisher, String genre) {
        this.namaGame = namaGame;
        this.publisher = publisher;
        this.genre = genre;
    }

    // Overloading
    public void tampilkanInfo() {
        System.out.println("Game: " + namaGame + " | Publisher: " + publisher + " | Genre: " + genre);
    }

    public void tampilkanInfo(boolean simple) {
        if (simple) {
            System.out.println("Game: " + namaGame + " | Genre: " + genre);
        } else {
            System.out.println("Game: " + namaGame + " | Publisher: " + publisher);
        }
    }
}
