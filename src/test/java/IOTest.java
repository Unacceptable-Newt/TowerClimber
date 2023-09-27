import org.example.IO.JsonLoad;
import org.example.IO.JsonSave;
import org.example.belonging.Weapon;
import org.example.entity.Enemy;
import org.example.entity.NPC;
import org.example.entity.Position;
import org.example.gameLogic.Level;
import org.example.gameLogic.Maze;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : xinchen
 * @date : 15/9/2023
 *
 * Test JSON IO
 **/
public class IOTest {
    static Maze testMaze;
    static Level testLevel;

    static JsonLoad loader = new JsonLoad();
    static JsonSave saver = new JsonSave();
    @BeforeAll
    static void before(){
        testLevel = new Level(1);

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
        testMaze.addEnemy(new Position(5, 4),new Enemy(2, 2, 2));

        testLevel.setMaze(testMaze);
    }

    /**
     * check if save things and start new game goes well
     *
     * @author: Austin Zerk
     * @author: Xin Chen
     */
    @Test
    void saveTest(){

        saver.saveCurrentProgress(testLevel);
        String targetFileName = "level1_cur.json";
        String currentDirectory = "src/cache/progress/current/";
        String fullPath = currentDirectory + targetFileName;
        Assertions.assertTrue(Files.exists(Paths.get(fullPath)));

        loader.emptyCurFolder();

        loader.loadStartMap();
        Assertions.assertTrue(Files.exists(Paths.get(fullPath)));
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

        Level loaded = loader.loadFile("src/cache/progress/current/level1_cur.json");
        Level loaded2 = loader.loadCurLevelData();

        Assertions.assertEquals(loaded.getLevel(), loaded2.getLevel());
        Assertions.assertEquals(loaded.getMaze().getPlayer().getLevel(), loaded2.getMaze().getPlayer().getLevel());
        Assertions.assertEquals(loaded.getMaze().getPlayer().getHealth(), loaded2.getMaze().getPlayer().getHealth());

        loader.loadNextLevel(loaded2.getLevel());
        Assertions.assertEquals(loader.loadCurLevelData().getLevel(), 2);

        loader.emptyCurFolder();
        Level loaded3 = loader.loadStartMap();
        String targetFileName = "level1_cur.json";
        String currentDirectory = "src/cache/progress/current/";
        String fullPath = currentDirectory + targetFileName;
        Assertions.assertTrue(Files.exists(Paths.get(fullPath)));
        Level nextLevel = loader.loadNextLevel(loaded3.getLevel());
        targetFileName = "level2_cur.json";
        fullPath = currentDirectory + targetFileName;
        Assertions.assertTrue(Files.exists(Paths.get(fullPath)));
        Level finalLevel = loader.loadNextLevel(nextLevel.getLevel());
        targetFileName = "level3_cur.json";
        fullPath = currentDirectory + targetFileName;
        Assertions.assertTrue(Files.exists(Paths.get(fullPath)));

        loader.emptyCurFolder();

        saver.saveCurrentProgress(testLevel);
    }

}
