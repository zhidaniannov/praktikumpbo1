class Laptop 
{
    String merek;
    String prosesor;
    int ram;

    // Constructor default
    public Laptop() 
    {
        this.merek = "Unknown";
        this.prosesor = "Unknown";
        this.ram = 0;
    }

    // Constructor dengan parameter
    public Laptop(String merek, String prosesor, int ram) 
    {
        this.merek = merek;
        this.prosesor = prosesor;
        this.ram = ram;
    }

    // Method untuk menampilkan info lengkap
    public void tampilkanInfo() 
    {
        System.out.println("Merek: " + this.merek + " | Prosesor: " + this.prosesor + " | RAM: " + this.ram + "GB");
    }

    // Method Overloading dengan logika BARU
    public void tampilkanInfo(boolean infoProsesor) 
    {
        if (infoProsesor) 
        {
            // Jika true, tampilkan Merek + Prosesor
            System.out.println("Merek: " + this.merek + " | Prosesor: " + this.prosesor);
        } else {
            // Jika false, tampilkan Merek + RAM
            System.out.println("Merek: " + this.merek + " | RAM: " + this.ram + "GB");
        }
    }
}