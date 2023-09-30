package org.example;

import org.example.gui.Display;

/**
 * Runs the game
 * @author Yucheng Zhu
 */
public class Game {

    // Game GUI size
    private final static int WIDTH = 1024;
    private final static int HEIGHT = 800;

    // Win the game after MAX_LEVEL
    public final static int MIN_LEVEL = 1;
    public final static int MAX_LEVEL = 3;

    /**
     * Run the game
     * @author Yucheng Zhu
     * @param args Typically nothing at the moment
     */
    public static void main(String[] args) {

        if (args.length == 1 && args[0].equals("-terminal")) {
            Display.TextDisplay();
        } else {
            // Call the GUI
            new Display(WIDTH, HEIGHT);
        }
    }
}
