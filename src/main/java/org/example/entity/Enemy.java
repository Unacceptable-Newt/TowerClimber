package org.example.entity;

/**
 * The Enemy class represent the enemy that player can fight with.
 * Enemy can attack player and player's health will reduce the value of attack
 * player can attack Enemy and Enemy's health will reduce the value of attack
 *  @author Rong Sun, Yue Zhu 
 *
 */
public class Enemy extends Life {
    private int attack;
    private int health;
    private int defense;

/*some notes:
 *  added a simply function, getHealth
 *  set a defualt amount of money for the enemies so that the
 *  player could be rewared by defeating the enemy
 *  added a function, setHealth.
 * 
 */
    public Enemy(int attack, int health, int defense) {
        super(100,health,new Position(0,0)); 
        this.defense = defense;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth(){
        return health;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public void setHealth(int damage){
        this.health = health - damage;
    }
}
