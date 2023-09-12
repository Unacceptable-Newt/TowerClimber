package org.example;

import org.example.gameLogic.Level;
import org.example.gui.Display;

/**
 * @author
 * Runs the game
 */
public class Game {
    // When the `MAX_GAME_LEVEL` is exceeded, the game ends.
    private final static int MIN_GAME_LEVEL = 1;
    private final static int MAX_GAME_LEVEL = 3;

    /**
     * @author
     * Run a new game
     */
    public void runNewGame() {
        int currentGameLevel = MIN_GAME_LEVEL;
        while (currentGameLevel <= MAX_GAME_LEVEL) {
            Level currentLevel = new Level(currentGameLevel);
            currentGameLevel = currentLevel.play();
            currentLevel.save();
        }
    }

    /**
     * @author
     * Run from saved game
     */
    public void runSavedGame() {}

    /**
     * @author Yucheng Zhu
     * Run the game
     * @param args Typically nothing at the moment
     */
    public static void main(String[] args) {
        // Call the GUI
        Display gui = new Display();
    }
}