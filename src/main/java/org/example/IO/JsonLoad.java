package org.example.IO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Game;
import org.example.belonging.Inventory;
import org.example.belonging.Item;
import org.example.belonging.Weapon;
import org.example.entity.*;
import org.example.gameLogic.Level;
import org.example.gameLogic.Maze;
import org.example.interaction.Direction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * JSON loader
 * @author Xin Chen
 */
public class JsonLoad {

    private final String FOLDER_PATH = "src/cache/progress";
    private final String CUR_PROGRESS_FILE_PATH = FOLDER_PATH +"/current/";
    private final String PREFIX = "level";
    private final String CUR_INDICATOR = "_cur";
    private final String SUFFIX = ".json";


    /**
     * Check level num is valid or not
     * @author Xin Chen
     * @param level It should be the level number reading from loader or game
     * @return Boolean, true if it is between 1-3
     */
    private boolean checkLevel(int level) {
        return level >= Game.MIN_LEVEL && level <= Game.MAX_LEVEL;
    }

    /**
     * Check "levelX.json" and "levelX_cur.json", where X is num
     * @author Xin Chen
     * @param path Should be dir path to progress folder
     * @return A list of different level json files
     */
    public ArrayList<String> loadLevelList(String path){
        // Read the file in path as "levelX.json" or "levelX_cur.json".
        ArrayList<String> levelList = new ArrayList<>();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.matches("level\\d+\\.json") || fileName.matches("level\\d+_cur\\.json")) {
                        levelList.add(path + fileName);
                    }
                }
            }
        }
        return levelList;
    }

    /**
     * This function is used to pass the hashmap representing the position
     * @author Austin Zerk
     * @param positionMap a hashmap with the x and y as keys and their integer values as values
     * @return Position of the hashmap
     */
    private Position parsePosition(HashMap<String, Integer> positionMap) {
        int x = positionMap.get("x");
        int y = positionMap.get("y");
        return new Position(x, y);
    }

    private Position parsePosition(String encodedPosition) {
        String posStr = encodedPosition.substring(11);
        StringBuilder xStr = new StringBuilder();
        StringBuilder yStr = new StringBuilder();
        int reading = 1;
        for (char c : posStr.toCharArray()) {
            if (c == ',')
                reading = 0;
            if (c == '}')
                break;
            if (reading == 1) {
                xStr.append(c);
            } else if(reading == 2) {
                yStr.append(c);
            }
            if (c == '=')
                reading = 2;
        }
        int x = Integer.parseInt(xStr.toString());
        int y = Integer.parseInt(yStr.toString());
        return new Position(x, y);
    }

    private Enemy parseEnemy(HashMap<String, Object> encodedEnemies, Position position) {
        int attack = (int) encodedEnemies.get("attack");
        int defence = (int) encodedEnemies.get("defense");
        Direction dir = parseDirection((String) encodedEnemies.get("direction"));
        int health = (int) encodedEnemies.get("health");
        int money = (int) encodedEnemies.get("money");
        String name = (String) encodedEnemies.get("name");
        Enemy out = new Enemy(name, attack, health, defence, new Position(5, 4));
        out.setDirection(dir);
        out.setMoney(money);
        out.setPosition(parsePosition((HashMap<String, Integer>) encodedEnemies.get("position")));
        out.setPosition(position);
        return out;
    }

    private Direction parseDirection(String dir) {
        return switch (dir) {
            case ("UP") -> Direction.UP;
            case ("DOWN") -> Direction.DOWN;
            case ("LEFT") -> Direction.LEFT;
            case ("RIGHT") -> Direction.RIGHT;
            default -> null;
        };
    }

    private NPC parseNPC(HashMap<String, Object> encodedEnemy, Position position) {
        int money = (int) encodedEnemy.get("money");
        int health = (int) encodedEnemy.get("health");
        Direction direction = parseDirection((String) encodedEnemy.get("direction"));
        String name = (String) encodedEnemy.get("name");
        ArrayList<String> dialog = (ArrayList<String>) encodedEnemy.get("dialogue");
        NPC out = new NPC(name, position);
        out.setDirection(direction);
        out.setHealth(health);
        out.setMoney(money);
        out.setPosition(position);
        return out;
    }

    private Item parseItem(HashMap<String, Object> encodedItem) {
        int price = (int) encodedItem.get("price");
        int weight = (int) encodedItem.get("weight");
        String name = (String) encodedItem.get("name");
        if (encodedItem.containsKey("attackValue")){
            return new Weapon(name, price, weight, (Integer) encodedItem.get("attackValue"));
        }
        throw new RuntimeException("Bad encoding of Item");
    }

    private Player parsePlayer(HashMap<String, Object> encodedPlayer) {
        int money = (int) encodedPlayer.get("money");
        int health = (int) encodedPlayer.get("health");
        Position position = parsePosition((HashMap<String, Integer>) encodedPlayer.get("position"));
        Direction dir = parseDirection((String) encodedPlayer.get("direction"));
        Weapon curWeapon;
        if (encodedPlayer.get("currentWeapon") == null)
            curWeapon = null;
        else
            curWeapon = (Weapon) parseItem((HashMap<String, Object>) encodedPlayer.get("currentWeapon"));

        int level = (int) encodedPlayer.get("level");
        Player out = new Player(money, health, level, position);
        out.setDirection(dir);
        out.setCurrentWeapon(curWeapon);
        return out;
    }

    private Wall parseWall(HashMap<String, Object> encodedWall){
        int length = (int) encodedWall.get("length");
        boolean dir = (boolean) encodedWall.get("up");
        Position pos = parsePosition((HashMap<String, Integer>) encodedWall.get("start"));
        return new Wall(length, pos, dir);
    }

    /**
     * Load files, this should be used to update files
     * @author Xin Chen
     * @param filePath Path to the dest files
     * @return Game state (class Level)
     */
    public Level loadFile(String filePath){
        // Reads the file specified by filepath and converts it to an instance of class Level
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            int levelNum = extractLevelNumber(filePath);
            if (checkLevel(levelNum)) {
                // Read as Map
                Map<String, Object> mazeData = objectMapper.readValue(new File(filePath), new TypeReference<Map<String, Object>>() {
                });
                // Create new maze for Level
                Map<String, Object> encodedMaze = (Map<String, Object>) mazeData.get("maze");
                int columns = (int) encodedMaze.get("columns");
                int rows = (int) encodedMaze.get("rows");
                Position exit = parsePosition((HashMap<String, Integer>) encodedMaze.get("exit"));
                Maze maze = new Maze(columns, rows, exit);

                ArrayList<HashMap<String, Object>> jsonWalls = (ArrayList<HashMap<String, Object>>) encodedMaze.get("encodedWalls");
                ArrayList<Wall> encodedWalls = new ArrayList<>();
                for (HashMap w : jsonWalls){
                    Wall newWall = parseWall(w);
                    maze.addWall(newWall.getStart(),newWall.getLength(),newWall.isUp());
                }

                HashMap<String,String> money = (HashMap<String, String>) encodedMaze.get("money");
                money.forEach((pos, m) -> {
                    maze.addMoney(parsePosition(pos), Integer.parseInt(m));
                });
                maze.setPlayer(parsePlayer((HashMap<String, Object>) encodedMaze.get("player")));
                maze.setRespawnPosition(maze.getPlayer().getPosition());

                HashMap<String, Object> encodedItems = (HashMap<String, Object>) encodedMaze.get("items");
                encodedItems.forEach((p, i) -> {
                    maze.addItem(parsePosition(p), parseItem((HashMap<String, Object>) i));
                });

                HashMap<String, Object> encodedEnemies = (HashMap<String, Object>) encodedMaze.get("enemies");
                encodedEnemies.forEach((p, e) -> {
                    maze.addEnemy(parsePosition(p), parseEnemy((HashMap<String, Object>) e, parsePosition(p)));
                });

                HashMap<String, Object> encodedNPCs = (HashMap<String, Object>) encodedMaze.get("npcs");
                encodedNPCs.forEach((p, n) -> {
                    maze.addNPC(parseNPC( (HashMap<String, Object>) n, parsePosition(p)));
                });


                // Create new level object
                Level level = new Level(levelNum);
                level.setMaze(maze);

                return level;
            } else {
                throw new RuntimeException("wrong level number, please check filename");
            }
        } catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * Extract level number from file name
     * @author Xin Chen
     * @param filePath Exactly path to the file
     * @return Level num in the path
     */
    public int extractLevelNumber(String filePath) {
        Pattern pattern = Pattern.compile("level(\\d+)(_cur)?\\.json");
        Matcher matcher = pattern.matcher(filePath);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            return -1;
        }
    }

    /**
     * Load cur progress
     * @author Xin Chen
     * @return A level object
     */
    public Level loadCurLevelData() {
        // Read the data in the file with "_cur" only, and at the same time read the number after the level
        Level level = null;
        ArrayList<String> files = this.loadLevelList(CUR_PROGRESS_FILE_PATH);
        for (String fileName: files) {
            if (fileName.contains(CUR_INDICATOR)) {
                // String path = CURPROGRESSFILEPATH + fileName;
                level = loadFile(fileName);
            }
        }

        if (level == null) {
            throw new RuntimeException("Error when loading cur file, please check loadCurLevelData() in JsonLoad");
        }
        return level;
    }

    /**
     * Create a folder if not already exist
     * @author Xin Chen
     * @return Level 1 map
     */
    public static boolean createFolder(String folderPath) {
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            return true;
        } else {
            boolean created = folder.mkdirs();
            return created;
        }
    }

    /**
     * This function is for loading new game from preset folder
     * @author Xin Chen
     * @return Level 1 map
     */
    public Level loadStartMap(){
        try {
            if (createFolder(CUR_PROGRESS_FILE_PATH)) {
                System.out.println("Exists or created: " + CUR_PROGRESS_FILE_PATH);
            } else {
                System.err.println("Cannot: " + CUR_PROGRESS_FILE_PATH);
            }
            // Set paths
            Path fileToCopyPath = Paths.get("src/cache/map/" + PREFIX + "1" + SUFFIX);
            Path destFilePath = Paths.get(CUR_PROGRESS_FILE_PATH + PREFIX + "1" + CUR_INDICATOR + SUFFIX);
            // Empty folder current first
            emptyCurFolder();
            // Copy, paste and load
            Files.copy(fileToCopyPath, destFilePath, StandardCopyOption.REPLACE_EXISTING);
            File level1File =  new File(destFilePath.toString());
            if(level1File.exists()){
                return loadCurLevelData();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        throw new RuntimeException("Load start map failed, please check");

    }

    /**
     * Load the next level
     * @author Xin Chen
     * @param curLevel When player get into next level, should have cur level pass in
     * @return An object level back
     */
    public Level loadNextLevel(int curLevel){
        Level newLevel = null;
        try {
            int nextLevel = curLevel + 1;
            if (nextLevel > Game.MAX_LEVEL){
                throw new RuntimeException("Reach the max level.");
            }
            // Set paths
            Path curFilePath = Paths.get(CUR_PROGRESS_FILE_PATH + PREFIX + curLevel + CUR_INDICATOR + SUFFIX);
            Path fileToCopyPath = Paths.get("src/cache/map/" + PREFIX + nextLevel + SUFFIX);
            Path destFilePath = Paths.get(CUR_PROGRESS_FILE_PATH + PREFIX + nextLevel + SUFFIX);
            // Load map from map then change the name to "level${destlevel}_cur.json"
            // Then update the player to the new class level
            Files.copy(fileToCopyPath, destFilePath, StandardCopyOption.REPLACE_EXISTING);

            // GetMaze for reload player
            Maze curMaze = this.loadFile(curFilePath.toString()).getMaze();
            ArrayList<String> datalist = this.loadLevelList(curFilePath.toString());

            JsonSave saver = new JsonSave();
            saver.updateFiles(datalist, curMaze.getPlayer());
            saver.updateFileName(curLevel,curLevel+1);

            newLevel = loadCurLevelData();
            return newLevel;

        } catch (Exception e){
            throw new RuntimeException("Check loadNextLevel()");
        }
    }

    /**
     * load inventory from json
     *
     * @author Xin Chen
     * @return saved inventory if file exists. new inventory if file does not exist
     */
    public Inventory loadInventory() {
        try {
            String filePath = CUR_PROGRESS_FILE_PATH + "inventory.json";
            ObjectMapper objectMapper = new ObjectMapper();

            // Read data from JSON
            Map<String, Object> rawData = objectMapper.readValue(new File(filePath), new TypeReference<Map<String, Object>>() {});
            Map<String, Object> inventoryData = (Map<String, Object>) rawData.get("inventory");

            // Create inventory instance
            int capacity = (Integer) inventoryData.get("capacity");
            Inventory inventory = new Inventory(capacity);

            // Populate inventory with weapons
            Map<String, Map<String, Object>> itemsData = (Map<String, Map<String, Object>>) inventoryData.get("items");
            for (Map.Entry<String, Map<String, Object>> itemEntry : itemsData.entrySet()) {
                String name = (String) itemEntry.getValue().get("name");
                int price = (Integer) itemEntry.getValue().get("price");
                int weight = (Integer) itemEntry.getValue().get("weight");
                int attackValue = (Integer) itemEntry.getValue().get("attackValue");

                // Create a weapon with the extracted details
                Weapon weapon = new Weapon(name, price, weight, attackValue);
                inventory.addItem(weapon);
            }

            //System.out.println(inventory.getCapacity());
            return inventory;
        } catch (Exception e) {
            return new Inventory(5);
        }
    }


    /**
     * Empty everything in CUR_PROGRESS_FILE_PATH
     * @author Xin Chen
     */
    public void emptyCurFolder() {
        // Empty everything in "src/cache/progress/current"
        try {
            Path dir = Paths.get(CUR_PROGRESS_FILE_PATH);
            if (Files.exists(dir)) {
                Files.list(dir).forEach(file -> {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                System.out.println("The specified directory does not exist.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
