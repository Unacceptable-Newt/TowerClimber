package org.example.entity;

import org.example.belonging.Inventory;
import org.example.belonging.Weapon;

/**
 * The Player class represent user's charactor, and he/she will have money, health, and player level
 * if player was attacked, player's health will decrease
 * .....
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

    public Player(Integer money, Integer health, Integer level, Position position) {
        super(money, health, position);
        this.level = level;
    }

    /**
     * @author Rong Sun
     * Handles the player getting attacked by invoking the defense method.
     */
    public void getAttacked(){
        defense();
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
     * put extra money to the player's property
     * @param money The extra money to add to the inventory
     */
    public Integer addMoney(Integer money) {
        return this.money+=money;
    }

    /**
     * @author Rong Sun
     * deduct money to the player's property
     * @param money The extra money to deduct to the inventory
     */
    public Integer deductMoney(Integer money) {
        return this.money-=money;
    }


}
