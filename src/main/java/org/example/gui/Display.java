package org.example.gui;
import org.example.Movement;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Yucheng Zhu
 * GUI to visualise the game
 */
public class  Display extends JFrame {
    private JTextArea textArea;
    private Movement movement;
    private Maze maze;
    private Gui gui;
    MovementEvents movementEvents;
    public Display() {

        // initialise the movement objects
        initialiseMovementObjects();

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
                // Get the text after considering change brought by movement
                String guiText = movementEvents.setGuiTextOnMovementKeysPressed(keyCode, movement, maze, gui);

                // FIXME: add other events

                // Update the GUI char "pixels" as a string
                if (guiText != null)
                    textArea.setText(guiText);
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
    public void initialiseMovementObjects() {
        movementEvents = new MovementEvents();

        Position currentPosition = new Position(20, 10);
        movement = new Movement();
        maze = new Maze(50, 50, new Position(1,1));
        maze.createNewPlayer(new Position(10,10));
        gui = new Gui();
    }
}
