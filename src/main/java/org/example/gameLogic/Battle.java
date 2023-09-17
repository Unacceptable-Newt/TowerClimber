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
     * @param player player attacking ememy
     * @param enemy enemy being attacked
     * @return weather or not the enemy dies
     */
    public boolean process(Player player , Enemy enemy){

        // Data preparation for battle.
        int playerAttack = player.getAttack();

        int enemyAttack = enemy.getAttack();

        // turn 1, the player attack first
        enemy.defend(playerAttack);
        if (enemy.getHealth() <= 0)
            return true; // enemy dies
        player.defend(enemyAttack);

        return false; // enemy lives
    }


}
