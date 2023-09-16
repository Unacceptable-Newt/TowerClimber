package org.example.belonging;

/**
 * The `Weapon` class represents a weapon object in the game.
 * It tells  about the weapon's name, price, and attack value.
 *
 * @author Rong Sun
 *
 */
public class Weapon extends Item {

    /**@author Rong Sun
     * The attack value of the weapon, indicating the damage it can deal.
     */
    private int attackValue;

    /**
     * @author Rong Sun
     * Gets the attack value of the weapon.
     * @return The attack value of the weapon.
     */
    public int getAttackValue() {
        return attackValue;
    }

    /**
     * @author Rong Sun
     * Sets the attack value of the weapon.
     * @param attackValue The new attack value to set.
     */
    public void setAttackValue(Integer attackValue) {
        this.attackValue = attackValue;
    }

    /**
     * @author Rong Sun
     * Constructs a new weapon with the specified name, price, and weight.
     * @param name   The name of the weapon.
     * @param price  The price of the weapon.
     * @param weight The weight of the weapon.
     */
    public Weapon(String name, int price, int weight) {
        super(name, price, weight);
    }

    /**
     * @author Rong Sun
     * Constructs a new weapon with the specified name, price, weight, and attack value.
     * @param name        The name of the weapon.
     * @param price       The price of the weapon.
     * @param weight      The weight of the weapon.
     * @param attackValue The attack value of the weapon.
     */
    public Weapon(String name, int price, int weight, int attackValue) {
        super(name, price, weight);
        this.attackValue = attackValue;
    }

}
