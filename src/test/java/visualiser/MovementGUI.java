package visualiser;
import org.example.Gui;
import org.example.Movement;
import org.example.PersistentDataNames;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Maze;
import org.example.interaction.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

/**
 * @author Yucheng Zhu
 * GUI to visualise movement
 * IMPORTANT: this class will be removed when the Game and Gui classes are implemeted.
 * DO NOT SPEND A LOT OF TIME DEVELOPING THIS. Implement Gui and Game instead.
 */
public class MovementGUI extends JFrame {
    private JTextArea textArea;

    Player player;
    private Movement movement;
    private Maze maze;
    private Gui gui;
    public MovementGUI() {

        // Stubbing objects
        // TODO: replace this method from objects in the Maze when it finishes.
        stubbingMovementObjects();

        // Create GUI
        this.setTitle("Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 700);

        // Add text area
        textArea = new JTextArea();

        // Make all characters evenly sized
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        this.add(scrollPane);

        // Listen to key events
        textArea.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                Direction direction = null;
                switch (keyCode) {
                    case KeyEvent.VK_W -> direction = Direction.UP;
                    case KeyEvent.VK_S -> direction = Direction.DOWN;
                    case KeyEvent.VK_A -> direction = Direction.LEFT;
                    case KeyEvent.VK_D -> direction = Direction.RIGHT;
                }

                if (direction != null) {
                    String guiText = updateGuiStringOnMovementKeyPressed(player, movement, maze, gui, direction);
                    textArea.setText(guiText);
                }
            }
        });


        // Make the GUI visible
        this.setVisible(true);
    }

    /**
     * @author Yucheng Zhu
     * Stubbing player's data to test movement.
     * TODO: replace this method from objects in the Maze when it finishes.
     */
    public void stubbingMovementObjects() {
        Position currentPosition = new Position(20, 10);
        player = new Player(10, 100, 1, currentPosition);
        movement = new Movement();
        maze = new Maze(50, 50);
        gui = new Gui();
    }

    /**
     * @author Yucheng Zhu
     * Return strings to be displayed in GUI when a movement key is pressed
     * @param player PlayerObject
     */
    public String updateGuiStringOnMovementKeyPressed(Player player, Movement movement, Maze maze, Gui gui, Direction direction) {
        player = (Player) movement.move(player, direction, maze);

        HashMap<PersistentDataNames, Object> gameObjects = new HashMap<>();
        gameObjects.put(PersistentDataNames.PLAYER, player);

        char[][] rasterise = gui.rasterise(gameObjects);
        return gui.flatten(rasterise);
    }


}
