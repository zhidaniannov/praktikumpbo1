public class Bank 
{
    private String namaBank;
    private String singkatanBank;
    private static int jumlahBank;

    public Bank(String namaBank, String singkatanBank) 
    {
        this.namaBank = namaBank;
        this.singkatanBank = singkatanBank;
        jumlahBank++;
    }

    public void tampilkanInfoBank() 
    {
        System.out.println("Nama Bank: " + namaBank);
        System.out.println("Singkatan Bank: " + singkatanBank);
    }

    public void jumlahBank() 
    {
        System.out.println("Jumlah Bank: " + jumlahBank);
    }

}