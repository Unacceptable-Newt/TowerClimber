package org.example.gameLogic;

import org.example.entity.Position;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author
 * All objects in a maze.
 */
public class Maze {
    private boolean[][] walls;

    /**
     * @author Austin Zerk u6648099
     * This is the creator of maze and takes an x and y size of the maze then sets the walls to be that size
     * and sets all the values to false
     * @param xSize the length of the horizontal axis of the walls
     * @param ySize the length of the vertical axis of the walls
     */
    public Maze(int xSize, int ySize){
        walls = new boolean[ySize][xSize];
        for (int i = 0; i < xSize; i++){
            for (int j = 0; j < ySize; j++){
                walls[i][j] = false;
            }
        }
    }

    /**
     * @author Austin Zerk
     * This function adds a wall to the current walls array
     * this is done by having a starting position then setting all the bools in the walls array from the
     * starting position either left or down for depending on weather up is set for length characters
     * it will just set values to true regardless of what was in the position
     * @param pos starting position of the wall segment
     * @param length length of the wall segment
     * @param up whether to go left or down from the starting position
     */
    public void add_wall(Position pos, int length, boolean up){
        if (pos.getY() > walls.length || pos.getX() > walls[0].length)
            return;
        if (up){
            for (int i = pos.getY(); i <= length + pos.getY(); i++) {
                walls[i][pos.getX()] = true;
            }
        }else {
            for (int i = pos.getX(); i <= length + pos.getX() ; i++) {
                walls[pos.getY()][i] = true;
            }
        }
    }

    /**
     * @author Austin Zerk
     * This function chechs if there is a wall at a given position
     * @param pos the position to check
     * @return true if there is a wall false otherwise
     */
    public boolean is_wall(Position pos){
        return walls[pos.getY()][pos.getX()];
    }
}
