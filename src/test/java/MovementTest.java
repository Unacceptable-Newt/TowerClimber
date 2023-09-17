import org.example.belonging.Item;
import org.example.move.Movement;
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

        // Set up maze
        int mazeX = 6;
        int mazeY = 6;
        maze = new Maze(mazeX, mazeY, new Position(1, 1));

        maze.setPlayer(player);

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
        Movement.left(player, maze);
        Assertions.assertEquals(Direction.LEFT, player.getDirection());
        Assertions.assertEquals(new Position(1, 2), player.getPosition());

        Movement.left(player, maze);
        Assertions.assertEquals(Direction.LEFT, player.getDirection());
        Assertions.assertEquals(new Position(0, 2), player.getPosition());

        // Cannot move up out of the left boundary
        Movement.left(player, maze);
        Assertions.assertEquals(Direction.LEFT, player.getDirection());
        Assertions.assertEquals(new Position(0, 2), player.getPosition());

        // Test down boundary
        Movement.down(player, maze);
        Assertions.assertEquals(Direction.DOWN, player.getDirection());
        Assertions.assertEquals(new Position(0, 3), player.getPosition());

        Movement.down(player, maze);
        Assertions.assertEquals(Direction.DOWN, player.getDirection());
        Assertions.assertEquals(new Position(0, 4), player.getPosition());

        Movement.down(player, maze);
        Assertions.assertEquals(Direction.DOWN, player.getDirection());
        Assertions.assertEquals(new Position(0, 5), player.getPosition());/**/

        Movement.down(player, maze);
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
        Movement.right(player, maze);
        Assertions.assertEquals(Direction.RIGHT, player.getDirection());
        Assertions.assertEquals(new Position(3, 2), player.getPosition());

        Movement.right(player, maze);
        Assertions.assertEquals(Direction.RIGHT, player.getDirection());
        Assertions.assertEquals(new Position(4, 2), player.getPosition());

        Movement.right(player, maze);
        Assertions.assertEquals(Direction.RIGHT, player.getDirection());
        Assertions.assertEquals(new Position(5, 2), player.getPosition());

        Movement.right(player, maze);
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
        Movement.up(player, maze);
        Assertions.assertEquals(Direction.UP, player.getDirection());
        Assertions.assertEquals(new Position(2, 1), player.getPosition());

        // Cannot move up pass the wall
        Movement.up(player, maze);
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
        Movement.down(player, maze);
        Assertions.assertEquals(Direction.DOWN, player.getDirection());
        Assertions.assertEquals(new Position(2, 3), player.getPosition());

        // Cannot walk into an item
        Movement.left(player, maze);
        Assertions.assertEquals(Direction.LEFT, player.getDirection());
        Assertions.assertEquals(new Position(2, 3), player.getPosition());

        // Move down OK
        Movement.down(player, maze);
        Assertions.assertEquals(Direction.DOWN, player.getDirection());
        Assertions.assertEquals(new Position(2, 4), player.getPosition());

        // Cannot walk into an NPC
        Movement.left(player, maze);
        Assertions.assertEquals(Direction.LEFT, player.getDirection());
        Assertions.assertEquals(new Position(2, 4), player.getPosition());

        // Move down OK
        Movement.down(player, maze);
        Assertions.assertEquals(Direction.DOWN, player.getDirection());
        Assertions.assertEquals(new Position(2, 5), player.getPosition());

        // Cannot walk into an Enemy
        Movement.left(player, maze);
        Assertions.assertEquals(Direction.LEFT, player.getDirection());
        Assertions.assertEquals(new Position(2, 5), player.getPosition());
    }

    /**
     * @author Yucheng Zhu
     * Test moving up
     */
    @Test
    public void testUp() {
        Life life = Movement.up(player, maze);
        Assertions.assertEquals(Direction.UP, life.getDirection());
        Assertions.assertEquals(new Position(2, 1), life.getPosition());
    }

    /**
     * @author Yucheng Zhu
     * Test moving down
     */
    @Test
    public void testDown() {
        Life life = Movement.down(player, maze);
        Assertions.assertEquals(Direction.DOWN, life.getDirection());
        Assertions.assertEquals(new Position(2, 3), life.getPosition());
    }

    /**
     * @author Yucheng Zhu
     * Test moving left
     */
    @Test
    public void testLeft() {
        Life life = Movement.left(player, maze);
        Assertions.assertEquals(Direction.LEFT, life.getDirection());
        Assertions.assertEquals(new Position(1, 2), life.getPosition());
    }

    /**
     * @author Yucheng Zhu
     * Test moving right
     */
    @Test
    public void testRight() {
        Life life = Movement.right(player, maze);
        Assertions.assertEquals(Direction.RIGHT, life.getDirection());
        Assertions.assertEquals(new Position(3, 2), life.getPosition());
    }

    /**
     * @author Yucheng Zhu
     * Test getting object at the position
     */
    @Test
    public void getObjectAtPosition() {
        // test nothing at the position
        Assertions.assertNull(Movement.getObjectAtPosition(maze, new Position(1, 2)));

        // test player at the position
        Assertions.assertTrue(Movement.getObjectAtPosition(maze, new Position(2, 2)) instanceof Player);

        // test item at the position
        Assertions.assertTrue(Movement.getObjectAtPosition(maze, new Position(1, 3)) instanceof Item);

        // test weapon at the position
        Assertions.assertTrue(Movement.getObjectAtPosition(maze, new Position(1, 3)) instanceof Weapon);

        // test NPC at the position
        Assertions.assertTrue(Movement.getObjectAtPosition(maze, new Position(1, 4)) instanceof NPC);

        // test enemy at the position
        Assertions.assertTrue(Movement.getObjectAtPosition(maze, new Position(1, 5)) instanceof Enemy);

        // test exit at the position
        Assertions.assertTrue(Movement.getObjectAtPosition(maze, new Position(1, 1)) instanceof Position);
    }

    /**
     * @author Yucheng Zhu
     * Test getting the frontal object
     */
    @Test
    public void testGetFrontalObject() {
        // test nothing at the position
        Assertions.assertNull(Movement.getPlayerFrontalObject(maze));
    }
}

