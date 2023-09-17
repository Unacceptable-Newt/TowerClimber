package org.example.IO;

import com.fasterxml.jackson.databind.ObjectMapper;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
        //FIXME 将"src/cache/progress/current内的所有文档清空"
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

        //FIXME check what is the current level, if it is not the same, update
        if(!checkLevelMatches(level.getLevel())){
            Level oldLevel = loader.loadCurLevelData();
            int oldLevelNum  = oldLevel.getLevel();
            updateFileName(oldLevelNum,level.getLevel());
        }

        // create dir if it has been deleted, and save to json
        if(folderExists(CURPROGRESSFILEPATH)){
            if(checkLevel(level.getLevel())){
                saving(path, level);
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
     * @param destLevel destLevel is the level player tend to save
     * @return
     */
    private boolean checkLevelMatches(int destLevel){
        ArrayList<String> levels = loader.loadLevelList(CURPROGRESSFILEPATH);
        int curLevel = -1;
        for(String path: levels){
            // TODO: check it
            if(path.contains(CURINDICATOR)) {
                curLevel = loader.extractLevelNumber(path);
            }
        }
        if(curLevel == -1) throw new RuntimeException();

        return destLevel == curLevel;
    }

    /**
     * @author xinchen
     * rename files with CURINDICATOR in CURPROGRESSFILEPATH
     * @param curLevel where player is currently at
     * @param destLevel where player tends to go
     */
    private void updateFileName(int curLevel, int destLevel){
        //FIXME 如果player进入别的层，那么将会触发这个改名函数，将"level${curLevel}_cur.json" 换成 "level${curLevel}.json",将"level${destLevel}.json"换成"level${destLevel}_cur.json", 如果"level${destLevel}.json"或者"level${destLevel}_cur.json"不存在则创建"level${destLevel}_cur.json"并将"level${curLevel}_cur.json" 换成 "level${curLevel}.json"
        try {
            //set paths
            Path curFilePath = Paths.get(CURPROGRESSFILEPATH + PREFIX + curLevel + CURINDICATOR + SURFFIX);
            Path destFilePath = Paths.get(CURPROGRESSFILEPATH + PREFIX + destLevel + SURFFIX);
            Path destFileCurPath = Paths.get(CURPROGRESSFILEPATH + PREFIX + destLevel + CURINDICATOR + SURFFIX);

            // Check if dest file or dest_cur file exists, if not create it by copying cur file to dest_cur file
            if (!Files.exists(destFilePath) && !Files.exists(destFileCurPath)) {
                Files.copy(curFilePath, destFileCurPath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Rename cur file to normal
            Path destinationPath = Paths.get(CURPROGRESSFILEPATH + PREFIX + curLevel + SURFFIX);
            Files.move(curFilePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            // Rename dest file to cur
            if (Files.exists(destFilePath)) {
                Files.move(destFilePath, destFileCurPath, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException e) {
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
            mazeData.put("enemies", maze.getEnemies());
            mazeData.put("player", maze.getPlayer());
            mazeData.put("npcs", maze.getNPCs());
            mazeData.put("exit", maze.getExit());
            mazeData.put("walls", maze.getEncodedWalls());
            mazeData.put("items", maze.getItems());
            mazeData.put("money", maze.getMoney());

            // translate to string
            String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mazeData);

            // Save string
            Files.write(Paths.get(destPath), jsonStr.getBytes());

            ArrayList<String> datalist = loader.loadLevelList(destPath);
            updateFiles(datalist, maze.getPlayer());
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
    private void updateFiles(ArrayList<String> datalist, Player curPlayer)  {
        // FIXME 除了带"_cur"的json文件，别的文件都需要单独将player拿出来单独重新写入新的数据
        for(String path: datalist){
            if (path.contains("_cur")) {
                continue;
            }else {
                try {
                    Level level = loader.loadFile(path);
                    Maze maze = level.getMaze();
                    ObjectMapper objectMapper = new ObjectMapper();

                    //update player
                    Player newPlayer = new Player(curPlayer.getMoney(),curPlayer.getHealth(), curPlayer.getLevel(),maze.getPlayer().getPosition());

                    // save data into MAP
                    Map<String, Object> mazeData = new HashMap<>();
                    mazeData.put("maze", maze);
                    mazeData.put("enemies", maze.getEnemies());
                    mazeData.put("player", newPlayer);
                    mazeData.put("npcs", maze.getNPCs());
                    mazeData.put("exit", maze.getExit());
                    mazeData.put("walls", maze.getEncodedWalls());
                    mazeData.put("items", maze.getItems());
                    mazeData.put("money", maze.getMoney());

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
    private void saveToNewProgress(){
        try {
            // FIXME 将current文件夹内的所有的东西都储存到新的文件夹
            String destFolderPath = FOLDERPATH + "/" + folderName+"/";
            String curFolderPath = CURPROGRESSFILEPATH;

            // 创建目标文件夹，如果不存在
            Files.createDirectories(Paths.get(destFolderPath));

            // 获取当前文件夹内的所有文件
            Stream<Path> paths = Files.walk(Paths.get(curFolderPath));

            // 复制所有文件到新的目标文件夹
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
