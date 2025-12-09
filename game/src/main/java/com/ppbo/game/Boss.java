package com.ppbo.game;

public class Boss extends Enemy {
    private final int experienceReward;

    private int passiveStack = 0;
    private int totalDamageReceived = 0;
    private boolean burst = false;
    private int burstDamage = 0;
    public Boss(String namaEnemy, int healthPoint, int atkPower, int experienceReward) {
        super(namaEnemy, healthPoint, atkPower);
        this.experienceReward = experienceReward;
    }

    @Override
    public void takeDamage(int damage) {
        this.healthPoint -= damage;
        System.out.println(namaEnemy + " menerima " + damage + " damage. Sisa HP: " + healthPoint);

        totalDamageReceived += damage;
        passiveStack++;

        if (passiveStack == 5) {
            burstDamage = (int) (totalDamageReceived * 0.20);
            burst = true;

            this.atkPower = burstDamage;

            System.out.println("stack penuh (5/5)!");
            System.out.println("Attack power boss diperbarui menjadi " + this.atkPower);

            passiveStack = 0;
        } else {
            System.out.println(namaEnemy + " mengumpulkan stack (" + passiveStack + "/5)");
        }
    }

    @Override
    public void attack(Character target) {
        if (burst) {
            System.out.println("Burst aktif! " + namaEnemy + " memberikan " + burstDamage
                    + " damage");
            target.takeDamage(burstDamage);
            burst = false;
        }

        System.out.println(namaEnemy + " menyerang " + target.getName() + "!");
        target.takeDamage(this.atkPower);
    }

    public int getExperienceReward() {
        return experienceReward;
    }
}