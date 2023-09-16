package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.belonging.Item;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Maze;
import org.example.util.Pair;

/**
 * @author Rong Sun
 * Initialises picking up an adjacent item
 */
public class ItemPicker implements Interaction {


    /**
     * @author Rong Sun
     * Interacts with an adjacent item to pick it up and changes the player and his inventory in the process.
     * @param inventory The player's inventory.
     * @param maze The maze.
     *
     * @return Modified player and inventory
     */
    @Override
    public Pair<Player, Inventory> interactWithAdjacent(Inventory inventory, Maze maze) {
                Player player = maze.getPlayer();
       Position playerPosition =  player.getPosition();
       boolean conditionofX = false;
       boolean conditionofY = false;
        // get the player's current position's item
        for (Position position : maze.getItems().keySet()) {
            conditionofX = Math.abs(playerPosition.getX() - position.getX()) == 1 && playerPosition.getY() == position.getY();
            // If the absolute value of the difference between the x or y coordinates of two locations is both 1, then they are adjacent
            /*if (Math.abs(playerPosition.getX() - position.getX()) == 1 && playerPosition.getY() == position.getY()) {

            }*/

            if (Math.abs(playerPosition.getY() - position.getY()) == 1 && playerPosition.getX() == position.getX()) {

            }
        }



        Item gainedItem = maze.pickItemAtPosition(maze.getPlayer().getPosition());
        // if there is item in that position
        if (gainedItem != null) {
            // add the item into the inventory
            inventory.addItem(gainedItem);
        }
        // return the new player and inventory
        return new Pair<>(maze.getPlayer(), inventory);
    }





}
