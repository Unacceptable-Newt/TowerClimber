package org.example.gameLogic;

import org.example.move.Movement;
import org.example.belonging.Item;
import org.example.entity.*;
import org.example.interaction.Direction;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Austin Zerk
 * All objects in a maze.
 */
public class Maze {
    private boolean[][] walls;
    private ArrayList<Wall> encodedWalls = new ArrayList<>();
    private int columns;
    private int rows;
    private HashMap<Position,Integer> money = new HashMap<>();
    private Player player;
    private Position exit;
    private HashMap<Position, Item> Items = new HashMap<>();
    private HashMap<Position, NPC> NPCs = new HashMap<>();
    private HashMap<Position, Enemy> Enemies = new HashMap<>();
    private Position respawnPosition = new Position(2,2);

    /**
     * @author Austin Zerk
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
        for (int i = 0; i < ySize; i++){
            for (int j = 0; j < xSize; j++){
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
        } else {
            for (int i = pos.getX(); i < length + pos.getX() ; i++) {
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
    public boolean isWall(Position pos){
        return walls[pos.getY()][pos.getX()];
    }

    /**
     * @author Austin Zerk
     * creates a level one player at the specified position
     * @param pos position for the player to start at
     */
    public void createNewPlayer(Position pos){
        this.player = new Player(0, 10, 1, pos);

    }

    /**
     * @author Yucheng Zhu
     * Move player. Change his location in the maze.
     *
     * @param direction The direction object used to instruct which direction the player will move
     */
    public void movePlayer( Direction direction) {
        this.player = (Player) Movement.move(getPlayer(), direction, this);
    }

    /**
     * @author Austin Zerk
     * a simple getter for player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @author Yucheng Zhu
     * a simple setter for player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @author Austin Zerk
     * a simple getter for enimies list
     */
    public HashMap<Position, Enemy> getEnemies() {
        return Enemies;
    }

    /**
     * @author Austin Zerk
     * a simple getter for items
     */
    public HashMap<Position, Item> getItems() {
        return Items;
    }

    /**
     * @author Austin Zerk
     * a simple getter for NPCs
     */
    public HashMap<Position, NPC> getNPCs() {
        return NPCs;
    }

    /**
     * @author Austin Zerk
     * Adds a weapon to a position on the map
     * @param pos Position on the map to put weapon
     * @param item Weapon to put at location
     */
    public void addItem(Position pos, Item item){
        Items.put(pos,item);
    }

    /**
     * @author Austin Zerk
     * Gets Item at position and removes it from map
     * @param pos Position to get item from
     * @return Item at location. If there is no item, it returns null
     */
    public Item pickItemAtPosition(Position pos){
        return Items.remove(pos);
    }

    /**
     * @author Yucheng Zhu
     * Peeks Item at position. It stays at the map
     * @param pos Position to get item from
     * @return Item at location. If there is no item, it returns null
     */
    public Item getItemAtPosition(Position pos){
        return Items.get(pos);
    }

    /**
     * @author Austin Zerk
     * Adds an enemy on the map
     * @param enemy Weapon to put at location
     */
    public void addEnemy(Position position, Enemy enemy){
        Enemies.put(position, enemy);
    }

    /**
     * @author Austin Zerk
     * Gets Enemy at position
     * @param pos Position to get item from
     * @return Enemy at location. If there is no Enemy, it returns null
     */
    public Enemy getEnemyAtPosition(Position pos){
        return Enemies.get(pos);
    }

    /**
     * @author Austin Zerk
     * Adds a NPC on the map.
     * @param npc NPC to put at location
     */
    public void addNPC(NPC npc){
        NPCs.put(npc.getPosition(), npc);
    }

    /**
     * @author Austin Zerk
     * Gets NPC at position.
     * @param pos Position to get item from
     * @return NPC at location. If there is no NPC, it returns null
     */
    public NPC getNPCAtPosition(Position pos){
        return NPCs.get(pos);
    }

    /**
     * @author Austin Zerk
     * @return xSize of map
     */
    public int getColumns(){return columns;}

    /**
     * @author Austin Zerk
     * Get rows
     * @return ySize of map
     */
    public int getRows() {
        return rows;
    }

    /**
     * @author Austin Zerk
     * Get encoded walls
     * @return Encoded walls
     */
    public ArrayList<Wall> getEncodedWalls() {
        return encodedWalls;
    }

    /**
     * @author Austin Zerk
     * Get exit's location
     * @return The exit's location
     */
    public Position getExit() {
        return exit;
    }

    /**
     * @author Austin Zerk
     * Set exit's location.
     * @param exit The exit's location
     */
    public void setExit(Position exit) {
        this.exit = exit;
    }

    /**
     * @author Austin Zerk & Rong Sun
     * Get money on the maze's floor.
     * @return Money's location and value
     */
    public Integer pickMoney(Position pos) {
        return money.remove(pos);
    }

    /**
     * @author Austin Zerk
     * Add money to the maze's floor.
     * @param position Money's location
     * @param money Money's value
     */
    public void addMoney(Position position, int money){
        this.money.put(position, money);
    }

    /**
     * @author Rong Sun
     * a simple getter for all the money and their positions in the maze
     */
    public HashMap<Position, Integer> getMoney() {
        return money;
    }

    /**
     * @author Rong Sun
     * a simple setter for all the money and their positions in the maze
     * @param money the money will be shown in the maze
     */
    public void setMoney(HashMap<Position, Integer> money) {
        this.money = money;
    }

    /**
     * @author Rong Sun
     * a simple setter for all the items and their positions in the maze
     * @param items the items that will be shown in the maze
     */
    public void setItems(HashMap<Position, Item> items) {
        Items = items;
    }

    /**
     * @author Austin Zerk
     * @return position for the player to respawn at
     */
    public Position getRespawnPosition(){return respawnPosition;}

    /**
     * @author Austin Zerk
     * @param position the position to set to the respawn position
     */
    public void setRespawnPosition(Position position) {respawnPosition = position;}

    /**
     * @author xinchen
     * @param encodedWalls encoded walls to set in maze
     */

    public void setEncodedWalls(ArrayList<Wall> encodedWalls){
        this.encodedWalls = encodedWalls;
    }

}
