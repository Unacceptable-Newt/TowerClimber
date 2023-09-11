package org.example.gui;

import org.example.Movement;
import org.example.PersistentDataNames;
import org.example.belonging.Item;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Maze;
import org.example.interaction.Direction;
import org.example.util.Pair;

import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * @author Yucheng Zhu
 * Handle all key events related to movements.
 */
public class MovementEvents {
    /**
     * @author Yucheng Zhu
     * Return strings to be displayed in GUI when a movement key is pressed
     * @param keyCode Key being pressed
     * @param player Player object
     * @param movement Movement object
     * @param maze Maze object
     * @param gui Gui object
     * @return Text to be displayed on the GUI
     */
    public String setGuiTextOnMovementKeysPressed(int keyCode, Player player, Movement movement, Maze maze, Gui gui) {
        String guiText = null; // Default value: No key pressed

        Direction direction = null;
        switch (keyCode) {
            case KeyEvent.VK_W -> direction = Direction.UP;
            case KeyEvent.VK_S -> direction = Direction.DOWN;
            case KeyEvent.VK_A -> direction = Direction.LEFT;
            case KeyEvent.VK_D -> direction = Direction.RIGHT;
        }

        if (direction != null) {
            guiText = updateGuiStringOnMovementKeyPressed(player, movement, maze, gui, direction);
        }
        return guiText;
    }

    /**
     * @author Yucheng Zhu
     * Return strings to be displayed in GUI when a movement key is pressed
     * @param player PlayerObject
     */
    public String updateGuiStringOnMovementKeyPressed(Player player, Movement movement, Maze maze, Gui gui, Direction direction) {
        player = (Player) movement.move(player, direction, maze);

        HashMap<PersistentDataNames, Object> gameObjects = new HashMap<>();
        gameObjects.put(PersistentDataNames.PLAYER, player);
        gameObjects.put(PersistentDataNames.ENEMIES,maze.getEnemies());
        gameObjects.put(PersistentDataNames.INVENTORY,maze.getItems());
        gameObjects.put(PersistentDataNames.NPCS,maze.getNPCs());
        gameObjects.put(PersistentDataNames.WALL,maze.getEncodedWalls());

        char[][] rasterise = gui.rasterise(gameObjects, maze.getySize(), maze.getxSize());
        return gui.flatten(rasterise);
    }
}
