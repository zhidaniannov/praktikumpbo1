package com.ppbo.game;
import java.util.Scanner;

import javax.sound.midi.SysexMessage;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan nama player: ");
        String namaPlayer =  scanner.nextLine();
        System.out.print("Masukkan HP player: ");
        int healthPoint =  scanner.nextInt();
        scanner.nextLine();
        System.out.print("Masukkan ATK player: ");
        int atkPower =  scanner.nextInt();
        scanner.nextLine();
        Player player = new Player(namaPlayer, healthPoint, atkPower);
        Goblin goblin = new Goblin("The Flying Dutchman", 1000, 10, 25, 100);

        System.out.println("=== RPG TURN-BASED GAME ===");
        System.out.println(player.getName() + " vs " + goblin.getName());
        System.out.println("============================");

        while (!player.isDead() && !goblin.isDead()) {
            System.out.println("\nAksi:");
            System.out.println("1. Serang");
            System.out.println("2. Heal");
            System.out.println("3. Keluar Game");
            System.out.print("Pilih: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    player.attack(goblin);
                    if (!goblin.isDead()) {
                        goblin.attack(player);
                    } else {
                        System.out.println(goblin.getName() + " dikalahkan!");
                    }
                    break;

                case 2:
                    player.heal();
                    goblin.attack(player);
                    break;

                case 3:
                    System.out.println("Game berakhir!");
                    return;

                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }

        if (player.isDead()) {
            System.out.println("\n=== Kamu Kalah! ===");
        } else {
            System.out.println("\n=== Kamu Menang! ===");
        }

        scanner.close();
    }
}
