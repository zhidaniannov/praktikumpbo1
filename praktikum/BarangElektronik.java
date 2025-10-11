public class BarangElektronik extends Produk {
    private int garansi;

    public BarangElektronik(String nama, int harga, int garansi) {
        super(nama, harga);
        this.garansi = garansi;
    }

    public void tampilkanGaransi(){
        System.out.println("garansi: " + garansi + " Bulan ");
    }


    @Override
    public double hitungPajak(){
        return harga * 0.01;
    }

    @Override
    public double hitungHarga(){
        return harga * 1.05;
    }
}