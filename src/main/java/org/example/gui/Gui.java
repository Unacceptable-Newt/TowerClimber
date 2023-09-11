package org.example.gui;

import org.example.PersistentDataNames;
import org.example.entity.Player;
import org.example.entity.Position;

import java.util.HashMap;

/**
 * @author Yucheng Zhu
 *
 * Given a gameStateString, update the GUI frame on screen.
 */
public class Gui {
    private char[][] rasteriseBlankGUI() {
        int rowsCount = 20; // Chars count in a line // TODO compute chars count from Maze size when Maze is implemented
        int columnsCount = 40; // lines number // TODO compute chars count from Maze size when Maze is implemented

        // The char matrix to return
        char[][] charsMatrix = new char[rowsCount][columnsCount];

        // Fill the char matrix with '.'
        for (int row = 0; row < rowsCount; row++) {
            for (int column = 0; column < columnsCount; column++) {
                charsMatrix[row][column] = '.';
            }
        }

        return charsMatrix;
    }

    /**
     * @author Yucheng Zhu
     * Given all game objects, update the char "pixels" for the GUI.
     * @param gameObjects Game objects summarising all the object with game variables
     * @return A matrix of all char "pixels" to be displayed on GUI
     */
    public char[][] rasterise(HashMap<PersistentDataNames, Object> gameObjects) {
        // FIXME

        // -- Create a blank GUI
        char[][] charsPixels = rasteriseBlankGUI();

        // -- Rasterise a player
        // Construct a player object
        if (gameObjects.containsKey(PersistentDataNames.PLAYER)) {
            Player player = (Player) gameObjects.get(PersistentDataNames.PLAYER);

            // Get the player's position
            Position position = player.getPosition();
            int row = position.getY();
            int col = position.getX();

            charsPixels[row][col] = 'P';
        }

        // -- Rasterise all items
        // FIXME

        // -- Rasterise all enemies
        // FIXME

        // -- Rasterise all NPCs
        // FIXME

        // -- Rasterise the exit to the next level
        // FIXME

        return charsPixels;
    }

    /**
     * @author Yucheng Zhu
     * Turn a char matrix into a continuous string to be displayed in GUI.
     * @param charsPixels A char matrix for all the char "pixels" displayed on the GUI
     * @return String to be displayed in GUI
     */
    public String flatten(char[][] charsPixels) {

        StringBuilder guiString = new StringBuilder();
        for (int row = 0; row < charsPixels.length; row++) {
            for (char c : charsPixels[row]) {
                guiString.append(c);
            }
            // Append new line
            if (row < charsPixels.length - 1) {
                guiString.append('\n');
            }
        }
        return guiString.toString();
    }

    /**
     * @author
     * Refresh the screen and display a new frame.
     * @param charsPixels A matrix of all char "pixels" to be displayed on GUI
     */
    public void paint(char[][] charsPixels) {
        // FIXME
    }

    /**
     * @author
     * Generate a gameStateString for the GUI.
     * gameStateString can also be used for tests.
     * @param gameObjects All game objects containing persistent data
     * @return  gameStateString A string summarising all the current game variables
     */
    public String generateGameStateString(Object[] gameObjects) {
        // FIXME
        return null;
    }
}
