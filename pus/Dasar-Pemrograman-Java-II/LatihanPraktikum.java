public class LatihanPraktikum {

    static class Charwuwa {
        String nama;
        int level;
        String weapon;

        Charwuwa() {
            this.nama = "unknown resonator";
            this.level = 1;
            this.weapon = "Heavy sword";
        }

        Charwuwa(String nama) {
            this.nama = nama;
            this.level = 1;
            this.weapon = "Heavy sword";
        }

        void tampilkanInfo() {
            System.out.println("Nama: " + nama);
            System.out.println("Level: " + level);
            System.out.println("Weapon: " + weapon);
        }

        void tampilkanInfo(boolean simple) {
            if (simple) {
                System.out.println("=== Resonator Info ===");
                System.out.println("Nama   : " + nama);
                System.out.println("Level  : " + level);
                System.out.println("Weapon : " + weapon);
                System.out.println("======================");
            } else {
                tampilkanInfo();
            }
        }

    }

    public static void main(String[] args) {
        Charwuwa char1 = new Charwuwa();
        Charwuwa char2 = new Charwuwa("Augusta");

        char1.tampilkanInfo();
        System.out.println();
        char2.tampilkanInfo(true);
    }

}
