package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.entity.Player;
import org.example.util.Pair;

/**
 * @author
 * Initialises picking up an adjacent item
 */
public class ItemPicker implements Interaction {
    /**
     * @author
     *
     * Interacts with an adjacent item to pick it up and changes the player and his inventory in the process.
     *
     * @param direction Which direction the player should move
     * @param level The level of the maze. (e.g. maze level 1, maze level 2, maze level 3)
     * @param player The player's current position in the maze.
     * @param inventory The player's inventory.
     *
     * @return Modified player and inventory
     */
    @Override
    public Pair<Player, Inventory> interactWithAdjacent(
            Direction direction, int level,
            Player player, Inventory inventory
    ) {
        // FIXME
        return null;
    }
}
