package org.example.gameLogic;

import org.example.belonging.Item;
import org.example.entity.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author
 * All objects in a maze.
 */
public class Maze {
    private boolean[][] walls;
    private ArrayList<Wall> encodedWalls = new ArrayList<>();
    private int columns;
    private int rows;
    private HashMap<Position,Integer> money = new HashMap<Position, Integer>();
    private Player player;
    private Position exit;
    private HashMap<Position, Item> Items = new HashMap<>();
    private HashMap<Position, NPC> NPCs = new HashMap<>();
    private HashMap<Position, Enemy> Enemies = new HashMap<>();

    /**
     * @author Austin Zerk u6648099
     * This is the creator of maze and takes an x and y size of the maze then sets the walls to be that size
     * and sets all the values to false
     * @param xSize the length of the horizontal axis of the walls
     * @param ySize the length of the vertical axis of the walls
     */
    public Maze(int xSize, int ySize, Position exit){
        this.walls = new boolean[ySize][xSize];
        this.exit = exit;
        this.columns = xSize;
        this.rows = ySize;
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
     * @param down whether to go left or down from the starting position
     */
    public void addWall(Position pos, int length, boolean down){
        encodedWalls.add(new Wall(length,pos,down));
        if (pos.getY() > walls.length || pos.getX() > walls[0].length)
            return;
        if (down){
            for (int i = pos.getY(); i < length + pos.getY(); i++) {
                walls[i][pos.getX()] = true;
            }
        }else {
            for (int i = pos.getX(); i < length + pos.getX() ; i++) {
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
    public HashMap<Position, Enemy> getEnemies() {
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
    public HashMap<Position, NPC> getNPCs() {
        return NPCs;
    }

    /**
     * @author Austin Zerk, u6648099
     * adds a weapon to a position on the map
     * @param pos position on the map to put weapon
     * @param item weapon to put at location
     */
    public void addItem(Position pos, Item item){
        Items.put(pos,item);
    }

    /**
     * @author Austin Zerk, u6648099
     * gets Item at position and removes it from map
     * @param pos position to get item from
     * @return item at location if there is no item there returns null
     */
    public Item getItemAtPosition(Position pos){
        return Items.remove(pos);
    }

    /**
     * @author Austin Zerk, u6648099
     * adds an enemy on the map
     * @param enemy weapon to put at location
     */
    public void addEnemy(Position position, Enemy enemy){
        Enemies.put(position, enemy);
    }

    /**
     * @author Austin Zerk, u6648099
     * gets Enemy at position and removes it from map
     * @param pos position to get item from
     * @return Enemy at location if there is no Enemy there returns null
     */
    public Enemy getEnemyAtPosition(Position pos){
        return Enemies.get(pos);
    }

    /**
     * adds a NPC on the map
     * @param npc NPC to put at location
     */
    public void addNPC(Position position, NPC npc){
        NPCs.put(position, npc);
    }

    /**
     * @author Austin Zerk, u6648099
     * gets NPC at position and removes it from map
     * @param pos position to get item from
     * @return NPC at location if there is no NPC there returns null
     */
    public NPC getNPCAtPosition(Position pos){
        return NPCs.get(pos);
    }

    /**
     * @author Austin Zerk, u6648099
     * @return xSize of map
     */
    public int getColumns(){return columns;}

    /**
     * @author Austin Zerk, u6648099
     * @return ySize of map
     */
    public int getRows() {
        return rows;
    }

    public ArrayList<Wall> getEncodedWalls() {
        return encodedWalls;
    }

    public Position getExit() {
        return exit;
    }
    public void setExit(Position exit) {
        this.exit = exit;
    }

    public HashMap<Position, Integer> getMoney() {
        return money;
    }

    public void addMoney(Position position, int money){
        this.money.put(position, money);
    }
}
