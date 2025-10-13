public class BarangElekronik extends Produk 
{
    private int garansi;

    public BarangElekronik(String nama, int harga, int garansi) 
    {
        super(nama, harga);
        this.garansi = garansi;
    }

    public void tampilkanGaransi() 
    {
        System.out.println("Garansi: " + garansi + " bulan");
    }

}