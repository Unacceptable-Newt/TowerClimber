package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.belonging.Item;
import org.example.entity.Player;
import org.example.gameLogic.Maze;
import org.example.move.Movement;
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
     * @return New updated player and inventory
     */
    @Override
    public Pair<Player, Inventory> interactWithAdjacent(Inventory inventory, Maze maze) {
        //create the instance of movement
        Movement movement = new Movement();
        //get the front object
        Object frontalObject = movement.getPlayerFrontalObject(maze);
        //if the object is an istance of item, and if there is item in that position
        if(frontalObject instanceof Item){
            maze.pickItemAtPosition(movement.getFrontalPosition(maze.getPlayer().getDirection()
                    ,maze.getPlayer().getPosition()));


                // add the item into the inventory
                inventory.addItem((Item) frontalObject);

        }


        // return the new player and inventory
        return new Pair<>(maze.getPlayer(), inventory);
    }





}
