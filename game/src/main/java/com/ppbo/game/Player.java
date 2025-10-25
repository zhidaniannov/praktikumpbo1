package com.ppbo.game;

public class Player implements Character {
    protected String namaPlayer;
    protected int healthPoint;
    protected int atkPower;

    public Player(String namaPlayer, int healthPoint, int atkPower) {
        this.namaPlayer = namaPlayer;
        this.healthPoint = healthPoint;
        this.atkPower = atkPower;
    }

    @Override
    public void attack(Character target) {
        if (Math.random() < 0.5) { // 80% chance to hit
            System.out.println(namaPlayer + " menyerang " + target.getName() + " dengan critical " + (atkPower * 2));
            target.takeDamage(atkPower * 2);
        } else {
            System.out.println(namaPlayer + " menyerang " + target.getName() + " dengan kekuatan " + atkPower);
            target.takeDamage(atkPower);
        }
    }

    @Override
    public void takeDamage(int damage) {
        healthPoint -= damage;
        System.out.println(namaPlayer + " menerima " + damage + " damage. Sisa HP: " + healthPoint);
    }

    @Override
    public boolean isDead() {
        return healthPoint <= 0;
    }

    @Override
    public String getName() {
        return namaPlayer;
    }

    public void heal() {
        healthPoint += 15;
        System.out.println(namaPlayer + " menggunakan potion dan memulihkan 15 HP. Total HP: " + healthPoint);
    }
}
