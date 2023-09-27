package org.example.gameLogic;

import org.example.entity.Enemy;
import org.example.entity.Player;
import org.example.entity.Position;

/**
* @author Yue Zhu
* this function handles the interaction between an enemy and a player position relation 
* @param player The player
* @param enemy The enemy
* @return True if the player is nearby the enemy.
*/

public class Approch {

    public boolean isNearby (Player player, Enemy enemy){
        //data preparation:
        Position positionPlayer = player.getPosition();
        Position positionEnemy= enemy.getPosition();
        double distance = calculateDistance(positionPlayer, positionEnemy);
        double standard = Math.sqrt(2);
        if(distance<=standard){
            return true;
        }
        else{
            return false;
        }

    }

    public double calculateDistance (Position p1, Position p2){
        int x1 = p1.getX();
        int y1 = p1.getY();
        int x2 = p2.getX();
        int y2 = p2.getY();

        double calculation = Math.sqrt(Math.pow((x1-x2),2) + Math.pow((y1 -y2), 2)); 
        return calculation;
    }
}
