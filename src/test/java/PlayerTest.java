import org.example.entity.Player;
import org.example.entity.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Yucheng Zhu
 * Test Player
 */
public class PlayerTest {
    /**
     * @author Yucheng Zhu
     * Test set player
     */
    @Test
    public void testSetPlayer() {
        Player player = new Player(50, 100, 1, new Position(2, 3));
        player.setLevel(3);
        Assertions.assertEquals(3, player.getLevel());
    }
}
