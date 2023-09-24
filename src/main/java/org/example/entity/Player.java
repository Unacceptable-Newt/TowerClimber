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


    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }


    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }


    public int getLevel() {
        return level;
    }


    public void setLevel(int level) {
        this.level = level;
    }


    /**
     * @author Rong Sun
     * once player choose an item from the inventory, player's current item will changed
     * @param itemName chosen item's name
     * @return true if the newItem can be choose
     */
    public boolean changeItem(String itemName, Inventory inventory){
        if(inventory.hasItem(itemName )){
            currentWeapon = (Weapon) inventory.getItems().get(itemName);
        }
        return false;
    }



    /**
     * @author Rong Sun
     * Put new earned money to the player's purse
     * @param earnedMoney The new added money to add to the inventory
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
