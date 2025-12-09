package com.example.ppbo;

public class Main {
    public static void main(String[] args) {
        // Object 1 - pakai constructor default
        Roblox game1 = new Roblox();
        game1.tampilkanInfo();

        // Object 2 dengan parameter
        Roblox game2 = new Roblox("Fish It", "Fish Atelier", "Adventure");
        game2.tampilkanInfo(true);

        // Object 3 dengan parameter
        Roblox game3 = new Roblox("Brookhaven", "Wolfpaq", "Roleplay");
        game3.tampilkanInfo(false);
    }
}