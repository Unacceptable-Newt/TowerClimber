package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.entity.Enemy;
import org.example.entity.Player;
import org.example.entity.Position;
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
     * @author Austin Zerk, Yucheng Zhu
     * Fights the enemy for one turn (i.e. the player hits the enemy, and then he/she/it retaliates).
     * The enemy will lose health. And if he/she/it survives, the player will also lose health.
     * The player can interact with the enemy repeated to complete the fight.
     *
     * If the player wins in this turn, he gains loot, and the enemy disappear.
     * If the enemy wins in this turn, the player respawns, and the enemy regains the full health
     *
     * Changes the player and his inventory in the process.
     *
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

        Position enemyPosition = Movement.getFrontalPosition(
                maze.getPlayer().getDirection(),
                maze.getPlayer().getPosition()
        );
        Enemy enemy = maze.getEnemyAtPosition(enemyPosition);

        if (enemy != null) {
            // The player attacks first
            enemy.defend(playerAttack);
            if (enemy.getHealth() <= 0) {
                // The enemy dies
                maze.getEnemies().remove(enemyPosition);

                // Get loot
                maze.getPlayer().addMoney(enemy.getMoney());

                // You win :)
                return new Pair<>(maze.getPlayer(), inventory);
            }

            // The enemy retaliates
            maze.getPlayer().defend(enemy.getAttack());
            if (maze.getPlayer().getHealth() <= 0) {
                // FIXME: Respawn the player

                // The enemy regains the full health
//                maze.getEnemies().get(enemyPosition).restoreFullHealth();

                // You died :(
                return new Pair<>(maze.getPlayer(), inventory);
            }
        }

        return new Pair<>(maze.getPlayer(), inventory); // No enemy here
    }
}
