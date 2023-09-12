import org.example.Movement;
import org.example.entity.Life;
import org.example.entity.Player;
import org.example.entity.Position;
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
        Position currentPosition = new Position(1, 1);
        player = new Player(10, 100, 1, currentPosition);
        player.setDirection(Direction.DOWN);
        movement = new Movement();
        maze = new Maze(50, 50, new Position(1,1));
    }

    /**
     * @author Yucheng Zhu
     * Test cannot move into the faced adjacent cell.
     * Change the direction but keep the position the same.
     */
    @Test
    public void testCannotMove() {
        // FIXME after Maze is done
    }

    /**
     * @author Yucheng Zhu
     * Test moving up
     */
    @Test
    public void testUp() {
        Life life = movement.up(player, maze);
        Assertions.assertEquals(Direction.UP, life.getDirection());
        Assertions.assertEquals(new Position(1, 0), life.getPosition());
    }

    /**
     * @author Yucheng Zhu
     * Test moving down
     */
    @Test
    public void testDown() {
        Life life = movement.down(player, maze);
        Assertions.assertEquals(Direction.DOWN, life.getDirection());
        Assertions.assertEquals(new Position(1, 2), life.getPosition());
    }

    /**
     * @author Yucheng Zhu
     * Test moving left
     */
    @Test
    public void testLeft() {
        Life life = movement.left(player, maze);
        Assertions.assertEquals(Direction.LEFT, life.getDirection());
        Assertions.assertEquals(new Position(0, 1), life.getPosition());
    }

    /**
     * @author Yucheng Zhu
     * Test moving right
     */
    @Test
    public void testRight() {
        Life life = movement.right(player, maze);
        Assertions.assertEquals(Direction.RIGHT, life.getDirection());
        Assertions.assertEquals(new Position(2, 1), life.getPosition());
    }
}

