package org.example.gameLogic;

import org.example.belonging.Item;
import org.example.belonging.Weapon;
import org.example.entity.Enemy;
import org.example.entity.NPC;
import org.example.entity.Player;
import org.example.entity.Position;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author
 * All objects in a maze.
 */
public class Maze {
    private boolean[][] walls;
    private int xSize;
    private int ySize;
    private Player player;
    private HashMap<Position, Item> Items = new HashMap<>();
    private ArrayList<NPC> NPCs = new ArrayList<>();
    private ArrayList<Enemy> Enemies = new ArrayList<>();

    /**
     * @author Austin Zerk u6648099
     * This is the creator of maze and takes an x and y size of the maze then sets the walls to be that size
     * and sets all the values to false
     * @param xSize the length of the horizontal axis of the walls
     * @param ySize the length of the vertical axis of the walls
     */
    public Maze(int xSize, int ySize){
        walls = new boolean[ySize][xSize];
        this.xSize = xSize;
        this.ySize = ySize;
        for (int i = 0; i < xSize; i++){
            for (int j = 0; j < ySize; j++){
                walls[i][j] = false;
            }
        }
    }

    /**
     * @author Austin Zerk u6648099
     * This function adds a wall to the current walls array
     * this is done by having a starting position then setting all the bools in the walls array from the
     * starting position either left or down for depending on weather up is set for length characters
     * it will just set values to true regardless of what was in the position
     * @param pos starting position of the wall segment
     * @param length length of the wall segment
     * @param up whether to go left or down from the starting position
     */
    public void addWall(Position pos, int length, boolean up){
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
     * @author Austin Zerk u6648099
     * This function chechs if there is a wall at a given position
     * @param pos the position to check
     * @return true if there is a wall false otherwise
     */
    public boolean isWall(Position pos){
        return walls[pos.getY()][pos.getX()];
    }

    /**
     * @author Austin Zerk u6648099
     * creates a level one player at the specified position
     * @param pos position for the player to start at
     */
    public void createNewPlayer(Position pos){
        this.player = new Player(0,10,1,pos);
    }

    /**
     * @author Austin Zerk
     * a simple getter for player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @author Austin Zerk, u6648099
     * a simple getter for enimies list
     */
    public ArrayList<Enemy> getEnemies() {
        return Enemies;
    }

    /**
     * @author Austin Zerk, u6648099
     * a simple getter for items
     */
    public HashMap<Position, Item> getItems() {
        return Items;
    }

    /**
     * @author Austin Zerk, u6648099
     * a simple getter for NPCs
     */
    public ArrayList<NPC> getNPCs() {
        return NPCs;
    }

    /**
     * adds a weapon to a position on the map
     * @param pos position on the map to put weapon
     * @param item weapon to put at location
     */
    public void addItem(Position pos, Item item){
        Items.put(pos,item);
    }

    /**
     * gets Item at position and removes it from map
     * @param pos position to get item from
     * @return item at location if there is no item there returns null
     */
    public Item getItemAtPosition(Position pos){
        return Items.remove(pos);
    }
}
