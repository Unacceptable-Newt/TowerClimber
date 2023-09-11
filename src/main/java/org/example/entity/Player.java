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

    private Position position;
    public Player(Integer money, Integer health, Integer level, Position position) {
        super(money,health,position);
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


}
