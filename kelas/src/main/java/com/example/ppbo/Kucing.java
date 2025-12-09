package com.example.ppbo;

public class Kucing extends Hewan {
    public Kucing(String nama) {
        super(nama);
    }

    @Override
    void suara() {
        System.out.println("Meong...");
    }
}
