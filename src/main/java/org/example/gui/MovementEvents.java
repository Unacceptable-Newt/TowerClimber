package org.example.gui;

import org.example.move.Movement;
import org.example.gameLogic.Maze;
import org.example.interaction.Direction;

import java.awt.event.KeyEvent;

/**
 * @author Austin Zerk, Yucheng Zhu
 * Handle all key events related to movements.
 */
public class MovementEvents {
    /**
     * @author Yucheng Zhu
     * Return strings to be displayed in GUI when a movement key is pressed
     * @param keyCode Key being pressed
     * @param movement Movement object
     * @param maze Maze object
     * @return Text to be displayed on the GUI
     */
    public static String setGuiTextOnMovementKeysPressed(int keyCode, Movement movement, Maze maze) {
        String guiText = null; // Default value: No key pressed

        Direction direction = null;
        switch (keyCode) {
            case KeyEvent.VK_W -> direction = Direction.UP;
            case KeyEvent.VK_S -> direction = Direction.DOWN;
            case KeyEvent.VK_A -> direction = Direction.LEFT;
            case KeyEvent.VK_D -> direction = Direction.RIGHT;
        }

        if (direction != null) {
            guiText = updateGuiStringOnMovementKeyPressed(movement, maze, direction);
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
    public static String updateGuiStringOnMovementKeyPressed(Movement movement, Maze maze, Direction direction) {
        maze.movePlayer(movement, direction);
        return Gui.updateGuiString(maze);
    }
}
