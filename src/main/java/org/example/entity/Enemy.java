package org.example.entity;

/**
 * The Enemy class represent the enemy that player can fight with.
 * Enemy can attack player and player's health will reduce the value of attack
 * player can attack Enemy and Enemy's health will reduce the value of attack
 *  @author Rong Sun
 *
 */
public class Enemy extends Life {
    private int attack;
    private int health;
    private int defense;

    public Enemy(int attack, int health, int defense) {
        this.attack = attack;
        this.health = health;
        this.defense = defense;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }
}
