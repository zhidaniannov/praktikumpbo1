package com.ppbo.game;

public class Enemy implements Character {
    protected String namaEnemy;
    protected int healthPoint;
    protected int atkPower;

    public Enemy(String namaEnemy, int healthPoint, int atkPower) {
        this.namaEnemy = namaEnemy;
        this.healthPoint = healthPoint;
        this.atkPower = atkPower;
    }

    @Override
    public void attack(Character target) {
        System.out.println(namaEnemy + " menyerang " + target.getName() + "!");
        target.takeDamage(atkPower);
    }

    @Override
    public void takeDamage(int damage) {
        healthPoint -= damage;
        System.out.println(namaEnemy + " menerima " + damage + " damage. Sisa HP: " + healthPoint);
    }

    @Override
    public boolean isDead() {
        return healthPoint <= 0;
    }

    @Override
    public String getName() {
        return namaEnemy;
    }
}
