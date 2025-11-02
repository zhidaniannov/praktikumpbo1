/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prakpbo.main;
import java.util.ArrayList;

/**
 *
 * @author asus
 */
public class Mahasiswa 
{
    private String nama;
    private String nim;
    private String prodi;
    private String jenisKelamin;
    private boolean isActive;

    public Mahasiswa(String nama, String nim, String prodi, String jenisKelamin, boolean isActive) 
    {
        this.nama = nama;
        this.nim = nim;
        this.prodi = prodi;
        this.jenisKelamin = jenisKelamin;
        this.isActive = isActive;
    }

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

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
    public static ArrayList<Mahasiswa> mahasiswas = new ArrayList<>();

}

