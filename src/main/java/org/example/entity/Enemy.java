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
    private String name;
    private final static int NORMAL_ENEMY_GOLD = 5;

/**
 * @author Yue Zhu, Austin Zerk, Rong Sun
 * Create a new enemy
 * @param attack The attack value
 * @param health The health value
 * @param defense The defense value
 */
    public Enemy(String name ,int attack, int health, int defense) {
        super(100, health, new Position(5, 4), attack);
        this.name =name;
        this.attack =attack;
        this.defense = defense;
        this.money = NORMAL_ENEMY_GOLD;
    }

    public String getName(){
        return name;
    }

    public int getHealth() {
        return health;
    }


    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

   

    public String enemyStatistics (Enemy enemy){
     String outPutString = "The Enemy you are facing is called " + getName() + ". The attack of the enemy is " + getAttack() + ". The Dfense of the enemy is " + getDefense()+ ". Its health is " + getHealth() + "." ;
     return outPutString;
    }



}
