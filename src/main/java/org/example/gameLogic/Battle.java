package org.example.gameLogic;

import org.example.entity.Enemy;
import org.example.entity.Player;

/*
 * this is the basic framework for the battle system.
 * it should be updated if the game uses the turn system 
 */

public class Battle {
    
    
    public boolean process(Player player , Enemy enemy){

        // Data preparation for battle.
        int playerAttack = player.getAttack();
        int playerHealth = player.getHealth();
        int playerDefense = player.getDefense();

        int enemyAttack = enemy.getAttack();
        int enemyHealth = enemy.getHealth();
        int enemyDefense = enemy.getDefense();


        // turn 1, the player attack first
        if(actualHarm(playerAttack, enemyDefense) > 0){
            int damage = actualHarm(playerAttack, enemyDefense);
            enemy.setHealth(damage);
            int currentEH = enemy.getHealth();
            if(currentEH <= 0){
                return true;
            }else{
                return false; // this should be updated. Temporary settings
            }
        }

        else{
            return false; // this should be updated. Temporary settings
        }
    }

    public int actualHarm (int attacker, int defenser){
        if(attacker <= defenser){
            return 0;
        }
        else{
            return attacker - defenser;
        }
    }


}
