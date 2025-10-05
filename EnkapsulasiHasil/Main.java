public class Main 
{
    public static void main(String[] args) {
        Produk p1 = new Produk("Laptop", 12000000, 5);
        Produk p2 = new Produk("HP", 3500000, 10);

        System.out.println("Nama produk 1 : " + p1.nama);
        
        System.out.println("Harga : " + p1.getHarga());

        p1.setHarga(7000000);
        System.out.println("Harga baru untuk produk 1 : " + p1.getHarga());

        
        p1.namaSupplierFix();
        p1.tampilkanInfo();
        p2.tampilkanInfo();

        Produk.infoJumlahProduk();
    }
}