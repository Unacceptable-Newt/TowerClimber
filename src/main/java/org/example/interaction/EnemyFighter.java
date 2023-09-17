package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.entity.Enemy;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Maze;
import org.example.move.Movement;
import org.example.util.Pair;

/**
 * @author
 * Initialises a fight with an adjacent monster
 */
public class EnemyFighter implements Interaction {

    /**
     * @author
     *
     * Interacts with an adjacent enemy to fight him/her/it and changes the player and his inventory in the process.
     * @param inventory The player's inventory.
     * @param maze The maze.
     *
     * @return Modified player and inventory
     */
    @Override
    public Pair<Player, Inventory> interactWithAdjacent(
            Inventory inventory, Maze maze
    ) {
        // Data preparation for battle.
        int playerAttack = maze.getPlayer().getAttack();

        Movement movement = new Movement();
        Position enemyPosition = movement.getFrontalPosition(maze.getPlayer().getDirection(),
                maze.getPlayer().getPosition());
        Enemy enemy = maze.getEnemyAtPosition(enemyPosition);

        if (enemy != null) {
            // turn 1, the player attack first
            enemy.defend(playerAttack);
            if (enemy.getHealth() <= 0) {
                maze.getEnemies().remove(enemyPosition);
                return null;
            }
            maze.getPlayer().defend(enemy.getAttack());
        }

        return new Pair<>(maze.getPlayer(),inventory); // enemy lives
    }
}
