import java.util.Scanner;

public class PraktikumEmpat {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Masukkan nilai = ");
        int nilai = scanner.nextInt();
        if (nilai > 75) {

            System.out.println("Anda lulus ujian ");

        } else {

            System.out.println("Anda harus mengulang ujian ");
        }
    }

    public static void IfElse(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Masukkan nilai : ");
        int nilai = scanner.nextInt();
        if (nilai > 75) {

            System.out.println("Anda lulus ujian ");

        } else if (nilai > 65) {

            System.out.println("Nilai anda berada di ambang batas kelulusan");

        } else {

            System.out.println("Anda harus mengulang ujian ");
        }
    }

    public static void Switch(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Masukkan warna (RGB)= ");
        String warna = scanner.nextLine();

        switch (warna) {
            case "R":
                System.out.println("Anda memasukkan warna merah");
                break;

            case "G":
                System.out.println("Anda memasukkan warna hijau");
                break;

            case "B":
                System.out.println("Anda memasukkan warna biru");
                break;
            default:
                System.out.println("Warna tidak tersedia");
                break;
        }
    }

    public static void NestedIf(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Masukkan angka: ");
        int angka = scanner.nextInt();

        if (angka >= 0) {

            System.out.println("Bilangan bernilai positif");

            if (angka % 2 == 0) {

                System.out.println("dan genap");

            } else {
                System.out.println("dan ganjil");
            }
        } else if (angka == 0) {

            System.out.println("Bilangan bernilai netral ");

        } else {
            System.out.println("Bilangan bernilai negatif ");

            if (angka % 2 == 0) {
                System.out.println("dan genap");
            } else {
                System.out.println("dan ganjil");
            }
        }

    }

    public static void ForLoop(String[] args) {

        int i;
        for (i = 1; i < 5; i++) {
            System.out.println("perulangan ke " + i);
        }

        String[] mahasiswa = { "Andi", "Budi", "Citra", "Dewi", "Eko" };

        for (int a = 0; i > mahasiswa.length; a++) {
            System.out.println("Mahasiswa ke " + (a + 1) + ": " + mahasiswa[a]);
        }

    }

    public static void While(String[] args) {

        int a = 10;
        while (a <= 5) {

            System.out.println("While loop ke " + a);

        }

        Scanner scanner = new Scanner(System.in);
        String password = "";

        while (!password.equals("java123")) {

            System.out.println("Masukkan Password: ");
            password = scanner.nextLine();

        }
        System.out.println("Login berhasil");

    }

    public static void DoWhile(String[] args) {
        int x = 6;
        do {
            System.out.println("Do whilee loop ke- " + x);
            x++;
        } while (x <= 5);
    }

    public static void NestedLoop(String[] args) {

        System.out.println("---Mencetak nomor kursi di bioskop---");
        char kursi = 'A';
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j <= 5; j++) {
                System.out.println(kursi + "" + j + "");
            }
            System.out.println();
            kursi++;
        }

    }

    public static void Break(String[] args) {
        for (int d = 1; d <= 10; d++) {
            if (d == 5) {
                System.out.println("Break di angka- " + d);
                break;
            }
            System.out.println("Angka: " + d);
        }
    }

    public static void Continue(String[] args) {
        
        for(int e = 1; e <=10; e++){
            if (e % 2 == 0) {
                continue;
            }
            System.out.println("Bilangan ganjil : " +e);
        }

    }

}
