package org.example.entity;

/**
 * The Enemy class represent the enemy that player can fight with.
 * Enemy can attack player and player's health will reduce the value of attack
 * player can attack Enemy and Enemy's health will reduce the value of attack
 *  @author Rong Sun, Yue Zhu
 *
 */
public class Enemy extends Life {
    private String name;
    private final static int NORMAL_ENEMY_GOLD = 5;

/**
 * @author Yue Zhu, Austin Zerk, Rong Sun
 * Create a new enemy
 * @param attack The attack value
 * @param health The health value
 * @param defense The defense value
 */
    public Enemy(String name ,int attack, int health, int defense, Position position) {
        super(100, health, position, attack, defense);
        this.name = name;
        this.money = NORMAL_ENEMY_GOLD;
    }

    public String getName(){
        return name;
    }

    /**
     * @author Yue Zhu
     * Return the enemy statistics string
     * @param enemy The enemy
     * @return The enemy statistics string
     */
    public String enemyStatistics (Enemy enemy){
     String outPutString = " "+ getName() + " ATT: " + getAttack() + " DEF: " + getDefense()+ " HP: " + getHealth() + "." ;
     return outPutString;
    }
}
