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
    @BeforeAll
    static void before(){
        testMaze = new Maze(40,20,new Position(1,1));
        testMaze.createNewPlayer(new Position(10,10));
        testMaze.addEnemy(new Position( 30,10),
                new Enemy("Cerberus",4,6, 2));
        testMaze.addWall(new Position(1,0),19,true);
        testMaze.addWall(new Position(39,0),20,true);
        testMaze.addWall(new Position(0,19),40,false);
        testMaze.addWall(new Position(0,0),40,false);
        testMaze.addWall(new Position(3,0),8,true);
        testMaze.addWall(new Position(3,10),10,true);
        testMaze.addNPC(new NPC("JASON",new Position(1,2)));
        testMaze.addItem(new Position(18,7),new Weapon("Gramps", 4,2,9));
        testMaze.addItem(new Position(18,10),new Weapon("Fring", 2,7,2));
        testLevel = new Level(1);
        testLevel.setMaze(testMaze);
    }

    @Test
    void saveTest(){
        JsonSave saver = new JsonSave();
        saver.saveCurrentProgress(testLevel);
        assert (true);
    }

    @Test
    void loadTest(){
        JsonLoad loader = new JsonLoad();
        Level loaded = loader.loadFile("src/cache/progress/current/level1_cur.json");
        Level loaded2 = loader.loadCurLevelData();
        System.out.println(loaded2.toString());
        assert (true);
    }

}
