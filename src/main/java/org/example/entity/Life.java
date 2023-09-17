package org.example.entity;

import org.example.interaction.Direction;

/**
 * @author Austin Zerk, Yucheng Zhu, Yue Zhu
 * An abstract superclass for all humans, monster and life-like creatures
 */
public abstract class Life {
    protected int money;
    protected int health;
    protected int attack;
    protected int defense;

    protected Position position;
    protected Direction direction;

    public Life(int money, int health, Position position,int attack){
        this.position = position;
        this.health = health;
        this.money = money;
        this.attack = attack;
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

    public void setAttack(int attack) {this.attack = attack;}

    public int getDefense(){
        return defense;
    }

    public void defend(int damage){
        this.health -= damage > defense ? damage - defense : 0;
    }

    public void attack(){

    }
}
