package org.example.entity;

import org.example.belonging.Inventory;
import org.example.belonging.Weapon;

/**
 * The Player class represent user's character, and he/she will have money, health, and player level
 * if player was attacked, player's health will decrease
 *  @author Rong Sun
 *
 */
public class Player extends Life {
    /**
     * @author Rong Sun
     * The current weapon equipped by the player.
     */
    Weapon currentWeapon;

    /**
     * @author Rong Sun
     * The level of the player.
     */
    int level;

    /**
     * Constructs a new player with the specified initial money, health, level, and position.
     * @author Rong Sun
     * @param money    The initial amount of money the player has.
     * @param maxHealth   The initial health points of the player.
     * @param level    The initial level of the player.
     * @param position The initial position of the player.
     */
    public Player(int money, int maxHealth, int level, Position position) {
        super(money, maxHealth, position, 4);
        this.level = level;
    }

    /**
     * Gets the current weapon equipped by the player.
     * @author Rong Sun
     * @return The current weapon equipped by the player.
     */
    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    /**
     * Sets the current weapon equipped by the player.
     * @author Rong Sun
     * @param currentWeapon The new weapon to equip.
     */
    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    /**
     * Gets the level of the player.
     * @author Rong Sun
     * @return The level of the player.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the level of the player.
     * @author Rong Sun
     * @param level The new level to set for the player.
     */
    public void setLevel(int level) {
        this.level = level;
    }


    /**
     * @author Rong Sun
     * once player choose an item from the inventory, player's current item will changed
     * @param itemName chosen item's name
     * @return true if the newItem can be used, which means it is in the inventory system
     */
    public boolean changeItem(String itemName, Inventory inventory){
        if(inventory.hasItem(itemName )){
            currentWeapon = (Weapon) inventory.getItems().get(itemName);
        }
        return false;
    }



    /**
     * @author Rong Sun
     * Put extra money to the player's possession
     * @param earnedMoney The extra money to add to the inventory
     */
    public int addMoney(int earnedMoney) {
        return this.money += earnedMoney;
    }

    /**
     * @author Rong Sun
     * Deduct money from the player's property
     * @param lostMoney The extra money to deduct to the inventory
     */
    public Integer deductMoney(int lostMoney) {
        return this.money -= lostMoney;
    }



}
