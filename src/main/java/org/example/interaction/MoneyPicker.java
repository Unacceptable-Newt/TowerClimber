package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.belonging.Item;
import org.example.entity.Player;
import org.example.gameLogic.Maze;
import org.example.util.Pair;

/**
 * @author Rong Sun
 * Initialises picking up adjacent money
 */
public class MoneyPicker  {
    /**
     * @author Rong Sun
     *
     * Interacts with an adjacent money to pick it up and changes the player and his inventory in the process.
     * @param maze The maze.
     * @return Modified player
     */

/*    public Player interactWithAdjacent(Maze maze) {
        // get the player's current position's item
        Integer pickedMoney = maze.pickMoney(maze.getPlayer().getPosition());
        // if there is item in that position
        if (pickedMoney != null) {
            // add the money into the inventory
            // Integer updatedMoney = inventory.addMoney(gainedMoney);
            maze.getPlayer().addMoney(pickedMoney);
        }
        // return the new player and inventory
        return maze.getPlayer();
    }*/

}
