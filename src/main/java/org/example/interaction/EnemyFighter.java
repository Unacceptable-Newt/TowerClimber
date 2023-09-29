package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.entity.Enemy;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Battle;
import org.example.gameLogic.Maze;
import org.example.move.Movement;
import org.example.util.Pair;

/**
 * @author Yue Zhu
 * @author Austin Zerk
 * @author Yucheng Zhu
 * Initialises a fight with an adjacent monster
 */
public class EnemyFighter implements Interaction {

    /**
     * @author Austin Zerk, Yucheng Zhu,Yue Zhu
     * Fights the enemy for one turn (i.e. the player hits the enemy, and then he/she/it retaliates).
     * The enemy will lose health. And if he/she/it survives, the player will also lose health.
     * The player can interact with the enemy repeated to complete the fight.
     *
     * If the player wins in this turn, he gains loot, and the enemy disappear.
     * If the enemy wins in this turn, the player respawns
     * Changes the player and his inventory in the process.
     *
     * @param inventory The player's inventory.
     * @param maze The maze.
     *
     * @return Modified player and inventory
     */
    @Override
    public Pair<Player, Inventory> interactWithAdjacent(Inventory inventory, Maze maze) {
        // Data preparation for battle.
        Position enemyPosition = Movement.getFrontalPosition(
                maze.getPlayer().getDirection(),
                maze.getPlayer().getPosition());
       
        Enemy enemy = maze.getEnemyAtPosition(enemyPosition);
        Player player = maze.getPlayer();

        if (enemy != null) {
           boolean winOrLoss = Battle.processFights(player, enemy);
           
           
           if (winOrLoss) {
                // In this case, the player wins
                maze.getEnemies().remove(enemyPosition); //remove defeated enemy
                maze.getPlayer().addMoney(enemy.getMoney()); // get loot
                return new Pair<>(maze.getPlayer(), inventory); // update the player and the inventory
           }
           
           else{  // The player will die and respawn
           Position respawnPosition = maze.getRespawnPosition();

           Player respwanedPlayer = respwanFunction(respawnPosition);

           maze.setPlayer(respwanedPlayer); // back to the start position 

           return new Pair<>(maze.getPlayer(), inventory);  // the inventory system remains unchanged.
           }
        }
        else {
            return new Pair<>(maze.getPlayer(), inventory); 
        }

       
    }

    public static Player respwanFunction(Position respawnPosition){
        Player respwanedPlayer = new Player(0, 10, 1, respawnPosition);
        return respwanedPlayer;
    }
}
