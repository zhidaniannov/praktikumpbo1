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
}
