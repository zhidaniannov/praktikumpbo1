package com.ppbo.enkapsulasi;

public class Jurusan {
    private String namaJurusan;
    private String kajur;
    private static int jumlahJurusan;

    public Jurusan(String namaJurusan, String kajur) {
        this.namaJurusan = namaJurusan;
        this.kajur = kajur;
        jumlahJurusan++;
    }

    public String getNamaJurusan() {
        return namaJurusan;
    }

    public void setNamaJurusan(String namaJurusan) {
        this.namaJurusan = namaJurusan;
    }

    public String getKajur() {
        return kajur;
    }

    public void setKajur(String kajur) {
        this.kajur = kajur;
    }

    public static int getJumlahJurusan() {
        return jumlahJurusan;
    }

    public void tampilkanInfoJurusan() {
        System.out.println("Nama Jurusan: " + namaJurusan);
        System.out.println("Kepala Jurusan: " + kajur);
    }
    
}
