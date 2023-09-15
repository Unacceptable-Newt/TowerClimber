package org.example.gui;
import org.example.gameLogic.Level;
import org.example.move.Movement;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

/**
 * @author Austin Zerk, Yucheng Zhu
 * GUI to visualise the game
 */
public class Display extends JFrame {
    private JTextPane textArea;
    private Movement movement;
    private Gui gui;
    private Level level;

    // Initialise events
    MovementEvents movementEvents;

    private HashSet<Integer> movementKeys;

    public boolean isMovementKeys(int key) {
        return movementKeys.contains(key);
    }

    private void setMovementKeys() {
        this.movementKeys = new HashSet<>();
        this.movementKeys.add(KeyEvent.VK_W);
        this.movementKeys.add(KeyEvent.VK_S);
        this.movementKeys.add(KeyEvent.VK_A);
        this.movementKeys.add(KeyEvent.VK_D);
    }

    public Display(int width, int height) {
        // Set movement keys only once
        setMovementKeys();

        // Create GUI
        this.setTitle("The Secret of the Princess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);

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

        // initialise the movement objects
        initialiseMovementObjects();
        textArea.setText(gui.updateGuiString(level.getMaze(), gui));

        // Listen to key events
        textArea.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                String guiText;
                int keyCode = e.getKeyCode();
                // Get the text after considering change brought by movement
                if (isMovementKeys(keyCode)) { // Movement events
                    movementEvents.setGuiTextOnMovementKeysPressed(keyCode, movement, level.getMaze(), gui);
                } else if (keyCode == KeyEvent.VK_E) { // exit event
                    level = ExitEvent.exit(movement, level);
                }

                // FIXME: add other events

                // Update the GUI char "pixels" as a string
                guiText = gui.updateGuiString(level.getMaze(), gui);

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

        movement = new Movement();

        level = new Level(1); // FIXME: load from file instead of creating a stubbed level when load is implemented

        // construct GUI
        gui = new Gui();
    }
}
