package org.example.belonging;

/**
 * The `Weapon` class represents a weapon object in the game.
 * It tells  about the weapon's name, price, and attack value.
 *
 * @author Rong Sun
 *
 */
public class Weapon extends Item {

    private int attackValue;

    public int getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(Integer attackValue) {
        this.attackValue = attackValue;
    }

    public Weapon(String name, int price, int weight) {
        super(name, price, weight);
    }

    public Weapon(String name, int price, int weight, int attackValue) {
        super(name, price, weight);
        this.attackValue = attackValue;
    }
}
