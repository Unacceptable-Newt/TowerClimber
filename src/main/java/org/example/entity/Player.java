package org.example.entity;

import org.example.belonging.Weapon;

/**
 * The Player class represent user's charactor, and he/she will have money, health, and player level
 * if player was attacked, player's health will decrease
 * .....
 *  @author Rong Sun
 *
 */
public class Player {

    private Integer money;
    private Integer health;
    private Integer level;

    private Weapon currentWeapon;

    private Position position;

    public Player(Integer money, Integer health, Integer level, Position position) {
        this.money = money;
        this.health = health;
        this.level = level;
        this.position = position;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void getAttacked(){
        defense();
    }

    public void defense(){

    }

    public void attack(){

    }



}
