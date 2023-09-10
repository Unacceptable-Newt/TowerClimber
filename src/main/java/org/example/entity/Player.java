package org.example.entity;

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
        this.money = money;
        this.health = health;
        this.level = level;
        this.position = position;
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


}
