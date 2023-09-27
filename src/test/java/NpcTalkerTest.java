import org.example.IO.JsonLoad;
import org.example.belonging.Inventory;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Level;
import org.example.interaction.Direction;
import org.example.interaction.NpcTalker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author Yucheng Zhu
 * Test NpcTalker
 */
public class NpcTalkerTest {
    Inventory inventory;
    Level level;

    JsonLoad loader = new JsonLoad();

    @BeforeEach
    public void init() {
        level = loader.loadCurLevelData();
        inventory = new Inventory(5);
        level.getMaze().setPlayer(new Player(250, 100, 1, new Position(4, 4)));
        level.getMaze().getPlayer().setDirection(Direction.DOWN);
    }

    /**
     * @author Yucheng Zhu
     * Test talking to an NPC in front of the player loads the correct dialogue text
     */
    @Test
    public void testNpcTalker() throws IOException {

        Assertions.assertEquals(
                "King George's Chief Councillor: 'Tis the fifth time that the princess was kidnapped! Methinks a great mystery surrounds her. Oh mighty hero, thou art the last hope of our land and ... the 5689th person to accept his majesty's missions.",
                NpcTalker.interactWithAdjacent(inventory, level, "")
        );

        // no conversation in a different direction:
        level.getMaze().getPlayer().setDirection(Direction.UP);
        Assertions.assertEquals(
                "",
                NpcTalker.interactWithAdjacent(inventory, level, "")
        );


        loader.emptyCurFolder();
    }
}
