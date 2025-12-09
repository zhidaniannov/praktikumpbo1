package com.ppbo.game;

public interface Character {
    void attack(Character target);
    void takeDamage(int damage);
    boolean isDead();
    String getName();
}
