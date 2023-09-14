package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.entity.Player;
import org.example.gameLogic.Maze;
import org.example.util.Pair;

/**
 * @author Yucheng Zhu
 * Initialises an interaction with an adjacent person or entity
 */
interface Interaction {
    /**
     * @author Yucheng Zhu
     *
     * An empty method for interaction.
     * Implementing it to have different interactions with different NPCs and entities.
     *
     * @param player The player.
     * @param inventory The player's inventory.
     * @param maze The maze.
     *
     * @return Modified player and inventory
     */
    Pair<Player, Inventory> interactWithAdjacent(
            Player player, Inventory inventory, Maze maze
    );

}
