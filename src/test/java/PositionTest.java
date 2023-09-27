import org.example.entity.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Yucheng Zhu
 * Test Position
 */
public class PositionTest {
    /**
     * @author Yucheng Zhu
     * Test position setters
     */
    @Test
    public void testSetter() {
        Position position = new Position(1, 1);
        position.setX(5);
        position.setY(7);
        Assertions.assertEquals(5, position.getX());
        Assertions.assertEquals(7, position.getY());
    }
}
