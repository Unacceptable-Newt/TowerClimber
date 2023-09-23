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
    @BeforeEach
    private void init() {

        Position currentPosition = new Position(2, 5);
        player = new Player(10, 100, 1, currentPosition);
        player.setDirection(Direction.LEFT);

        // Set up maze
        int mazeX = 6;
        int mazeY = 6;
        maze = new Maze(mazeX, mazeY, new Position(1, 1));

        maze.setPlayer(player);

        // Add walls at the top boundary
        maze.addWall(new Position(0, 0),mazeX, false);

        //dialog for NPC whill be changed in future versions
        List<String> dialog = new ArrayList<>();
        dialog.add("Why Hello There");

        // Add things
        maze.addItem(new Position(1, 3), new Weapon("The Big Axe",3, 5, 4));
        maze.addNPC(new Position(1, 4),new NPC("John", new Position(5, 5),dialog));
        maze.addEnemy(new Position(1, 5),new Enemy(2, 2, 2));
    }
    @Test
    public void killEnemyTest(){
        EnemyFighter enemyFighter = new EnemyFighter();
        Inventory inventory = new Inventory(5);
        enemyFighter.interactWithAdjacent(inventory,maze);
        Assertions.assertNull(maze.getEnemyAtPosition(new Position(1, 5)));
        Assertions.assertNotNull(maze.getPlayer());
        Assertions.assertEquals(100, player.getHealth());
    }
    @Test
    public void NoEnemyTest(){
        EnemyFighter enemyFighter = new EnemyFighter();
        Inventory inventory = new Inventory(5);
        player.setDirection(Direction.UP);
        enemyFighter.interactWithAdjacent(inventory, maze);

        Assertions.assertNotNull(maze.getEnemyAtPosition(new Position(1, 5)));
        Assertions.assertEquals(2, maze.getEnemyAtPosition(new Position(1, 5)).getHealth());
        Assertions.assertNotNull(maze.getPlayer());
        Assertions.assertEquals(100, player.getHealth());
    }
}

