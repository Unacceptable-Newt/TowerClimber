package org.example;

import java.util.HashMap;

/**
 * @author
 *
 * Given a gameStateString, update the GUI frame on screen.
 */
public class Gui {
    private char[][] rasteriseBlankGUI() {
        // FIXME
        return null;
    }

    /**
     * @author
     * Given all game objects, update the char "pixels" for the GUI.
     * @param gameObjects Game objects summarising all the object with game variables
     * @param previousCharsPixels Char "pixels" before the change
     * @return A matrix of all char "pixels" to be displayed on GUI
     */
    public char[][] rasterise(HashMap<String, Object> gameObjects, char[][] previousCharsPixels) {
        // FIXME
        return null;
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
