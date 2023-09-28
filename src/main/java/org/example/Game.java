package org.example;

import org.example.gui.Display;

/**
 * @author Yucheng Zhu
 * Runs the game
 */
public class Game {

    // Game GUI size
    private final static int WIDTH = 1024;
    private final static int HEIGHT = 800;

    /**
     * @author Yucheng Zhu
     * Run the game
     * @param args Typically nothing at the moment
     */
    public static void main(String[] args) {
        // Call the GUI
        new Display(WIDTH, HEIGHT);

    }
}
