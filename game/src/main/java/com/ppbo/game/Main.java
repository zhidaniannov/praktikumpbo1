package com.ppbo.game;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Player player = new Player("Aya", 1500, 20, 0);
        Boss boss = new Boss("Bos", 1000, 10, 25);

        System.out.println("=== RPG TURN-BASED GAME ===");
        System.out.println(player.getName() + " vs " + boss.getName());
        System.out.println("============================");

        while (!player.isDead() && !boss.isDead()) {
            System.out.println("\nAksi:");
            System.out.println("1. Serang");
            System.out.println("2. Heal");
            System.out.println("3. Keluar Game");
            System.out.print("Pilih: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    player.attack(boss);
                    if (!boss.isDead()) {
                        boss.attack(player);
                    } else {
                        System.out.println(boss.getName() + " dikalahkan!");
                        player.gainExp(boss.getExperienceReward());
                    }
                    break;

                case 2:
                    player.heal();
                    boss.attack(player);
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
