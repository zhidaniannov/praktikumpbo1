package com.ppbo.enkapsulasi;

public class Fakultas {
    private String namaFakultas;
    private String dekan;
    private static int jumlahFakultas;

    public Fakultas(String namaFakultas, String dekan) {
        this.namaFakultas = namaFakultas;
        this.dekan = dekan;
        jumlahFakultas++;
    }

    public void tampilkanInfoFakultas() {
        System.out.println("Nama Fakultas: " + namaFakultas);
        System.out.println("Kode Fakultas: " + dekan);
    }

    public void jumlahFakultas() {
        System.out.println("Jumlah Fakultas: " + jumlahFakultas);
    }

}