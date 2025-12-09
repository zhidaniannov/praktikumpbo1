package com.example.ppbo;

public class Anjing extends Hewan {
    public Anjing(String nama) {
        super(nama);
    }

    @Override
    void suara() {
        System.out.println("Guk guk!");
    }
}
