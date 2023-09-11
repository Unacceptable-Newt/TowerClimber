package org.example.entity;

import org.example.belonging.Weapon;

/**
 * An abstract superclass for all humans, monster and life-like creatures
 */
public abstract class Life {
    int money;
    int health;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }


    public void defense(){

    }

    public void attack(){

    }
}
