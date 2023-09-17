package org.example.IO;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.belonging.Item;
import org.example.entity.Enemy;
import org.example.entity.NPC;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Level;
import org.example.gameLogic.Maze;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xinchen
 * JSON loader
 */
public class JsonLoad {

    private final String FOLDERPATH = "src/cache/progress";
    private final String CURPROGRESSFILEPATH = FOLDERPATH+"/current/";
    private final String PREFIX = "level";
    private final String CURINDICATOR = "_cur";
    private final String SURFFIX = ".json";

    /**
     * @author xinchen
     * @param progress the progress from gui
     * @return True if folder exists, False if not
     */
    private boolean folderExists(String progress){
        try {
            String folderPath = FOLDERPATH;
            if(Objects.equals(progress, "")){
                folderPath = FOLDERPATH + "/" +progress;
            }

            File folder = new File(folderPath);
            return folder.exists() && folder.isDirectory();
        }catch (Exception e){
            return false;
        }
    }

    /**
     *  @author xinchen
     * check level num is valid or not
     * @param level it should be the level number reading from loader or game
     * @return Boolean, true if it is between 1-3
     */
    private boolean checkLevel(int level){
        return level >= 0 && level <= 2;
    }

    /**
     * @author xinchen
     * Load list of json folders that saving the
     * @return null if folder is empty, list if folder has files
     */
    public ArrayList<String> loadProcessList(){
        //FIXME 将所有的在FOLDERPATH中的第一级folder读出来，folder的文件格式是"dd_MM_yy_HH_mm"和"current"， 只读这些，别的不读
        ArrayList<String> progressList = new ArrayList<>();
        File folder = new File(FOLDERPATH);
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
     * @author xinchen
     * check "levelX.json" and "levelX_cur.json", where X is num
     * @param path should be dir path to progress folder
     * @return a list of different level json files
     */
    public ArrayList<String> loadLevelList(String path){
        //FIXME 读取path中有多少个文件是符合"levelX.json"或者"levelX_cur.json"
        ArrayList<String> levelList = new ArrayList<>();
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.matches("level\\d+\\.json") || fileName.matches("level\\d+_cur\\.json")) {
                        levelList.add(path+fileName);
                    }
                }
            }
        }

        return levelList;
    }

    /**
     * @author xinchen
     * load files, this should be used to update files
     * @param filePath path to the dest files
     * @return game state (class Level)
     */
    public Level loadFile(String filePath){
        //FIXME 读取filepath指定的文件，并将其转换成class Level的实例
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            int levelNum = extractLevelNumber(filePath);
            if (checkLevel(levelNum)) {
                // read as Map
                Map<String, Object> mazeData = objectMapper.readValue(new File(filePath), new TypeReference<Map<String, Object>>() {
                });
                // create new maze for Level
                Position exitPosition = (Position) mazeData.get("exit");
                Maze mazeInFile = (Maze) mazeData.get("maze");
                Maze maze = new Maze(mazeInFile.getColumns(), mazeInFile.getRows(), exitPosition);


                // set Attributes for maze
                Map<Position, Enemy> enemies = (Map<Position, Enemy>) mazeData.get("enemies");
                for (Map.Entry<Position, Enemy> entry : enemies.entrySet()) {
                    maze.addEnemy(entry.getKey(), entry.getValue());
                }

                Map<Position, NPC> npcs = (Map<Position, NPC>) mazeData.get("npcs");
                for (Map.Entry<Position, NPC> entry : npcs.entrySet()) {
                    maze.addNPC(entry.getKey(), entry.getValue());
                }

                Map<Position, Item> items = (Map<Position, Item>) mazeData.get("items");
                for (Map.Entry<Position, Item> entry : items.entrySet()) {
                    maze.addItem(entry.getKey(), entry.getValue());
                }

                maze.setExit((Position) mazeData.get("exit"));
                maze.setMoney((HashMap<Position, Integer>) mazeData.get("money"));

                Player player = (Player) mazeData.get("player");

                Position playerPos = player.getPosition();

                maze.createNewPlayer(playerPos);


                // create new level object
                Level level = new Level(levelNum);
                level.setMaze(maze);

                return level;
            }else{
                throw new RuntimeException("wrong level number, please check filename");
            }
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * @author xinchen
     * extract level number from file name
     * @param filePath exactly path to the file
     * @return level num in the path
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
     * @author xinchen
     *
     * load cur progress
     *
     * @return a level object
     */

    public Level loadCurLevelData(){
        //FIXME 读取只带有"_cur"的文件内的数据，同时要将level后面的数字读取到
        Level level = null;
        ArrayList<String> files = this.loadLevelList(CURPROGRESSFILEPATH);
        for(String fileName: files){
            if(fileName.contains(CURINDICATOR)){
                String path = CURPROGRESSFILEPATH + fileName;
                level = loadFile(path);
            }
        }

        if(level.equals(null)){
            throw new RuntimeException("Error when loading cur file, please check loadCurLevelData() in JsonLoad");
        }
        return level;
    }

    /**
     * @author xinchen
     *
     * ask player if he/she would like to save the previous game, if not empty everything in the "current" folder,
     * if yes save the current folder to new progress then move the dest progress to "current" folder
     *
     * @param progress String: load the progress chosen from gui
     */
    public void loadJsonFolder(String progress, boolean saveOrNot){
        //FixME 将如果有current问player要不要保存，否则此存档全部写入current，是则将current存到当前时间的文件夹，将即将载入的progress全盘写入到current
        if(loadProcessList().contains(progress)) {

        }else {
            throw new RuntimeException("There is no such a progress");
        }

    }

    /**
     * @author xinchen
     *
     * empty everything in CURPROGRESSFILEPATH
     */
    public void emptyCurFolder(){
        //FIXME 将"src/cache/progress/current"内的所有文档清空
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

}
