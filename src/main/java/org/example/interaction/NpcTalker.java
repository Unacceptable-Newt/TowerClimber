package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.entity.Player;
import org.example.gameLogic.Maze;
import org.example.util.Pair;

/**
 * @author
 * Initialises a talk with an adjacent NPC
 */
public class NpcTalker implements Interaction{
    /**
     * @author
     *
     * Interacts with an adjacent NPC to talk to him/her and changes the player and his inventory in the process.
     * @param player The player.
     * @param inventory The player's inventory.
     * @param maze The maze.
     *
     * @return Modified player and inventory
     */
    @Override
    public Pair<Player, Inventory> interactWithAdjacent(
            Player player, Inventory inventory, Maze maze
    ) {
        // FIXME
        return null;
    }
}
