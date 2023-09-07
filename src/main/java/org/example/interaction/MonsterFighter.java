package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.entity.Player;
import org.example.entity.Position;

/**
 * @author
 * Initialises a fight with an adjacent monster
 */
public class MonsterFighter implements Interaction {
    @Override
    public void interactWithAdjacent(
            Direction direction, int level,
            Player player, Inventory inventory
    ) {
        // FIXME
    }
}
