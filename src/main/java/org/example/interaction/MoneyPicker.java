package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.entity.Player;
import org.example.entity.Position;

/**
 * @author
 * Initialises picking up adjacent money
 */
public class MoneyPicker implements Interaction {
    @Override
    public void interactWithAdjacent(
            Direction direction, int level,
            Player player, Inventory inventory
    ) {
        // FIXME
    }
}
