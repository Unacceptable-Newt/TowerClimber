package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.entity.Player;
import org.example.entity.Position;

/**
 * @author
 * Initialises picking up an adjacent item
 */
public class ItemPicker implements Interaction {
    @Override
    public void interactWithAdjacent(
            Direction direction, int level,
            Player player, Inventory inventory
    ) {
        // FIXME
    }
}
