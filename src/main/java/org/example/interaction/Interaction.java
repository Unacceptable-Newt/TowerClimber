package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.entity.Player;

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
     * @param direction Which direction the player should move
     * @param level The level of the maze. (e.g. maze level 1, maze level 2, maze level 3)
     * @param playerCurrentPosition The player's current position in the maze.
     */
    void interactWithAdjacent(
            Direction direction, int level,
            Player player, Inventory inventory
    );

}
