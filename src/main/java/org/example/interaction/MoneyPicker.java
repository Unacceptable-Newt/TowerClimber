package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.entity.Player;
import org.example.gameLogic.Maze;
import org.example.util.Pair;

/**
 * @author
 * Initialises picking up adjacent money
 */
public class MoneyPicker implements Interaction {
    /**
     * @author
     *
     * Interacts with an adjacent money to pick it up and changes the player and his inventory in the process.
     *
     * @param player The player.
     * @param inventory The player's inventory.
     * @param maze The maze.
     *
     * @return Modified player and inventory
     */
    @Override
    public Pair<Player, Inventory> interactWithAdjacent(Player player, Inventory inventory, Maze maze) {
        return null;
    }
}
