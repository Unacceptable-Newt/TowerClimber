import org.example.Movement;
import org.example.belonging.Weapon;
import org.example.entity.*;
import org.example.gameLogic.Maze;
import org.example.interaction.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Yucheng Zhu
 * Test Movement.
 */
public class MovementTest {
    Player player;
    private Movement movement;
    private Maze maze;

    /**
     * @author Yucheng Zhu
     * Recreate a new set of variables for each test
     */
    @BeforeEach
    private void init() {
        Position currentPosition = new Position(2, 2);
        player = new Player(10, 100, 1, currentPosition);
        player.setDirection(Direction.DOWN);
        movement = new Movement();

        // Set up maze
        int mazeX = 6;
        int mazeY = 6;
        maze = new Maze(mazeX, mazeY, new Position(1, 1));
//        maze.createNewPlayer(new Position(2, 2));

        // Add walls at the top boundary
        maze.addWall(new Position(0, 0),mazeX, false);

        // Add things
        maze.addItem(new Position(1, 3), new Weapon("The Big Axe",3, 5, 4));
        maze.addNPC(new Position(1, 4),new NPC("John", new Position(5, 5)));
        maze.addEnemy(new Position(1, 5),new Enemy(2, 2, 2));
    }

    /**
     * @author Yucheng Zhu
     * Test cannot move out of the map.
     * Change the direction but keep the position the same.
     */
    @Test
    public void testCannotStepOutsideTheBoundary() {
        // Move left OK, so long as it stays on the map
        player = (Player) movement.left(player, maze);
        Assertions.assertEquals(Direction.LEFT, player.getDirection());
        Assertions.assertEquals(new Position(1, 2), player.getPosition());

        player = (Player) movement.left(player, maze);
        Assertions.assertEquals(Direction.LEFT, player.getDirection());
        Assertions.assertEquals(new Position(0, 2), player.getPosition());

        // Cannot move up out of the left boundary
        player = (Player) movement.left(player, maze);
        Assertions.assertEquals(Direction.LEFT, player.getDirection());
        Assertions.assertEquals(new Position(0, 2), player.getPosition());

        // Test down boundary
        player = (Player) movement.down(player, maze);
        Assertions.assertEquals(Direction.DOWN, player.getDirection());
        Assertions.assertEquals(new Position(0, 3), player.getPosition());

        player = (Player) movement.down(player, maze);
        Assertions.assertEquals(Direction.DOWN, player.getDirection());
        Assertions.assertEquals(new Position(0, 4), player.getPosition());

        player = (Player) movement.down(player, maze);
        Assertions.assertEquals(Direction.DOWN, player.getDirection());
        Assertions.assertEquals(new Position(0, 5), player.getPosition());

        player = (Player) movement.down(player, maze);
        Assertions.assertEquals(Direction.DOWN, player.getDirection());
        Assertions.assertEquals(new Position(0, 5), player.getPosition());
    }

    /**
     * @author Yucheng Zhu
     * Test cannot move out of the map.
     * Change the direction but keep the position the same.
     */
    @Test
    public void testCannotStepOutsideTheRightBoundary() {
        player = (Player) movement.right(player, maze);
        Assertions.assertEquals(Direction.RIGHT, player.getDirection());
        Assertions.assertEquals(new Position(3, 2), player.getPosition());

        player = (Player) movement.right(player, maze);
        Assertions.assertEquals(Direction.RIGHT, player.getDirection());
        Assertions.assertEquals(new Position(4, 2), player.getPosition());

        player = (Player) movement.right(player, maze);
        Assertions.assertEquals(Direction.RIGHT, player.getDirection());
        Assertions.assertEquals(new Position(5, 2), player.getPosition());

        player = (Player) movement.right(player, maze);
        Assertions.assertEquals(Direction.RIGHT, player.getDirection());
        Assertions.assertEquals(new Position(5, 2), player.getPosition());
    }

    /**
     * @author Yucheng Zhu
     * Test cannot move into the faced adjacent cell.
     * Change the direction but keep the position the same.
     */
    @Test
    public void testCannotPassAWall() {
        // move up OK
        player = (Player) movement.up(player, maze);
        Assertions.assertEquals(Direction.UP, player.getDirection());
        Assertions.assertEquals(new Position(2, 1), player.getPosition());

        // Cannot move up pass the wall
        player = (Player) movement.up(player, maze);
        Assertions.assertEquals(Direction.UP, player.getDirection());
        Assertions.assertEquals(new Position(2, 1), player.getPosition());
    }

    /**
     * @author Yucheng Zhu
     * Test cannot move into the faced adjacent cell.
     * Change the direction but keep the position the same.
     */
    @Test
    public void testCannotWalkIntoThings() {
        // Move down OK
        player = (Player) movement.down(player, maze);
        Assertions.assertEquals(Direction.DOWN, player.getDirection());
        Assertions.assertEquals(new Position(2, 3), player.getPosition());

        // Cannot walk into an item
        player = (Player) movement.left(player, maze);
        Assertions.assertEquals(Direction.LEFT, player.getDirection());
        Assertions.assertEquals(new Position(2, 3), player.getPosition());

        // Move down OK
        player = (Player) movement.down(player, maze);
        Assertions.assertEquals(Direction.DOWN, player.getDirection());
        Assertions.assertEquals(new Position(2, 4), player.getPosition());

        // Cannot walk into an NPC
        player = (Player) movement.left(player, maze);
        Assertions.assertEquals(Direction.LEFT, player.getDirection());
        Assertions.assertEquals(new Position(2, 4), player.getPosition());

        // Move down OK
        player = (Player) movement.down(player, maze);
        Assertions.assertEquals(Direction.DOWN, player.getDirection());
        Assertions.assertEquals(new Position(2, 5), player.getPosition());

        // Cannot walk into an Enemy
        player = (Player) movement.left(player, maze);
        Assertions.assertEquals(Direction.LEFT, player.getDirection());
        Assertions.assertEquals(new Position(2, 5), player.getPosition());
    }

    /**
     * @author Yucheng Zhu
     * Test moving up
     */
    @Test
    public void testUp() {
        Life life = movement.up(player, maze);
        Assertions.assertEquals(Direction.UP, life.getDirection());
        Assertions.assertEquals(new Position(2, 1), life.getPosition());
    }

    /**
     * @author Yucheng Zhu
     * Test moving down
     */
    @Test
    public void testDown() {
        Life life = movement.down(player, maze);
        Assertions.assertEquals(Direction.DOWN, life.getDirection());
        Assertions.assertEquals(new Position(2, 3), life.getPosition());
    }

    /**
     * @author Yucheng Zhu
     * Test moving left
     */
    @Test
    public void testLeft() {
        Life life = movement.left(player, maze);
        Assertions.assertEquals(Direction.LEFT, life.getDirection());
        Assertions.assertEquals(new Position(1, 2), life.getPosition());
    }

    /**
     * @author Yucheng Zhu
     * Test moving right
     */
    @Test
    public void testRight() {
        Life life = movement.right(player, maze);
        Assertions.assertEquals(Direction.RIGHT, life.getDirection());
        Assertions.assertEquals(new Position(3, 2), life.getPosition());
    }
}

