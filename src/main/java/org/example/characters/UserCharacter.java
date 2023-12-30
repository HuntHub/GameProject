package org.example.characters;
import java.util.Random;

public class UserCharacter extends Character {
    private static final Random random = new Random(); // Static instance of Random
    private int coins;
    public enum AttackStyle {
        NORMAL,
        POWER
    }

    private AttackStyle currentAttackStyle;

    public UserCharacter(int health, int attackPower) {
        super("User", health, attackPower, 30, 0);
        this.currentAttackStyle = AttackStyle.NORMAL; // Default to normal attack style
        this.coins = 0;
    }

    @Override
    public void attack(Character target) {
        int damage = calculateDamage();

        if (this.currentAttackStyle == AttackStyle.POWER) {
            damage *= 2; // Power attack does double damage
        }
        int reducedDamage = target.calculateReceivedDamage(damage);
        System.out.println("You use " + currentAttackStyle + " attack on " + target.getName() + " for " + reducedDamage + " damage!");
        target.receiveDamage(reducedDamage);
    }

    public void heal(int healthAmount) {
        int newHealth = getHealth() + healthAmount;
        if (newHealth > 100) { // Assuming 100 is the max health
            newHealth = 100;
        }
        setHealth(newHealth);
    }
    public void increaseAttackPower(int amount) {
        int newAttackPower = getAttackPower() + amount;
        setAttackPower(newAttackPower);
    }

    protected int calculateDamage() {
        return random.nextInt(getAttackPower() + 1);
    }

    // Getters and setters
    public AttackStyle getCurrentAttackStyle() {
        return this.currentAttackStyle;
    }

    public void setCurrentAttackStyle(AttackStyle attackStyle) {
        this.currentAttackStyle = attackStyle;
    }

    public void earnCoins(int amount) {
        this.coins += amount;
    }

    public boolean spendCoins(int amount) {
        if (this.coins >= amount) {
            this.coins -= amount;
            return true;
        } else {
            System.out.println("Not enough coins!");
            return false;
        }
    }

    // Getter for coins
    public int getCoins() {
        return this.coins;
    }
}
