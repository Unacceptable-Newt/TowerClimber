package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.belonging.Item;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Maze;
import org.example.util.Pair;

/**
 * @author
 * Initialises picking up an adjacent item
 */
public class ItemPicker implements Interaction {
    Maze maze;

    /**
     * @author
     *
     * Interacts with an adjacent item to pick it up and changes the player and his inventory in the process.
     *
     * @param direction Which direction the player should move
     * @param level The level of the maze. (e.g. maze level 1, maze level 2, maze level 3)
     * @param player The player.
     * @param inventory The player's inventory.
     *
     * @return Modified player and inventory
     */
    @Override
    public Pair<Player, Inventory> interactWithAdjacent( Direction direction, int level, Player player, Inventory inventory) {
        // get the player's current position's item
        Item gainedItem = pickItem(direction, level, player);
        // if there is item in that position
        if (gainedItem != null) {
            // add the item into the inventory
            inventory.addItem(gainedItem);
        }
        // return the new player and inventory
        return new Pair<>(player, inventory);
    }

    // get the item from the maze
    private Item pickItem(Direction direction, int level, Player player) {

        maze.getItemAtPosition(player.getPosition());
        return null;
    }

}
