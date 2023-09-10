package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.entity.Player;
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
     *
     * @param direction Which direction the player should move
     * @param level The level of the maze. (e.g. maze level 1, maze level 2, maze level 3)
     * @param player The player.
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
