package org.example.gui;
import org.example.Movement;
import org.example.belonging.Inventory;
import org.example.belonging.Weapon;
import org.example.entity.Enemy;
import org.example.entity.NPC;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Maze;
import org.example.interaction.ItemPicker;
import org.example.interaction.MoneyPicker;
import org.example.util.Pair;

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
    private ItemPicker itemPicker;
    private MoneyPicker moneyPicker;
    private Inventory inventory;
    public Display() {

        // Create GUI
        this.setTitle("The Secret of the Princess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(560, 970);

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
        // initialise the picker objects
        initialisePickerObjects();
        textArea.setText(gui.updateGuiString(maze, gui));

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
                pickStuff(e);
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

        // set up maze
        int mazeX = 35;
        int mazeY = 35;
        maze = new Maze(mazeX, mazeY, new Position(1, 1));
        maze.createNewPlayer(new Position(10, 10));

        // add walls at the boundary
        maze.addWall(new Position(0, 0),mazeX, false);
        maze.addWall(new Position(0, 0),mazeY, true);
        maze.addWall(new Position(mazeX - 1, 0), mazeY, true);
        maze.addWall(new Position(0, mazeY - 1), mazeX, false);

        // add things
        maze.addItem(new Position(30, 25), new Weapon("The Big Axe",3, 5, 4));
        maze.addNPC(new Position(5, 5),new NPC("John", new Position(5, 5)));
        maze.addEnemy(new Position(25, 20),new Enemy(2, 2, 2));
        gui = new Gui();
    }

    /**
     * Rong Sun
     */

    public void initialisePickerObjects() {
        this.inventory = new Inventory(5);
        this.itemPicker  = new ItemPicker();
        this.moneyPicker = new MoneyPicker();

    }

    /**
     * Rong Sun
     * @param e
     */
    public void pickStuff(KeyEvent e){

        int keyCode = e.getKeyCode();

        // Check if the "P" key is pressed
        if (keyCode == KeyEvent.VK_P) {
            int size = inventory.getItems().size();
            // Trigger the ItemPicker operation
            // You might call your ItemPicker method here
            Pair<Player, Inventory> playerInventoryPair = itemPicker.interactWithAdjacent(inventory, maze);

            // Update the GUI text
            String newItemText = "You picked up an item!";
            if(playerInventoryPair.second().getItems().size()>size){
                if (keyCode == KeyEvent.VK_P) {
                    //A pop-up dialog box
                    JOptionPane.showMessageDialog(null, "picked up", "pick message", JOptionPane.INFORMATION_MESSAGE);

                    // Create a timer and close the prompt after a few seconds
                    Timer timer = new Timer(3000, new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                            // Close prompt box
                            Window[] windows = JOptionPane.getRootFrame().getOwnedWindows();
                            for (Window window : windows) {
                                if (window instanceof JDialog) {
                                    JDialog dialog = (JDialog) window;
                                    if (dialog.getContentPane().getComponentCount() == 1
                                            && dialog.getContentPane().getComponent(0) instanceof JOptionPane) {
                                        dialog.dispose();
                                    }
                                }
                            }
                        }
                    });

                    // Start timer, execute after 3000 milliseconds
                    timer.setRepeats(false);
                    timer.start();

                }
            }



        }

        // FIXME: Handle other events as needed

        // Update the GUI char "pixels" as a string
        // You can keep this part if it's relevant to your game
//        String guiText = movementEvents.setGuiTextOnMovementKeysPressed(keyCode, movement, maze, gui);
        String guiText = gui.updateGuiString(maze, gui);
        if (guiText != null) {
            textArea.setText(guiText);
        }

    }
}
