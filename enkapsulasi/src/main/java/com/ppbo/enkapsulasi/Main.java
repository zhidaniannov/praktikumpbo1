package com.ppbo.enkapsulasi;

public class Main {
    public static void main(String[] args) {
        Fakultas fakultas1 = new Fakultas("Teknologi Informasi", "Dr. A");
        Fakultas fakultas2 = new Fakultas("Teknik", "Dr. B");
        System.out.println("==================================");
        fakultas1.tampilkanInfoFakultas();
        System.out.println("==================================");

        fakultas2.tampilkanInfoFakultas();
        System.out.println("==================================");

        fakultas1.jumlahFakultas();
        System.out.println("==================================\n");
        System.out.println("==================================");

        Jurusan jurusan1 = new Jurusan("Teknik Elektro", "Dr. C");
        Jurusan jurusan2 = new Jurusan("Teknik Mesin", "Dr. D");
        jurusan1.tampilkanInfoJurusan();
        System.out.println("==================================");
        jurusan2.tampilkanInfoJurusan();

        System.out.println("==================================");
        System.out.println("Jumlah Jurusan: " + Jurusan.getJumlahJurusan());
        System.out.println("==================================");
    }
}