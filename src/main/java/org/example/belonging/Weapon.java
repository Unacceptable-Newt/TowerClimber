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

    public Weapon(String weaponName, Integer price, Integer attackValue) {
        this.name = weaponName;
        this.price = price;
        this.attackValue = attackValue;
    }
}