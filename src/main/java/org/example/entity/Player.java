package org.example.entity;

import org.example.belonging.Inventory;
import org.example.belonging.Item;
import org.example.belonging.Weapon;

/**
 * The Player class represent user's charactor, and he/she will have money, health, and player level
 * if player was attacked, player's health will decrease
 * .....
 *  @author Rong Sun
 *
 */
public class Player extends Life {
    Weapon currentWeapon;
    int level;

    public Player(Integer money, Integer health, Integer level, Position position) {
        super(money, health, position);
        this.level = level;
    }

    public void getAttacked(){
        defense();
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
     * once player choose an item from the inventory, player's current item will changed
     * @param newItem chosen item
     * @return true if the newItem can be used, which means it is in the inventory system
     */
    public boolean changeItem(Item newItem, Inventory inventory){
        if(inventory.hasItem(newItem )){
            currentWeapon = (Weapon) newItem;
        }
        return false;
    }
}
