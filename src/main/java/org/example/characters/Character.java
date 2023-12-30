package org.example.characters;
import java.util.Random;

public class Character {
    private static final Random random = new Random(); // Static instance of Random
    private String name;
    private int health;
    private int defense;
    private int coinReward;
    private int attackPower;
    private boolean defeated;

    public Character(String name, int health, int attackPower, int defense, int coinReward) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defeated = false;
        this.coinReward = coinReward;
        this.defense = defense;
    }

    public void attack(Character target) {
        int initialDamage = calculateDamage();
        int reducedDamage = target.calculateReceivedDamage(initialDamage);

        System.out.println(this.getName() + " attacks " + target.getName() + " for " + reducedDamage + " damage!");
        target.receiveDamage(reducedDamage);
    }

    protected int calculateDamage() {
        return random.nextInt(attackPower + 1);
    }
    protected int calculateReceivedDamage(int damage) {
        // Calculate damage after defense mitigation
        return Math.max(damage - (defense / 5), 0);
    }

    public void receiveDamage(int damage) {
        this.health -= damage;
        System.out.println(this.name + " takes " + damage + " damage.");

        if (this.health <= 0) {
            this.health = 0; // Ensure health doesn't go negative
            this.defeated = true;
            System.out.println(this.name + " has been defeated!");
        }
    }

    // Getters and setters
    public int getHealth() {
        return this.health;
    }

    public int getAttackPower() {
        return this.attackPower;
    }

    public String getName() {
        return this.name;
    }

    public boolean isDefeated() {
        return this.defeated;
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }

    protected void setHealth(int health) {
        this.health = health;
    }

    protected void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getCoinReward() {
        return coinReward;
    }
}