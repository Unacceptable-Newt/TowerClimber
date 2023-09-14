package org.example.gui;
import org.example.Movement;
import org.example.belonging.Weapon;
import org.example.entity.Enemy;
import org.example.entity.NPC;
import org.example.entity.Position;
import org.example.gameLogic.Maze;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Austin Zerk, Yucheng Zhu
 * GUI to visualise the game
 */
public class Display extends JFrame {
    private JTextPane textArea;
    private Movement movement;
    private Maze maze;
    private Gui gui;
    MovementEvents movementEvents;
    public Display() {

        // initialise the movement objects
        initialiseMovementObjects();

        // Create GUI
        this.setTitle("The Secret of the Princess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setSize(1350, 780);
        this.setSize(800, 1080);

        // Add text area
        textArea = new JTextPane();

        // Make all characters evenly sized
        SimpleAttributeSet spacingSet = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(spacingSet, -0.25f);
        textArea.setParagraphAttributes(spacingSet, false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
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
                if (guiText != null) {
                    textArea.setText(guiText);
                }
            }
        });

        // Make the GUI visible
        this.setVisible(true);
    }

    /**
     * @author Austin Zerk, Yucheng Zhu
     * Stubbing player's data to test movement.
     * TODO: replace this method from objects in the Maze when it finishes.
     */
    public void initialiseMovementObjects() {
        movementEvents = new MovementEvents();

        Position currentPosition = new Position(20, 10);
        movement = new Movement();
        int mazeX = 35;
        int mazeY = 35;
        maze = new Maze(mazeX, mazeY, new Position(1, 1));
        maze.createNewPlayer(new Position(10, 10));
        maze.addWall(new Position(0, 0),mazeX, false);
        maze.addWall(new Position(0, 0),mazeY, true);
        maze.addWall(new Position(mazeX - 1, 0), mazeY, true);
        maze.addWall(new Position(0, mazeY - 1), mazeX, false);
        maze.addItem(new Position(30, 25), new Weapon("The Big Axe",3, 5, 4));
        maze.addNPC(new Position(5, 5),new NPC("John", new Position(5, 5)));
        maze.addEnemy(new Position(25, 20),new Enemy(2, 2, 2));
        gui = new Gui();
    }

    public static void main(String[] args) {
        // Call the GUI
        Display gui = new Display();
    }
}
