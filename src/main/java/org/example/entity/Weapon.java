package org.example.entity;

/**
 * The `Weapon` class represents a weapon object in the game.
 * It tells  about the weapon's name, price, and attack value.
 *
 * @author [Rong Sun]
 * @version 1.0
 */
public class Weapon {
    private String weaponName;

    private Integer price;

    private Integer attackValue;

    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = weaponName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAttackValue() {
        return attackValue;
    }

    public void setAttackValue(Integer attackValue) {
        this.attackValue = attackValue;
    }

    public Weapon(String weaponName, Integer price, Integer attackValue) {
        this.weaponName = weaponName;
        this.price = price;
        this.attackValue = attackValue;
    }
}
