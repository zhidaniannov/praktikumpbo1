package com.ppbo.game;

public class Goblin extends Enemy {
    private final int experienceReward;
    private int shield;

    public Goblin(String namaEnemy, int healthPoint, int atkPower, int experienceReward, int shield) {
        super(namaEnemy, healthPoint, atkPower);
        this.experienceReward = experienceReward;
        this.shield = shield;
    }

    @Override
    public void takeDamage(int damage) {
        if (shield > 0) {
            shield -= (int) (damage * 0.8);
            System.out.println(namaEnemy + "Shield aktif, mengurangi dmg 20%. damage diterima" + (int) (damage * 0.8));
        } else {
            System.out.println("Shield hancur");
            healthPoint -= damage;
            System.out.println(namaEnemy + " menerima " + damage + " damage. Sisa HP: " + healthPoint);
        }
    }

    @Override
    public void attack(Character target) {
        System.out.println(namaEnemy + " menyerang " + target.getName() + "!");
        target.takeDamage(this.atkPower);
    }

    public int getExperienceReward() {
        return experienceReward;
    }
}