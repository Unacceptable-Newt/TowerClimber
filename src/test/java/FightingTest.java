import org.example.belonging.Inventory;
import org.example.belonging.Weapon;
import org.example.entity.Enemy;
import org.example.entity.NPC;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Maze;
import org.example.interaction.Direction;
import org.example.interaction.EnemyFighter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FightingTest {

    private Player player;
    private Maze maze;
    private final Inventory inventory = new Inventory(5);

    private final EnemyFighter enemyFighter = new EnemyFighter();
    @BeforeEach
    private void init() {

        Position currentPosition = new Position(2, 5);
        player = new Player(10, 100, 1, currentPosition);
        player.setDirection(Direction.LEFT);
        player.setCurrentWeapon(new Weapon("The Destroyer",7,7,12));

        // Set up maze
        int mazeX = 6;
        int mazeY = 6;
        maze = new Maze(mazeX, mazeY, new Position(1, 1));

        maze.setPlayer(player);

        // Add walls at the top boundary
        maze.addWall(new Position(0, 0),mazeX, false);

        // Add things
        maze.addItem(new Position(1, 3), new Weapon("The Big Axe",3, 5, 4));
        maze.addNPC(new NPC("John", new Position(1, 4)));
        maze.addEnemy(new Position(1, 5),new Enemy(2, 2, 0));
    }
    @Test
    public void killEnemyTest(){
        enemyFighter.interactWithAdjacent(inventory,maze);
        Assertions.assertNull(maze.getEnemyAtPosition(new Position(1, 5)));
        Assertions.assertNotNull(maze.getPlayer());
        Assertions.assertEquals(100, player.getHealth());
    }
    @Test
    public void NoEnemyTest(){
        player.setDirection(Direction.UP);
        enemyFighter.interactWithAdjacent(inventory, maze);

        Assertions.assertNotNull(maze.getEnemyAtPosition(new Position(1, 5)));
        Assertions.assertEquals(2, maze.getEnemyAtPosition(new Position(1, 5)).getHealth());
        Assertions.assertNotNull(maze.getPlayer());
        Assertions.assertEquals(100, player.getHealth());
    }

    @Test
    public void NoWeaponTest(){
        player.setCurrentWeapon(null);
        enemyFighter.interactWithAdjacent(inventory,maze);

        Assertions.assertNotNull(maze.getEnemyAtPosition(new Position(1,5)));
        Assertions.assertEquals(1, maze.getEnemyAtPosition(new Position(1, 5)).getHealth());
        Assertions.assertNotNull(maze.getPlayer());
        Assertions.assertEquals(99, player.getHealth());
    }
}

