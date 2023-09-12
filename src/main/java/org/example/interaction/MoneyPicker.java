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
    @Override
    public Pair<Player, Inventory> interactWithAdjacent(Player player, Inventory inventory, Maze maze) {
        return null;
    }
    /**
     * @author
     *
     * Interacts with an adjacent money to pick it up and changes the player and his inventory in the process.
     *
     * @param direction Which direction the player should move
     * @param level The level of the maze. (e.g. maze level 1, maze level 2, maze level 3)
     * @param player The player.
     * @param inventory The player's inventory.
     *
     * @return Modified player and inventory
  */
}
