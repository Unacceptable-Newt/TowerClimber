package org.example.entity;

import org.example.belonging.Weapon;
import org.example.interaction.Direction;

/**
 * An abstract superclass for all humans, monster and life-like creatures
 */
public abstract class Life {
    protected int money;
    protected int health;
    protected int attack;
    protected int defense;

    protected Position position;
    protected Direction direction;

    public Life(int money, int health, Position position){
        this.position = position;
        this.health = health;
        this.money = money;

        this.direction = Direction.UP;
    }
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

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getAttack(){
        return attack;
    }

    public int getDefense(){
        return defense;
    }

    public void defense(){

    }

    public void attack(){

    }
}
