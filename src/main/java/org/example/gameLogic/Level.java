package org.example.gameLogic;

import org.example.IO.JsonLoad;
import org.example.IO.JsonSave;
import org.example.belonging.Weapon;
import org.example.entity.Enemy;
import org.example.entity.NPC;
import org.example.entity.Position;
import org.example.move.LevelStates;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Yucheng Zhu
 * High-level game logic: Everything that can be done in this level.
 */
public class Level {
    private LevelStates levelStates = LevelStates.MAZE;

    private int level;

    Maze maze;

    public int getLevel() {
        return level;
    }

    /**
     * @author Yucheng Zhu
     * Level should only be set at the beginning of the game
     * @param level Current level
     */
    private void setLevel(int level) {
        this.level = level;
    }

    public Maze getMaze() {
        return maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    public LevelStates getLevelStates() {
        return levelStates;
    }

    public void setLevelStates(LevelStates levelStates) {
        this.levelStates = levelStates;
    }

    /**
     * @author Yucheng Zhu
     * Start a new level by loading data from the file
     * @param currentGameLevel Current game level
     */
    public Level(int currentGameLevel) {
        setLevel(currentGameLevel);
        setLevelStates(LevelStates.MAZE);

        // FIXME: Load data from the file when load is implemented
        // REPLACE THE STUBBED DATA
        // set up maze
        int mazeX = 65;
        int mazeY = 20;
        maze = new Maze(mazeX, mazeY, new Position(1, 1));
        maze.createNewPlayer(new Position(10, 10));

        // add walls at the boundary
        maze.addWall(new Position(0, 0),mazeX, false);
        maze.addWall(new Position(0, 0),mazeY, true);
        maze.addWall(new Position(mazeX - 1, 0), mazeY, true);
        maze.addWall(new Position(0, mazeY - 1), mazeX, false);

        // add things
        maze.addItem(new Position(30, 25), new Weapon("The Big Axe",3, 5, 4));
        maze.addItem(new Position(31, 25), new Weapon("The Small Axe",3, 5, 4));
        maze.addItem(new Position(32, 25), new Weapon("The medium Axe",3, 5, 4));
        maze.addItem(new Position(33, 25), new Weapon("The crazy cat",3, 5, 4));
        maze.addItem(new Position(28, 25), new Weapon("The ugly fly",3, 5, 4));
        maze.addItem(new Position(29, 25), new Weapon("The dragon knife",3, 5, 4));
        maze.addNPC(new Position(4, 5), new NPC("King George's Chief Councillor", new Position(4, 5)));
        maze.addEnemy(new Position(25, 20),new Enemy(2, 2, 2));
         maze.addEnemy(new Position(5, 4),new Enemy(2, 2, 2));

    }



    /**
     * @author Yucheng Zhu
     * Play the current level
     *
     * @return Next level (i.e. player goes to the next floor)
     */
    public int play() {
        // FIXME
        return 1;
    }

    /**
     * @author
     *
     * Save the current level to the storage
     */
    public void save() {
        JsonSave saver= new JsonSave();
        saver.saveCurrentProgress(this);
        // FIXME
    }
}
