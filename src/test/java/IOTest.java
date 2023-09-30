import org.example.IO.JsonLoad;
import org.example.IO.JsonSave;
import org.example.belonging.Inventory;
import org.example.belonging.Weapon;
import org.example.entity.Enemy;
import org.example.entity.NPC;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Level;
import org.example.gameLogic.Maze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * @author : xinchen
 * @date : 15/9/2023
 *
 * Test JSON IO
 **/
public class IOTest {
    static Maze testMaze;
    static Level testLevel;
    static Level testLevel1;
    static Level testLevel2;


    static Inventory inventory;
    static JsonLoad loader = new JsonLoad();
    static JsonSave saver = new JsonSave();
    @BeforeAll
    static void before(){
        testLevel = new Level(1);
        testLevel1 = new Level(2);
        testLevel2 = new Level(3);

        int mazeX = 65;
        int mazeY = 20;
        testMaze = new Maze(mazeX, mazeY, new Position(1, 1));
        testMaze.createNewPlayer(new Position(10, 10));

        // add walls at the boundary
        testMaze.addWall(new Position(0, 0),mazeX, false);
        testMaze.addWall(new Position(0, 0),mazeY, true);
        testMaze.addWall(new Position(mazeX - 1, 0), mazeY, true);
        testMaze.addWall(new Position(0, mazeY - 1), mazeX, false);

        // add things
        testMaze.addItem(new Position(10, 15), new Weapon("The Big Axe",3, 5, 4));
        testMaze.addItem(new Position(11, 15), new Weapon("The Small Axe",3, 5, 4));
        // maze.addItem(new Position(32, 25), new Weapon("The medium Axe",3, 5, 4));
        testMaze.addItem(new Position(9, 15), new Weapon("The crazy cat",3, 5, 4));
        testMaze.addItem(new Position(12, 15), new Weapon("The ugly fly",3, 5, 4));
        testMaze.addItem(new Position(13, 15), new Weapon("The dragon knife",3, 5, 4));
        testMaze.addNPC(new NPC("King George's Chief Councillor", new Position(4, 5)));
//        maze.addEnemy(new Position(25, 20),new Enemy(2, 2, 2));
        testMaze.addEnemy(new Position(5, 4),new Enemy("Tom", 2, 2, 2, new Position(5, 4)));

        testLevel.setMaze(testMaze);
        testLevel1.setMaze(testMaze);
        testLevel2.setMaze(testMaze);

        inventory = new Inventory(5);


        inventory.addItem(new Weapon("A", 24, 12, 14));
        inventory.addItem(new Weapon("B", 15, 18,15));

    }

    /**
     * check if save things and start new game goes well
     *
     * @author: Austin Zerk
     * @author: Xin Chen
     */
    @Test
    void saveTest(){

        //test saveCurrentProgress
        saver.saveCurrentProgress(testLevel);
        String targetFileName = "level1_cur.json";
        String currentDirectory = "src/cache/progress/current/";
        String fullPath = currentDirectory + targetFileName;
        Assertions.assertTrue(Files.exists(Paths.get(fullPath)));

        //test saveInventory
        saver.saveInventory(inventory);
        targetFileName = "inventory.json";
        fullPath = currentDirectory + targetFileName;
        Assertions.assertTrue(Files.exists(Paths.get(fullPath)));

        // empty
        loader.emptyCurFolder();
    }

    /**
     * check up level function is working, loader loads things well,
     * leave level1_cur.json for rest test
     *
     * @author: Austin Zerk
     * @author: Xin Chen
     */
    @Test
    void loadTest() {

        //test loadStartMap and keep it for loading test
        String targetFileName = "level1_cur.json";
        String currentDirectory = "src/cache/progress/current/";
        String fullPath = currentDirectory + targetFileName;
        loader.loadStartMap();
        Assertions.assertTrue(Files.exists(Paths.get(fullPath)));

        //test loadCurLevelData
        Level loaded = loader.loadFile("src/cache/progress/current/level1_cur.json");
        Level loaded2 = loader.loadCurLevelData();

        Assertions.assertEquals(loaded.getLevel(), loaded2.getLevel());
        Assertions.assertEquals(loaded.getMaze().getPlayer().getLevel(), loaded2.getMaze().getPlayer().getLevel());
        Assertions.assertEquals(loaded.getMaze().getPlayer().getHealth(), loaded2.getMaze().getPlayer().getHealth());

        //test loadNextLevel
        loader.loadNextLevel(loaded2.getLevel());
        Assertions.assertEquals(loader.loadCurLevelData().getLevel(), 2);

        //test when player start new game
        loader.emptyCurFolder();
        Level loaded3 = loader.loadStartMap();
        targetFileName = "level1_cur.json";
        currentDirectory = "src/cache/progress/current/";
        fullPath = currentDirectory + targetFileName;
        Assertions.assertTrue(Files.exists(Paths.get(fullPath)));
        Level nextLevel = loader.loadNextLevel(loaded3.getLevel());
        targetFileName = "level2_cur.json";
        fullPath = currentDirectory + targetFileName;
        Assertions.assertTrue(Files.exists(Paths.get(fullPath)));
        Level finalLevel = loader.loadNextLevel(nextLevel.getLevel());
        targetFileName = "level3_cur.json";
        fullPath = currentDirectory + targetFileName;
        Assertions.assertTrue(Files.exists(Paths.get(fullPath)));
        Assertions.assertEquals(3,loader.extractLevelNumber(fullPath));

        //test inventory save and load
        saver.saveInventory(inventory);
        Inventory inventory1 = loader.loadInventory();
        Assertions.assertEquals(inventory.getCapacity(), inventory1.getCapacity());
        Assertions.assertEquals(inventory.getItems().size(), inventory1.getItems().size());
        Assertions.assertEquals(inventory.getItems().get("A").getName(), inventory1.getItems().get("A").getName());

        loader.emptyCurFolder();

        // leave it for talk test and later test
        saver.saveCurrentProgress(testLevel);
    }

    /**
     * to test the progress of update player in new file
     */
    @Test
    public void testUpdate(){
        saver.emptyCurFolder();

        testLevel1.getMaze().getPlayer().setLevel(2);
        saver.saveCurrentProgress(testLevel1);

        Player testPlayer = testLevel1.getMaze().getPlayer();


        File sourceFile = new File("src/cache/map/level3.json");
        File destFile = new File("src/cache/progress/current/level3.json");

        try (
                InputStream fis = new FileInputStream(sourceFile);
                OutputStream fos = new FileOutputStream(destFile)
        ) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            System.out.println("File copied successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String curFilePath = "src/cache/progress/current/";

        ArrayList<String> datalist = loader.loadLevelList(curFilePath);

        Assertions.assertNotEquals(loader.loadFile(String.valueOf(destFile)).getMaze().getPlayer().getLevel(), loader.loadCurLevelData().getMaze().getPlayer().getLevel());


        saver.updateFiles(datalist,testPlayer);
        Assertions.assertEquals(loader.loadFile(String.valueOf(destFile)).getMaze().getPlayer().getLevel(), loader.loadCurLevelData().getMaze().getPlayer().getLevel());


        saver.updateFileName(testLevel1.getLevel(), testLevel1.getLevel()+1);

        Assertions.assertTrue(Files.exists(Paths.get("src/cache/progress/current/level3_cur.json")));

        saver.emptyCurFolder();
    }

    /**
     * To test if inventory is null
     *
     * @author Xin Chen
     */
    @Test
    public void testSaveInventoryThrowsException(){
        Assertions.assertThrows(RuntimeException.class, () -> {
            saver.saveInventory(null);
        });
    }

    /**
     * To test exception when load last level
     *
     * @author Xin Chen
     */
    @Test
    public void testNextLevelThrowsException(){
        Assertions.assertThrows(RuntimeException.class, () -> {
        loader.loadNextLevel(testLevel2.getLevel());
        });
    }

    /**
     *  TO test loadInventory, when there's no such a file
     *
     * @author Xin Chen
     */

    @Test
    public void testLoadInventory(){
        loader.emptyCurFolder();
        Inventory inventory1 = loader.loadInventory();
        Assertions.assertEquals(0, inventory1.getItems().size());

    }

    /**
     * TO test if a folder contain duplicate _cur file
     *
     * @author Xin Chen
     */

    @Test
    public void testDuplicate(){
        saver.emptyCurFolder();
        saver.saveCurrentProgress(testLevel);
        Assertions.assertTrue(Files.exists(Paths.get("src/cache/progress/current/level1_cur.json")));
        saver.saveCurrentProgress(testLevel1);
        Assertions.assertTrue(Files.exists(Paths.get("src/cache/progress/current/level2_cur.json")));
        saver.saveCurrentProgress(testLevel2);

        Assertions.assertFalse(Files.exists(Paths.get("src/cache/progress/current/level1_cur.json")));
        Assertions.assertFalse(Files.exists(Paths.get("src/cache/progress/current/level2_cur.json")));
        Assertions.assertTrue(Files.exists(Paths.get("src/cache/progress/current/level3_cur.json")));

        saver.emptyCurFolder();

        // for NpcTalkerTest
        saver.saveCurrentProgress(testLevel);
    }

}
