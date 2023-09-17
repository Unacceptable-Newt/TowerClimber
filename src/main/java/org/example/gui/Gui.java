package org.example.gui;

import org.example.PersistentDataNames;
import org.example.belonging.Item;
import org.example.entity.*;
import org.example.gameLogic.Maze;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Austin Zerk, Yucheng Zhu
 *
 * Given a gameStateString, update the GUI frame on screen.
 */
public class Gui {
    private static char[][] rasteriseBlankGUI(int rows, int columns) {

        // The char matrix to return
        char[][] charsMatrix = new char[rows][columns];

        // Fill the char matrix with '.'
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                charsMatrix[row][column] = '.';
            }
        }

        return charsMatrix;
    }

    /**
     * @author Austin Zerk, Yucheng Zhu
     * Given all game objects, update the char "pixels" for the GUI.
     * @param gameObjects Game objects summarising all the object with game variables
     * @return A matrix of all char "pixels" to be displayed on GUI
     */
    public static char[][] rasterise(
            HashMap<PersistentDataNames, Object> gameObjects,
            int rows,
            int columns
    ) {
        // -- Create a blank GUI
        char[][] charsPixels = rasteriseBlankGUI(rows, columns);

        gameObjects.forEach((name, object) -> {
            if (name.equals(PersistentDataNames.PLAYER)) { // Add player to GUI
                Player player = (Player) object;

                // Get the player's position
                Position position = player.getPosition();
                int row = position.getY();
                int col = position.getX();

                charsPixels[row][col] = 'P';
            } else if (name.equals(PersistentDataNames.ENEMIES)) { // Add player to GUI
                HashMap<Position, Enemy> enemies = (HashMap<Position, Enemy>) object;

                enemies.forEach( (position, enemy) -> {
                    int row = position.getY();
                    int col = position.getX();
                    charsPixels[row][col] = 'e';
                });
            // FIXME: Change it to TREASURES?
            } else if (name.equals(PersistentDataNames.INVENTORY)) { // Add items to GUI
                HashMap<Position,Item> items = (HashMap<Position, Item>) object;

                items.forEach(((position, item) -> {
                    int row = position.getY();
                    int col = position.getX();
                    charsPixels[row][col] = 'I';
                }));
            } else if (name.equals(PersistentDataNames.NPCS)) { // Add NPCs to GUI
                HashMap<Position, NPC> NPCs = (HashMap<Position, NPC>) object;

                NPCs.forEach((position, npc) -> {
                    int row = position.getY();
                    int col = position.getX();
                    charsPixels[row][col] = 'n';
                });
            } else if (name.equals(PersistentDataNames.WALL)) { // Add walls to GUI
                ArrayList<Wall> walls = (ArrayList<Wall>) object;

                walls.forEach(wall -> {
                    Position position = wall.getStart();
                    if (wall.isUp()){
                        for (int i = position.getY(); i < wall.getLength() + position.getY(); i++) {
                            charsPixels[i][position.getX()] = '#';
                        }
                    } else {
                        for (int i = position.getX(); i < wall.getLength() + position.getX(); i++) {
                            charsPixels[position.getY()][i] = '#';
                        }
                    }
                });
            } else if (name.equals(PersistentDataNames.EXIT)) { // Add Exit to GUI
                Position exit = (Position) object;

                int row = exit.getY();
                int col = exit.getX();
                charsPixels[row][col] = 'X';
            }
        });

        return charsPixels;
    }

    /**
     * @author Austin Zerk, Yucheng Zhu
     * Return strings to be displayed in GUI when a movement key is pressed
     * @param maze the maze to be turned into a string
     * @return a string representing the state of the maze after the move
     */
    public static String updateGuiString(Maze maze) {

        HashMap<PersistentDataNames, Object> gameObjects = new HashMap<>();
        gameObjects.put(PersistentDataNames.PLAYER, maze.getPlayer());
        gameObjects.put(PersistentDataNames.ENEMIES, maze.getEnemies());
        gameObjects.put(PersistentDataNames.INVENTORY, maze.getItems());
        gameObjects.put(PersistentDataNames.NPCS, maze.getNPCs());
        gameObjects.put(PersistentDataNames.WALL, maze.getEncodedWalls());
        gameObjects.put(PersistentDataNames.EXIT, maze.getExit());

        char[][] rasterise = Gui.rasterise(gameObjects, maze.getRows(), maze.getColumns());
        return Gui.flatten(rasterise) +
                "\nKing George's Chief Councillor: 'Tis the fifth time that the princess was kidnapped! " +
                "Methinks a great mystery surrounds her. " +
                "Oh mighty hero, thou art the last hope of our land and ... the 5689th person to accept his majesty's missions.";
    }

    /**
     * @author Yucheng Zhu
     * Turn a char matrix into a continuous string to be displayed in the GUI.
     * @param charsPixels A char matrix for all the char "pixels" displayed on the GUI
     * @return String to be displayed in GUI
     */
    public static String flatten(char[][] charsPixels) {

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
    public static void paint(char[][] charsPixels) {
        // FIXME
    }

    /**
     * @author
     * Generate a gameStateString for the GUI.
     * gameStateString can also be used for tests.
     * @param gameObjects All game objects containing persistent data
     * @return  gameStateString A string summarising all the current game variables
     */
    public static String generateGameStateString(Object[] gameObjects) {
        // FIXME
        return null;
    }
}
