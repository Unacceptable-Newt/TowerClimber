package org.example.IO;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * @author Xin Chen
 * JSON loader
 */
public class JsonLoad {

    private final String FOLDER_PATH = "src/cache/progress";
    private final String CUR_PROGRESS_FILE_PATH = FOLDER_PATH +"/current/";
    private final String PREFIX = "level";
    private final String CUR_INDICATOR = "_cur";
    private final String SUFFIX = ".json";


    /**
     * @author Xin Chen
     * @param progress The progress from gui
     * @return True if folder exists, False if not
     */
    private boolean folderExists(String progress) {
        try {
            String folderPath = FOLDER_PATH;
            if (Objects.equals(progress, "")) {
                folderPath = FOLDER_PATH + "/" + progress;
            }

            File folder = new File(folderPath);
            return folder.exists() && folder.isDirectory();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @author Xin Chen
     * Check level num is valid or not
     * @param level It should be the level number reading from loader or game
     * @return Boolean, true if it is between 1-3
     */
    private boolean checkLevel(int level) {
        return level >= 1 && level <= 3;
    }

    /**
     * @author Xin Chen
     * Load list of json folders that saving the
     * @return null if folder is empty, list if folder has files
     */
    public ArrayList<String> loadProgressList() {
        // Read out all the first level folder in FOLDERPATH,
        // the file format of the folder is "dd_MM_yy_HH_mm" and "current",
        // only read these, nothing else.

        ArrayList<String> progressList = new ArrayList<>();
        File folder = new File(FOLDER_PATH);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isDirectory()) {
                    String folderName = file.getName();
                    if (folderName.matches("\\d{2}_\\d{2}_\\d{2}_\\d{2}_\\d{2}") || folderName.equals("current")) {
                        progressList.add(folderName);
                    }
                }
            }
        }

        return progressList;
    }

    /**
     * @author Xin Chen
     * Check "levelX.json" and "levelX_cur.json", where X is num
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
     * @author Austin Zerk
     * This function is used to pass the hashmap representing the position
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
        Enemy out = new Enemy(attack, health, defence);
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
     * @author Xin Chen
     * Load files, this should be used to update files
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
                    maze.addNPC(parsePosition(p), parseNPC( (HashMap<String, Object>) n, parsePosition(p)));
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
     * @author Xin Chen
     * Extract level number from file name
     * @param filePath Exactly path to the file
     * @return Level num in the path
     */
    protected int extractLevelNumber(String filePath) {
        Pattern pattern = Pattern.compile("level(\\d+)(_cur)?\\.json");
        Matcher matcher = pattern.matcher(filePath);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            return -1;
        }
    }

    /**
     * @author Xin Chen
     *
     * Load cur progress
     *
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
     * @author Xin Chen
     *
     * Ask player if he/she would like to save the previous game, if not empty everything in the "current" folder,
     * If yes save the current folder to new progress then move the dest progress to "current" folder
     *
     * @param progress String: Load the progress chosen from gui
     * @param saveOrNot Boolean: Check if player wants to overwrite on the current folder
     */
    public Level loadProgress(String progress, boolean saveOrNot) {
        // Will if there is a current ask the player to save it,
        // otherwise this archive is all written to current,
        // yes then save current to the current time folder,
        // will be loaded into the progress of all the discs written to current
        if (loadProgressList().contains(progress)) {
            if (saveOrNot) { // yes
                emptyCurFolder();
                String folderProgressPath = FOLDER_PATH + progress;
                try {
                    // Get what current folder has
                    Stream<Path> paths = Files.walk(Paths.get(folderProgressPath));
                    // Copy all current files to new progress
                    paths.forEach(sourcePath -> {
                        try {
                            Files.copy(sourcePath, Paths.get(CUR_PROGRESS_FILE_PATH).resolve(Paths.get(CUR_PROGRESS_FILE_PATH).relativize(sourcePath)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    paths.close();

                    return loadCurLevelData();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Cannot load correctly in loadProgress(), 1st");
                }
            } else { // No
                // Save first
                JsonSave saver = new JsonSave();
                saver.saveToNewProgress();

                // Empty
                emptyCurFolder();

                // Copy previous progress to current
                String folderProgressPath = FOLDER_PATH + progress;
                try {
                    // get what progress has
                    Stream<Path> paths = Files.walk(Paths.get(folderProgressPath));

                    // Copy all progress to cur
                    paths.forEach(sourcePath -> {
                        try {
                            Files.copy(sourcePath, Paths.get(CUR_PROGRESS_FILE_PATH).resolve(Paths.get(CUR_PROGRESS_FILE_PATH).relativize(sourcePath)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    paths.close();
                    return loadCurLevelData();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Cannot save and load correctly in loadProgress(), 2nd");
                }
            }
        } else {
            throw new RuntimeException("There is no such a progress");
        }
    }


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
     * @author: Xin Chen
     *
     * This function is for loading new game from preset folder
     *
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
     * @author: Xin Chen
     *
     * @param curLevel When player get into next level, should have cur level pass in
     * @return An object level back
     */
    public Level loadNextLevel(int curLevel){
        Level newLevel = null;
        try {
            int nextLevel = curLevel + 1;
            if(nextLevel >3){
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
     * @author Xin Chen
     *
     * Empty everything in CURPROGRESSFILEPATH
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
