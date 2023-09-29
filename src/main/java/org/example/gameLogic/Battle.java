package org.example.gameLogic;

import org.example.entity.Enemy;
import org.example.entity.Player;

/*
 * this is the basic framework for the battle system.
 * it should be updated if the game uses the turn system 
 */
public class Battle {

    /**
     * @author Yue Zhu
     * this function handles the interaction between an enemy and a player
     * @param player The player
     * @param enemy The enemy
     * @return True if the enemy dies. Otherwise, return false.
     */
     public static boolean processFights(Player player , Enemy enemy){

        // Data preparation for battle.
        int playerAttack = player.getAttack();
        int weaponAttack;

        if (player.getCurrentWeapon() != null) {
            weaponAttack = player.getCurrentWeapon().getAttackValue();
        } else {
            weaponAttack = 0;
        }

        int actualAttack = playerAttack + weaponAttack; //use the current weapon to defeat enemy

        int enemyAttack = enemy.getAttack();

        // turn 1, the player attack first
        enemy.defend(actualAttack);
        
        if (enemy.getHealth() <= 0) {
             return true; // enemy dies
        } else {
        player.defend(enemyAttack);
            return false; // enemy lives
        }
    }
}
