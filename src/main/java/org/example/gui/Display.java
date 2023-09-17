package org.example.gui;
import org.example.move.Movement;
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
import org.example.gameLogic.Level;


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
    private Level level;
    private ItemPicker itemPicker;
    private MoneyPicker moneyPicker;
    private Inventory inventory;

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
        // initialise the picker objects
        initialisePickerObjects();
        textArea.setText(Gui.updateGuiString(level.getMaze()));

        // Listen to key events
        textArea.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                String guiText;
                int keyCode = e.getKeyCode();
                // Get the text after considering change brought by movement
                if (isMovementKeys(keyCode)) { // Movement events
                    MovementEvents.setGuiTextOnMovementKeysPressed(keyCode, movement, level.getMaze());
                } else if (keyCode == KeyEvent.VK_E) { // exit event
                    level = ExitEvent.exit(movement, level);
                }

                // FIXME: add other events

                // Update the GUI char "pixels" as a string
                guiText = Gui.updateGuiString(level.getMaze());

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

        movement = new Movement();

        level = new Level(1); // FIXME: load from file instead of creating a stubbed level when load is implemented
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
        if (keyCode == KeyEvent.VK_E) {
            int size = inventory.getItems().size();
            // Trigger the ItemPicker operation
            // You might call your ItemPicker method here
            Pair<Player, Inventory> playerInventoryPair = itemPicker.interactWithAdjacent(inventory, level.getMaze());

            // Update the GUI text
            String newItemText = "You picked up an item!";
            if(playerInventoryPair.second().getItems().size()>size){
                if (keyCode == KeyEvent.VK_E) {
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
        String guiText = Gui.updateGuiString(level.getMaze());
        if (guiText != null) {
            textArea.setText(guiText);
        }

    }
}
