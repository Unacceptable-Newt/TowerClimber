import org.example.IO.JsonLoad;
import org.example.IO.JsonSave;
import org.example.belonging.Inventory;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Level;
import org.example.gameLogic.Maze;
import org.example.gui.ExitEvent;
import org.example.interaction.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Yucheng Zhu
 * Test ExitEvent
 */
public class ExitEventTest {
    /**
     * @author Yucheng Zhu
     * Test fail to exit the current level
     */
    @Test
    public void testExitEventNotNearAnExit() {
        // Set up a level where the player is facing the exit, because the player's not facing the exit
        Level level = new Level(1);
        Inventory inventory = new Inventory(5);
        Maze maze = new Maze(30, 20, new Position(1, 1));
        Player player = new Player(50, 100, 1, new Position(1, 2));
        player.setDirection(Direction.DOWN);
        maze.setPlayer(player);
        level.setMaze(maze);

        // Stay at the current level
        Assertions.assertEquals(1, ExitEvent.exit(level,inventory).getLevel());
    }

    /**
     * @author Yucheng Zhu
     * Test succeeds in exiting the current level, because the player's facing the exit
     */
    @Test
    public void testExitEventFacingAnExit() {
        // Set up a level where the player is facing the exit
        Level level = new Level(1);
        Inventory inventory = new Inventory(5);
        Maze maze = new Maze(30, 20, new Position(1, 1));
        Player player = new Player(50, 100, 1, new Position(1, 2));
        player.setDirection(Direction.UP);
        maze.setPlayer(player);
        level.setMaze(maze);
        JsonSave saver = new JsonSave();
        saver.saveCurrentProgress(level);

        // Go to the next level
        Assertions.assertEquals(2, ExitEvent.exit(level, inventory).getLevel());
        saver.emptyCurFolder();

    }
}
