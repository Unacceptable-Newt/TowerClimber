package org.example.entity;

/**
 * The Enemy class represent the enemy that player can fight with.
 * Enemy can attack player and player's health will reduce the value of attack
 * player can attack Enemy and Enemy's health will reduce the value of attack
 *  @author Rong Sun, Yue Zhu 
 *
 */
public class Enemy extends Life {
    private int defense;

/**
 * @author Yue Zhu, Austin Zerk, Rong Sun
 * Create a new enemy
 */
    public Enemy(int attack, int health, int defense) {
        super(100, health, new Position(0, 0), attack);
        this.defense = defense;
    }

    /**
     * @author Rong Sun
     * Get the enemy's defence
     * @return The enemy's defence value
     */
    public int getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

}
