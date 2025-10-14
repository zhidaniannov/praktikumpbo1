package com.ppbo.game;

public class Player implements Character {
    protected String namaPlayer;
    protected int healthPoint;
    protected int atkPower;
    protected int experiencePoint;

    public Player(String namaPlayer, int healthPoint, int atkPower, int experiencePoint) {
        this.namaPlayer = namaPlayer;
        this.healthPoint = healthPoint;
        this.atkPower = atkPower;
        this.experiencePoint = experiencePoint;
    }

    @Override
    public void attack(Character target) {
        System.out.println(namaPlayer + " menyerang " + target.getName() + " dengan kekuatan " + atkPower);
        target.takeDamage(atkPower);
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

    public void gainExp(int exp) {
        experiencePoint += exp;
        System.out.println(namaPlayer + " mendapatkan " + exp + " EXP. Total EXP: " + experiencePoint);
    }
}
