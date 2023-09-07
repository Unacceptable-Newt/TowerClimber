import org.example.interaction.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Yucheng Zhu
 * Test Direction
 */
public class DirectionTest {

    /**
     * @author Yucheng Zhu
     * Make sure it works as expected
     */
    @Test
    public void testDirections() {
        Assertions.assertNotEquals(Direction.UP, Direction.DOWN);
        Assertions.assertNotEquals(Direction.UP, Direction.LEFT);
        Assertions.assertNotEquals(Direction.UP, Direction.RIGHT);
    }
}
