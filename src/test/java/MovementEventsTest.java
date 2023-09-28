import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Maze;
import org.example.gui.Gui;
import org.example.gui.MovementEvents;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.util.HashSet;

/**
 * @author Yucheng Zhu
 * Test MovementEvent
 */
public class MovementEventsTest {
    /**
     * @author Yucheng Zhu
     * Movement event keys
     */
    @Test
    public void testSetMovementKeys() {
        HashSet<Integer> movementKeys = MovementEvents.setMovementKeys();
        Assertions.assertTrue(MovementEvents.isInKeySet(movementKeys, KeyEvent.VK_W));
    }

    /**
     * @author Yucheng Zhu
     * Movement event keys
     */
    @Test
    public void testSetGuiTextOnMovementKeysPressed() {

        // Create maze
        String guiText;
        Maze maze = new Maze(3, 3, new Position(2, 2));
        maze.setPlayer(new Player(50, 100, 1, new Position(1, 1)));

        // check the initial position
        guiText = Gui.updateGuiString(maze);
        System.out.println(Gui.updateGuiString(maze));
        Assertions.assertEquals(
                "...\n" +
                        ".P.\n" +
                        "..X",
                guiText
        );

        // move up
        guiText = MovementEvents.setGuiTextOnMovementKeysPressed(KeyEvent.VK_W, maze);
        Assertions.assertEquals(
                ".P.\n" +
                        "...\n" +
                        "..X",
                guiText
        );

        // move down
        guiText = MovementEvents.setGuiTextOnMovementKeysPressed(KeyEvent.VK_S, maze);
        Assertions.assertEquals(
                "...\n" +
                        ".P.\n" +
                        "..X",
                guiText
        );


        // move left
        guiText = MovementEvents.setGuiTextOnMovementKeysPressed(KeyEvent.VK_A, maze);
        Assertions.assertEquals(
                "...\n" +
                        "P..\n" +
                        "..X",
                guiText
        );


        // move right
        guiText = MovementEvents.setGuiTextOnMovementKeysPressed(KeyEvent.VK_D, maze);
        Assertions.assertEquals(
                "...\n" +
                        ".P.\n" +
                        "..X",
                guiText
        );

    }

}
