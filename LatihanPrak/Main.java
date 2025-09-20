public class Main 
{
    public static void main(String[] args) 
    {

        // Object 1 - pakai constructor default
        System.out.println("--- Laptop 1 ---");
        Laptop laptop1 = new Laptop();
        laptop1.tampilkanInfo();

        // Object 2 - dengan parameter
        System.out.println("\n--- Laptop 2 (Info Ringkas) ---");
        Laptop laptop2 = new Laptop("Dell XPS", "Intel i7", 16);
        laptop2.tampilkanInfo(true); 

        // Object 3 - dengan parameter
        System.out.println("\n--- Laptop 3 (Info Lengkap) ---");
        Laptop laptop3 = new Laptop("Asus ROG", "AMD Ryzen 9", 32);
        laptop3.tampilkanInfo(false); 
    }
}