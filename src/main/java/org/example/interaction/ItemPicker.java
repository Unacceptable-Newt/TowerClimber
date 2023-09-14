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


    /**
     * @author RS
     *
     * Interacts with an adjacent item to pick it up and changes the player and his inventory in the process.

     * @param player The player.
     * @param inventory The player's inventory.
     * @param maze The maze.
     *
     * @return Modified player and inventory
     */
    @Override
    public Pair<Player, Inventory> interactWithAdjacent(Player player, Inventory inventory, Maze maze) {
        // get the player's current position's item
        Item gainedItem = pickItem( player, maze);
        // if there is item in that position
        if (gainedItem != null) {
            // add the item into the inventory
            inventory.addItem(gainedItem);
        }
        // return the new player and inventory
        return new Pair<>(player, inventory);
    }

    // get the item from the maze
    private Item pickItem( Player player, Maze maze) {

        maze.getItemAtPosition(player.getPosition());
        return null;
    }

}
