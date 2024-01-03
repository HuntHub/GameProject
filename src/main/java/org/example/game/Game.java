package org.example.game;

import org.example.characters.Character;
import org.example.characters.UserCharacter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private List<Character> characters;
    private UserCharacter userCharacter;
    private Scanner scanner;
    private boolean isBoss1Defeated = false;

    public Game() {
        initializeCharacters();
        scanner = new Scanner(System.in);
    }

    private void initializeCharacters() {
        userCharacter = new UserCharacter(100, 20); // User character
        characters = new ArrayList<>();
        characters.add(userCharacter);
        characters.add(new Character("Enemy 1", 100, 15,0, 50));
        characters.add(new Character("Enemy 2", 100, 15,20, 75));
        characters.add(new Character("Boss 1", 100, 50, 50, 1000));
        // More enemies can be added here
    }

    public void play() {
        while (!isGameOver()) {
            Character enemy = findNextEnemy();
            if (enemy != null) {
                conductBattle(enemy);
                if (!userCharacter.isDefeated()) {
                    int coinsEarned = enemy.getCoinReward();
                    userCharacter.earnCoins(coinsEarned);
                    System.out.println("You earned " + coinsEarned + " coins! Total coins: " + userCharacter.getCoins());
                    shopPhase(); // Add shop phase after each battle
                }
            } else {
                System.out.println("All enemies defeated! You win!");
                break;
            }
        }
        scanner.close();
    }

    private Character findNextEnemy() {
        return characters.stream()
                .filter(character -> !character.equals(userCharacter) && !character.isDefeated())
                .findFirst()
                .orElse(null);
    }

    private void conductBattle(Character enemy) {
        System.out.println(enemy.getName() + " approaches!");

        while (!userCharacter.isDefeated() && !enemy.isDefeated()) {
            userTurn(enemy);
            if (!enemy.isDefeated()) {
                enemy.attack(userCharacter);
            }
            if (!userCharacter.isDefeated() && enemy.isDefeated() && enemy.getName().equals("Boss 1")) {
                isBoss1Defeated = true;
                System.out.println("Boss 1 defeated! New items unlocked in the shop.");
            }
            printCharacterStats();
        }

        if (userCharacter.isDefeated()) {
            System.out.println("You have been defeated. Game over.");
        }
    }

    private void shopPhase() {
        boolean shopping = true;

        while (shopping) {
            System.out.println("----------------------------------------");
            System.out.println("Welcome to the shop! Your coins: " + userCharacter.getCoins());
            System.out.println("1. Small Health Potion (+10 Health) - 10 coins");
            System.out.println("2. Medium Health Potion (+20 Health) - 25 coins");
            System.out.println("3. Large Health Potion (+30 Health) - 75 coins");
            System.out.println("4. Sword (+5 Attack Power) - 50 coins");
            if (isBoss1Defeated) {
                System.out.println("5. Longsword (+15 Attack Power) - 400 coins");
            }
            System.out.println("6. Exit shop");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    if (userCharacter.spendCoins(10)) userCharacter.heal(10);
                    System.out.println("You have purchased a Small Health Potion. Health increased!");
                    break;
                case 2:
                    if (userCharacter.spendCoins(25)) userCharacter.heal(20);
                    System.out.println("You have purchased a Medium Health Potion. Health increased!");
                    break;
                case 3:
                    if (userCharacter.spendCoins(75)) userCharacter.heal(30);
                    System.out.println("You have purchased a Large Health Potion. Health increased!");
                    break;
                case 4:
                    if (userCharacter.spendCoins(50)) userCharacter.increaseAttackPower(5);
                    System.out.println("You have purchased a Sword. Attack power increased!");
                    break;
                case 5:
                    if (userCharacter.spendCoins(400 )) userCharacter.increaseAttackPower(15);
                    System.out.println("You have purchased a Longsword. Attack power increased!");
                    break;
                case 6:
                    shopping = false; // Exit the shop
                    System.out.println("Exiting shop.");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }

            if (choice != 6) {
                System.out.println("Your remaining coins: " + userCharacter.getCoins());
                System.out.println("Your current health: " + userCharacter.getHealth());
                System.out.println("Your current attack power: " + userCharacter.getAttackPower());
            }
        }
    }

    private void userTurn(Character enemy) {
        System.out.println("Choose your attack style:");
        System.out.println("1. Normal Attack");
        System.out.println("2. Power Attack");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        UserCharacter.AttackStyle attackStyle = (choice == 2) ? UserCharacter.AttackStyle.POWER : UserCharacter.AttackStyle.NORMAL;
        userCharacter.setCurrentAttackStyle(attackStyle);
        userCharacter.attack(enemy);
    }

    private void printCharacterStats() {
        System.out.println("----------------------------------------");
        System.out.println(userCharacter.getName() + ": Health=" + userCharacter.getHealth() + ", Attack=" + userCharacter.getAttackPower());
    }

    private boolean isGameOver() {
        return userCharacter.isDefeated() || characters.stream().allMatch(Character::isDefeated);
    }
}