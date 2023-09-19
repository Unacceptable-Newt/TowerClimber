package org.example.IO;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
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
     * @author Austin Zerk
     * This function is used to pass the hashmap representing the position
     * @param positionMap a hashmap with the x and y as keys and their integer values as values
     * @return Position of the hashmap
     */
    private Position parsePosition(HashMap<String,String> positionMap){
        int x = Integer.parseInt(positionMap.get("x"));
        int y = Integer.parseInt(positionMap.get("y"));
        return new Position(x,y);
    }

    private Position parsePosition(String encodedPosition){
        int xoffs  = -1;
        int yoffs  = -1;
        int x = 0;
        int y = 0;
        for (char c : encodedPosition.toCharArray()){
            if(c == 'x')
                xoffs = 2;
            if(c == 'y')
                yoffs = 2;
            if(yoffs == 0)
                y = Character.getNumericValue(c);
            if (xoffs == 0)
                x = Character.getNumericValue(c);
            xoffs--;
            yoffs--;
        }
        return new Position(x,y);
    }

    private Enemy parseEnemy(HashMap<String,Object> encodedEnemies, Position position){
        int attack = Integer.parseInt((String) encodedEnemies.get("attack"));
        int defence = Integer.parseInt((String) encodedEnemies.get("defence"));
        Direction dir = parseDirection((String) encodedEnemies.get("direction"));
        int health = Integer.parseInt((String) encodedEnemies.get("health"));
        int money = Integer.parseInt((String) encodedEnemies.get("money"));
        Enemy out = new Enemy(attack,health,defence);
        out.setDirection(dir);
        out.setMoney(money);
        out.setPosition(parsePosition((HashMap<String, String>) encodedEnemies.get("Position")));
        out.setPosition(position);
        return out;
    }

    private Direction parseDirection(String dir){
        return switch (dir) {
            case ("UP") -> Direction.UP;
            case ("DOWN") -> Direction.DOWN;
            case ("LEFT") -> Direction.LEFT;
            case ("RIGHT") -> Direction.RIGHT;
            default -> null;
        };
    }

    private NPC parseNPC(HashMap<String,Object> encodedEnemy, Position position){
        int money = Integer.parseInt((String) encodedEnemy.get("money"));
        int health = Integer.parseInt((String) encodedEnemy.get("health"));
        Direction direction = parseDirection((String) encodedEnemy.get("direction"));
        String name = (String) encodedEnemy.get("name");
        ArrayList<String> dialog = (ArrayList<String>) encodedEnemy.get("dialog");
        NPC out = new NPC(name,position,dialog);
        out.setDirection(direction);
        out.setHealth(health);
        out.setMoney(money);
        out.setPosition(position);
        return out;
    }

    private Item parseItem(HashMap<String,Object> encodedItem){
        int price = Integer.parseInt((String) encodedItem.get("price"));
        int weight = Integer.parseInt((String) encodedItem.get("weight"));
        String name = (String) encodedItem.get("name");
        if (encodedItem.containsKey("attackValue")){
            return new Weapon(name,price,weight, (Integer) encodedItem.get("attackValue"));
        }
        throw new RuntimeException("Bad encoding of Item");
    }

    private Player parsePlayer(HashMap<String,Object> encodedPlayer){
        int money = Integer.parseInt((String) encodedPlayer.get("money"));
        int health = Integer.parseInt((String) encodedPlayer.get("health"));
        Position position = parsePosition((HashMap<String, String>) encodedPlayer.get("Position"));
        Direction dir = parseDirection((String) encodedPlayer.get("direction"));
        Weapon curWeapon;
        if (encodedPlayer.get("currentWeapon") == "null")
            curWeapon = null;
        else
            curWeapon = (Weapon) parseItem((HashMap<String, Object>) encodedPlayer.get("currentWeapon"));

        int level = Integer.parseInt((String) encodedPlayer.get("level"));
        Player out = new Player(money,health,level,position);
        out.setDirection(dir);
        out.setCurrentWeapon(curWeapon);
        return out;
    }

    private Wall parseWall(HashMap<String,Object> encodedWall){
        int length = Integer.parseInt((String) encodedWall.get("length"));
        boolean dir = Boolean.parseBoolean((String) encodedWall.get("up"));
        Position pos = parsePosition((HashMap<String, String>) encodedWall.get("start"));
        return new Wall(length,pos,dir);
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
                Map<String, Object> encodedMaze = (Map<String, Object>) mazeData.get("maze");
                int columns = Integer.parseInt((String) encodedMaze.get("columns"));
                int rows = Integer.parseInt((String) encodedMaze.get("rows"));
                Position exit = parsePosition((HashMap<String, String>) encodedMaze.get("exit"));
                Maze maze = new Maze(columns,rows,exit);

                ArrayList<HashMap<String,Object>> jsonWalls = (ArrayList<HashMap<String, Object>>) encodedMaze.get("encodedWalls");
                ArrayList<Wall> encodedWalls = new ArrayList<>();
                for (HashMap w : jsonWalls){
                    encodedWalls.add(parseWall(w));
                }
                maze.setEncodedWalls(encodedWalls);

                HashMap<String,String> money = (HashMap<String, String>) encodedMaze.get("money");
                money.forEach((pos,m) -> {
                    maze.addMoney(parsePosition(pos),Integer.parseInt(m));
                });
                maze.setPlayer(parsePlayer((HashMap<String, Object>) encodedMaze.get("player")));

                HashMap<String,Object> encodedItems = (HashMap<String, Object>) encodedMaze.get("items");
                encodedItems.forEach((p,i) -> {
                    maze.addItem(parsePosition(p),parseItem((HashMap<String, Object>) i));
                });

                HashMap<String,Object> encodedEnemies = (HashMap<String, Object>) encodedMaze.get("items");
                encodedEnemies.forEach((p,e) -> {
                    maze.addItem(parsePosition(p),parseItem((HashMap<String, Object>) e));
                });

                HashMap<String,Object> encodedNPCs = (HashMap<String, Object>) encodedMaze.get("items");
                encodedNPCs.forEach((p,n) -> {
                    maze.addItem(parsePosition(p),parseItem((HashMap<String, Object>) n));
                });


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
                //String path = CURPROGRESSFILEPATH + fileName;
                level = loadFile(fileName);
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
