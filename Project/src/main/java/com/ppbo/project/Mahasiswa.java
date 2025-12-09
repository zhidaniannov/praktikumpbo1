package com.ppbo.project;

import java.util.ArrayList;
import java.util.List;

public class Mahasiswa {
    private String nama;
    private String nim;
    private String prodi;
    private String jenisKelamin;
    private boolean isActive;

    public Mahasiswa(String nama, String nim, String prodi, String jenisKelamin, boolean isActive) {
        this.nama = nama;
        this.nim = nim;
        this.prodi = prodi;
        this.jenisKelamin = jenisKelamin;
        this.isActive = isActive;
    }

    // ------- DB sederhana (List static) -------
    public static final List<Mahasiswa> mahasiswas = new ArrayList<>();

    public static Mahasiswa findByNim(String nim) {
        for (Mahasiswa m : mahasiswas) {
            if (m.getNim().equals(nim))
                return m;
        }
        return null;
    }

    public static boolean existsByNim(String nim) {
        return mahasiswas.stream().anyMatch(m -> m.nim.equals(nim));
    }

    public static boolean addIfNotExists(Mahasiswa m) {
        if (existsByNim(m.nim))
            return false;
        mahasiswas.add(m);
        return true;
    }

    public static boolean updateByNim(String oldNim, Mahasiswa updated) {
        for (int i = 0; i < mahasiswas.size(); i++) {
            if (mahasiswas.get(i).nim.equals(oldNim)) {
                if (!oldNim.equals(updated.nim) && existsByNim(updated.nim))
                    return false;
                mahasiswas.set(i, updated);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteByNim(String nim) {
        return mahasiswas.removeIf(m -> m.nim.equals(nim));
    }

    // ------- Getters / Setters -------
    public String getNama() {
        return nama;
    }

    public String getNim() {
        return nim;
    }

    public String getProdi() {
        return prodi;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public void setJenisKelamin(String jk) {
        this.jenisKelamin = jk;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }
}
