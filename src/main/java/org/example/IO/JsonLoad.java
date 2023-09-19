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
    private Position parsePosition(HashMap<String,Integer> positionMap){
        int x = positionMap.get("x");
        int y = positionMap.get("y");
        return new Position(x,y);
    }

    private Position parsePosition(String encodedPosition){
        String posstr = encodedPosition.substring(11);
        StringBuilder xstr = new StringBuilder();
        StringBuilder ystr = new StringBuilder();
        int reading = 1;
        for (char c : posstr.toCharArray()){
            if (c==',')
                reading = 0;
            if (c=='}')
                break;
            if (reading == 1) {
                xstr.append(c);
            }else if(reading == 2){
                ystr.append(c);
            }
            if (c=='=')
                reading = 2;
        }
        int x = Integer.parseInt(xstr.toString());
        int y = Integer.parseInt(ystr.toString());
        return new Position(x,y);
    }

    private Enemy parseEnemy(HashMap<String,Object> encodedEnemies, Position position){
        int attack = (int) encodedEnemies.get("attack");
        int defence = (int) encodedEnemies.get("defense");
        Direction dir = parseDirection((String) encodedEnemies.get("direction"));
        int health = (int) encodedEnemies.get("health");
        int money = (int) encodedEnemies.get("money");
        Enemy out = new Enemy(attack,health,defence);
        out.setDirection(dir);
        out.setMoney(money);
        out.setPosition(parsePosition((HashMap<String, Integer>) encodedEnemies.get("position")));
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
        int money = (int) encodedEnemy.get("money");
        int health = (int) encodedEnemy.get("health");
        Direction direction = parseDirection((String) encodedEnemy.get("direction"));
        String name = (String) encodedEnemy.get("name");
        ArrayList<String> dialog = (ArrayList<String>) encodedEnemy.get("dialogue");
        NPC out = new NPC(name,position,dialog);
        out.setDirection(direction);
        out.setHealth(health);
        out.setMoney(money);
        out.setPosition(position);
        return out;
    }

    private Item parseItem(HashMap<String,Object> encodedItem){
        int price = (int) encodedItem.get("price");
        int weight = (int) encodedItem.get("weight");
        String name = (String) encodedItem.get("name");
        if (encodedItem.containsKey("attackValue")){
            return new Weapon(name,price,weight, (Integer) encodedItem.get("attackValue"));
        }
        throw new RuntimeException("Bad encoding of Item");
    }

    private Player parsePlayer(HashMap<String,Object> encodedPlayer){
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
        Player out = new Player(money,health,level,position);
        out.setDirection(dir);
        out.setCurrentWeapon(curWeapon);
        return out;
    }

    private Wall parseWall(HashMap<String,Object> encodedWall){
        int length = (int) encodedWall.get("length");
        boolean dir = (boolean) encodedWall.get("up");
        Position pos = parsePosition((HashMap<String, Integer>) encodedWall.get("start"));
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
                int columns = (int) encodedMaze.get("columns");
                int rows = (int) encodedMaze.get("rows");
                Position exit = parsePosition((HashMap<String, Integer>) encodedMaze.get("exit"));
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

                HashMap<String,Object> encodedEnemies = (HashMap<String, Object>) encodedMaze.get("enemies");
                encodedEnemies.forEach((p,e) -> {
                    maze.addEnemy(parsePosition(p),parseEnemy((HashMap<String, Object>) e,parsePosition(p)));
                });

                HashMap<String,Object> encodedNPCs = (HashMap<String, Object>) encodedMaze.get("npcs");
                encodedNPCs.forEach((p,n) -> {
                    maze.addNPC(parsePosition(p),parseNPC((HashMap<String, Object>) n,parsePosition(p)));
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
