package com.example.ppbo;

public class Main {
    public static void main(String[] args) {
        // Object 1 - pakai constructor default
        Music lagu1 = new Music();
        lagu1.tampilkanInfo();

        // Object 2 dengan parameter
        Music lagu2 = new Music("Thinking Out Loud", "Ed Sheeran", "Pop");
        lagu2.tampilkanInfo(true);

        // Object 3 dengan parameter
        Music lagu3 = new Music("The Scientist", "Coldplay", "Alternative Rock");
        lagu3.tampilkanInfo(false);
    }
}