package com.example.ppbo;

public class abstrack {

    abstract class Hewan {
        String nama;

        Hewan(String nama) {
            this.nama = nama;

        }

        abstract void suara();

        void info() {
            System.out.println("Nama hewan: " + nama);
        }

    }

}
