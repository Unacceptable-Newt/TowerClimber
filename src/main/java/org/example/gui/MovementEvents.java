package org.example.gui;

import org.example.gameLogic.Maze;
import org.example.interaction.Direction;

import java.awt.event.KeyEvent;
import java.util.HashSet;

/**
 * @author Austin Zerk, Yucheng Zhu
 * Handle all key events related to movements.
 */
public class MovementEvents {
    /**
     * @author Yucheng Zhu
     * Set a set of movement keys so that a key can be checked more easily
     * @return All movement keys
     */
    public static HashSet<Integer> setMovementKeys() {
        HashSet<Integer> movementKeys = new HashSet<>();
        movementKeys.add(KeyEvent.VK_W);
        movementKeys.add(KeyEvent.VK_S);
        movementKeys.add(KeyEvent.VK_A);
        movementKeys.add(KeyEvent.VK_D);

        return movementKeys;
    }

    /**
     * @author Yucheng Zhu
     * Check whether a key is in a set of key (e.g. one of the WSAD movement keys)
     * @return True if it is. Otherwise, false.
     */
    public static boolean isInKeySet(HashSet<Integer> keySet, int key) {
        return keySet.contains(key);
    }

    /**
     * @author Yucheng Zhu
     * Return strings to be displayed in GUI when a movement key is pressed
     * @param keyCode Key being pressed
     * @param maze Maze object
     * @return Text to be displayed on the GUI
     */
    public static String setGuiTextOnMovementKeysPressed(int keyCode, Maze maze) {
        String guiText = null; // Default value: No key pressed

        Direction direction = null;
        switch (keyCode) {
            case KeyEvent.VK_W -> direction = Direction.UP;
            case KeyEvent.VK_S -> direction = Direction.DOWN;
            case KeyEvent.VK_A -> direction = Direction.LEFT;
            case KeyEvent.VK_D -> direction = Direction.RIGHT;
        }

        if (direction != null) {
            guiText = updateGuiStringOnMovementKeyPressed(maze, direction);
        }
        return guiText;
    }

    /**
     * @author Austin Zerk, Yucheng Zhu
     * Return strings to be displayed in GUI when a movement key is pressed
     * @param maze the maze to be turned into a string
     * @param direction the direction the player is moving
     * @return a string representing the state of the maze after the move
     */
    public static String updateGuiStringOnMovementKeyPressed(Maze maze, Direction direction) {
        maze.movePlayer(direction);
        return Gui.updateGuiString(maze);
    }
}
