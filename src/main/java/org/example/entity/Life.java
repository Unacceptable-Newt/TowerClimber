package org.example.entity;

import org.example.interaction.Direction;

/**
 * @author Austin Zerk
 * @author Yucheng Zhu
 * @author Yue Zhu
 * An abstract superclass for all humans, monster and life-like creatures
 */
public abstract class Life {
    protected int money; // Each life has some (or zero) money. Monster's money will be given to the player upon death
    protected int maxHealth; // Full health. Cannot heal beyond that
    protected int health; // Current health.
    protected int attack; // Each life has an inborn attack value. The player's attack value is also modified by his weapon
    protected int defense; // Each life has an inborn defence value. The player's defence value is modified by his weapon

    protected Position position;
    protected Direction direction;

    /**
     * @author Austin Zerk
     * @author Yucheng Zhu
     * @author Yue Zhu
     * @param money Each life has some (or zero) money. Monster's money will be given to the player upon death
     * @param maxHealth Full health. Cannot heal beyond that
     * @param position The life's current position on the map
     * @param attack Each life has an inborn attack value. The player's attack value is also modified by his weapon
     */
    public Life(int money, int maxHealth, Position position, int attack){
        this.position = position;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
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

    /**
     * @author Austin Zerk
     * Take damage. Damage is first absorbed by defence.
     * If damage is greater than defence, deduct the health by their difference
     * Else, no damage is taken
     * e.g. if damage is 5 and defense is 2, deduct 3 health. If damage is 5 and defence is 6, deduct 0 health.
     * @param damage Incoming damage value
     */
    public void defend(int damage){
        this.health -= damage > defense ? damage - defense : 0;
    }

    public void attack(){}
}
