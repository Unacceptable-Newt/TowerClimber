package org.example.entity;

import org.example.interaction.Direction;

/**
 * @author Austin Zerk, Yucheng Zhu, Yue Zhu
 * An abstract superclass for all humans, monster and life-like creatures
 */
public abstract class Life {
    protected int money;
    protected int maxHealth; // Full health. Cannot heal beyond that
    protected int health; // Current health.
    protected int attack;
    protected int defense;
    

    protected Position position;
    protected Direction direction;

    public Life(int money, int maxHealth, Position position, int attack,int defense){
        this.money = money;
        this.position = position;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.attack = attack;
        this.defense = defense;
        this.direction = Direction.UP;
    }
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int max_health) {
        this.maxHealth = max_health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * @author Yucheng Zhu
     * Restore the life to its max health.
     * Used in respawning.
     */
    public void restoreFullHealth() {
        setHealth(maxHealth);
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
