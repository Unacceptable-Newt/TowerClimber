import org.example.IO.JsonSave;
import org.example.entity.Enemy;
import org.example.entity.NPC;
import org.example.entity.Position;
import org.example.gameLogic.Level;
import org.example.gameLogic.Maze;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

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
    @BeforeAll
    static void before(){
        testMaze = new Maze(40,20,new Position(1,1));
        testMaze.createNewPlayer(new Position(10,10));
        testMaze.addEnemy(new Position( 30,10),
                new Enemy(4,6, 2));
        testMaze.addWall(new Position(1,0),19,true);
        testMaze.addWall(new Position(39,0),20,true);
        testMaze.addWall(new Position(0,19),40,false);
        testMaze.addWall(new Position(0,0),40,false);
        testMaze.addWall(new Position(3,0),8,true);
        testMaze.addWall(new Position(3,10),10,true);
        testMaze.addNPC(new Position(1,2),
                new NPC("JASON",new Position(1,2),new ArrayList<>()));
        testLevel = new Level(1);
        testLevel.setMaze(testMaze);
    }

    @Test
    void saveTest(){
        JsonSave saver = new JsonSave();
        saver.saveCurrentProgress(testLevel);
        assert (true);
    }

}
