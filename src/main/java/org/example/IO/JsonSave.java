package org.example.IO;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.example.belonging.Item;
import org.example.entity.*;
import org.example.gameLogic.Level;
import org.example.gameLogic.Maze;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;


/**
 * @author xinchen
 * JSON saver
 */
public class JsonSave{
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yy_HH_mm");
    private Date currentDate = new Date();
    private String folderName = dateFormat.format(currentDate);
    private final String FOLDERPATH = "src/cache/progress";
    private final String CURPROGRESSFILEPATH = FOLDERPATH+"/current/";

    private final String PREFIX = "level";
    private final String CURINDICATOR = "_cur";
    private final String SURFFIX = ".json";

    private JsonLoad loader = new JsonLoad();

    /**
     * @author xinchen
     * @return True if folder exists, False if not
     */
    private boolean folderExists(String filePath){
        try {
            File folder = new File(filePath);
            return folder.exists() && folder.isDirectory();
        }catch (Exception e){
            return false;
        }
    }

    /**
     * @author xinchen
     * check level num is valid or not
     * @param level it should be the level number reading from loader or game
     * @return Boolean, true if it is between 1-3
     */
    private boolean checkLevel(int level){
        return level > 0 && level <= 3;
    }

    /**
     * @author xinchen
     * empty everything in CURPROGRESSFILEPATH
     */
    public void emptyCurFolder(){
        //empty files in "src/cache/progress/current"
        try {
            Path dir = Paths.get(CURPROGRESSFILEPATH);
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

    /**
     * @author xinchen
     * Save current progress to json
     * @param level should be current level
     */
    public void saveCurrentProgress(Level level) {
        String path = CURPROGRESSFILEPATH + PREFIX + level.getLevel() + CURINDICATOR + SURFFIX;

//        /
//        if(!checkLevelMatches(level.getLevel())){
//            Level oldLevel = loader.loadCurLevelData();
//            int oldLevelNum  = oldLevel.getLevel();
//            updateFileName(oldLevelNum);
//        }

        // create dir if it has been deleted, and save to json
        if(folderExists(CURPROGRESSFILEPATH)){
            if(checkLevel(level.getLevel())){
                int levelDiff = checkLevelMatches(level.getLevel());
                if (levelDiff != 0) {
                    updateFileName(level.getLevel() - levelDiff,level.getLevel());
                }
                saving(path,level);
            }else {
                throw new RuntimeException("Level error! Encounter at class JsonSave - saveCurrentProgress(Level level), please check input param");
            }
        }else {
            try {
                //create dir
                Files.createDirectories(Paths.get(CURPROGRESSFILEPATH));
                saving(path, level);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * @author xinchen
     *  check if player is still in the same level in "current" folder
     *  assuming player only goes up levels
     * @param destLevel destLevel is the level player tend to save
     * @return integer representing the size of the level increase
     */
    private int checkLevelMatches(int destLevel){
        ArrayList<String> levels = loader.loadLevelList(CURPROGRESSFILEPATH);
        if (levels.size() == 0)
            return -1;
        int curLevel = -1;
        for(String path: levels){
            if(path.contains(CURINDICATOR)) {
                curLevel = loader.extractLevelNumber(path);
                break;
            }
        }
        //if(curLevel == -1) throw new RuntimeException();

        return destLevel - curLevel;
    }

    /**
     * @author xinchen
     * rename files with CURINDICATOR in CURPROGRESSFILEPATH
     * @param curLevel where player is currently at
     */
    protected void updateFileName(int curLevel, int destLevel){
        // If the player goes to another level,
        // then this function will be triggered,
        // replacing "level${curLevel}_cur.json" with "level${curLevel}.json" and "level${destLevel}.json" with "level${destLevel}_cur.json",
        // if "level${destLevel}.json" or "level${destLevel}_cur.json" or "level${destLevel}_cur.json" is used,
        // then this function will be triggered, and this will be replaced by "level${destLevel}_cur.json". .json",
        // if "level${destLevel}.json" or "level${destLevel}_cur.json" does not exist then create "level${destLevel}_cur.json" and replace "level${curLevel}_cur.json" with " level${curLevel}.json".
        try {
            //set paths
            Path curFilePath = Paths.get(CURPROGRESSFILEPATH + PREFIX + curLevel + CURINDICATOR + SURFFIX);
            Path destFilePath = Paths.get(CURPROGRESSFILEPATH + PREFIX + destLevel + SURFFIX);
            Path destFileCurPath = Paths.get(CURPROGRESSFILEPATH + PREFIX + destLevel + CURINDICATOR + SURFFIX);

            //Check if dest file or dest_cur file exists, if not create it by copying cur file to dest_cur file
            if (!Files.exists(destFilePath) && !Files.exists(destFileCurPath)) {
                Files.copy(curFilePath, destFileCurPath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Old concept, ignore now
//            // Rename cur file to normal
//            Path destinationPath = Paths.get(CURPROGRESSFILEPATH + PREFIX + curLevel + SURFFIX);
//            Files.move(curFilePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);


            File fileToDelete = new File(String.valueOf(curFilePath));
            if (fileToDelete.exists()) {
                if (fileToDelete.delete()) {
                    System.out.println("Cur progress is deleted");
                } else {
                    throw new RuntimeException("Cannot delete! Check updateFileName");
                }
            }

            // Rename dest file to cur
             if (Files.exists(destFilePath)) {
                Files.move(destFilePath, destFileCurPath, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @author xinchen
     * save level data to json
     * @param destPath the destination path that should be saved to
     * @param level class Level
     */
    private void saving(String destPath, Level level){
        Maze maze = level.getMaze();
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // save data into MAP
            Map<String, Object> mazeData = new HashMap<>();
            mazeData.put("maze", maze);

            // translate to string
            String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mazeData);

            // Save string
            Files.write(Paths.get(destPath), jsonStr.getBytes());

            //ArrayList<String> datalist = loader.loadLevelList(destPath);
            //updateFiles(datalist, maze.getPlayer());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @author xinchen
     *  update player info to other levels, excluding the position info
     * @param datalist a list of other files
     * @param curPlayer the player that changed his/her attributes
     */
    protected void updateFiles(ArrayList<String> datalist, Player curPlayer)  {
        // overwrite all files, not ending with "_cur", with updated player
        for(String path: datalist){
            if (path.contains("_cur")) {
                continue;
            }else {
                try {
                    // load other level to get the maze information
                    Level level = loader.loadFile(path);
                    Maze oldMaze = level.getMaze();

                    ObjectMapper objectMapper = new ObjectMapper();

                    Maze newMaze = new Maze(oldMaze.getColumns(), oldMaze.getRows(), oldMaze.getExit());

                    HashMap<Position, Item> itemHashMap =  oldMaze.getItems();
                    HashMap<Position, Enemy> enemyHashMap = oldMaze.getEnemies();
                    HashMap<Position, NPC> NPCHashMap = oldMaze.getNPCs();
                    ArrayList<Wall> wallArrayList = oldMaze.getEncodedWalls();
                    HashMap<Position, Integer> moneyHashMap = oldMaze.getMoney();

                    //update player
                    //TODO double check position with other members
                    Player newPlayer = new Player(curPlayer.getMoney(),curPlayer.getHealth(), curPlayer.getLevel(),oldMaze.getPlayer().getPosition());
                    newPlayer.setCurrentWeapon(curPlayer.getCurrentWeapon());
                    newMaze.setPlayer(newPlayer);

                    Iterator<Position> itemIter = itemHashMap.keySet().iterator();
                    while (itemIter.hasNext()) {
                        Position key = itemIter.next();
                        newMaze.addItem(key, itemHashMap.get(key));
                    }

                    Iterator<Position> enemyIter = enemyHashMap.keySet().iterator();
                    while (enemyIter.hasNext()) {
                        Position key = enemyIter.next();
                        newMaze.addEnemy(key, enemyHashMap.get(key));
                    }

                    Iterator<Position> NPCiter = NPCHashMap.keySet().iterator();
                    while (NPCiter.hasNext()) {
                        Position key = NPCiter.next();
                        newMaze.addNPC(key, NPCHashMap.get(key));
                    }

                    newMaze.setEncodedWalls(wallArrayList);

                    Iterator<Position> moneyiter = moneyHashMap.keySet().iterator();
                    while (moneyiter.hasNext()) {
                        Position key = moneyiter.next();
                        newMaze.addMoney(key, moneyHashMap.get(key));
                    }

                    // save data into MAP
                    Map<String, Object> mazeData = new HashMap<>();
                    mazeData.put("maze", newMaze);

                    String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mazeData);
                    Files.write(Paths.get(path), jsonStr.getBytes());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * @author xinchen
     * save progress to new progress folder
     */
    public void saveToNewProgress(){
        try {
            String destFolderPath = FOLDERPATH + "/" + folderName+"/";
            String curFolderPath = CURPROGRESSFILEPATH;

            // create folder
            Files.createDirectories(Paths.get(destFolderPath));

            // get what current folder has
            Stream<Path> paths = Files.walk(Paths.get(curFolderPath));

            // copy all current files to new progress
            paths.forEach(sourcePath -> {
                try {
                    Files.copy(sourcePath, Paths.get(destFolderPath).resolve(Paths.get(curFolderPath).relativize(sourcePath)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            paths.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot save new progress");
        }
    }


}
