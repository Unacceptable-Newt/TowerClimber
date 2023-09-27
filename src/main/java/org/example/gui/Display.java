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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

/**
 * @author Austin Zerk, Yucheng Zhu
 * GUI to visualise the game
 */
public class Display extends JFrame {
    private static JTextPane textArea;
    private static Level level;
    private static ItemPicker itemPicker;
    private static MoneyPicker moneyPicker;
    private static Inventory inventory;

    private HashSet<Integer> movementKeys;

    private static String Inventory = "";
    private static String displayMaze = "";
    private static String dialog = "";

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

    public static void TextDisplay(){
        // initialise the movement objects
        initialiseMovementObjects();
        // initialise the picker objects
        initialisePickerObjects();

        displayMaze = Gui.updateGuiString(level.getMaze());
        System.out.println(displayMaze);

        InputStreamReader sin = new InputStreamReader(System.in);
        char ch;
        while (true){
            try {
                ch = (char) sin.read();
                if (ch == 'q') break;
                //move the player if movement keys are pressed passing the keycode for jPane compatibility
                else if (ch == 'w') MovementEvents.setGuiTextOnMovementKeysPressed(87,level.getMaze());
                else if (ch == 'a') MovementEvents.setGuiTextOnMovementKeysPressed(65,level.getMaze());
                else if (ch == 's') MovementEvents.setGuiTextOnMovementKeysPressed(83,level.getMaze());
                else if (ch == 'd') MovementEvents.setGuiTextOnMovementKeysPressed(68,level.getMaze());
                // interaction events
                else if (ch == 'e') {
                    level = ExitEvent.exit(level);
                    pickStuff(69, true);
                }

                //update the maze string
                displayMaze = Gui.updateGuiString(level.getMaze());

                //print the display
                //System.out.println(Inventory);
                System.out.println(displayMaze);
                //System.out.println(dialog);

            // if getting the input fails inform the user but do not stop the program
            } catch (IOException e){
                System.err.println("Failed to get character from input");
            }
        }
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
                    MovementEvents.setGuiTextOnMovementKeysPressed(keyCode, level.getMaze());
                } else if (keyCode == KeyEvent.VK_E) { // exit event
                    level = ExitEvent.exit(level);
                }

                // FIXME: add other events

                // Update the GUI char "pixels" as a string
                guiText = Gui.updateGuiString(level.getMaze());

                if (guiText != null) {
                    textArea.setText(guiText);
                }
                pickStuff(e.getKeyCode(),false);
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
    public static void initialiseMovementObjects() {

        level = new Level(1); // FIXME: load from file instead of creating a stubbed level when load is implemented
    }

    /**
     * Rong Sun
     */

    public static void initialisePickerObjects() {
        inventory = new Inventory(5);
        itemPicker  = new ItemPicker();
        moneyPicker = new MoneyPicker();

    }

    /**
     * Rong Sun
     * @param keyCode the last key that was pressed
     */
    public static void pickStuff(int keyCode, boolean textMode){

        // Check if the "P" key is pressed
        if (keyCode == KeyEvent.VK_E) {
            int size = inventory.getItems().size();
            // Trigger the ItemPicker operation
            // You might call your ItemPicker method here
            Pair<Player, Inventory> playerInventoryPair = itemPicker.interactWithAdjacent(inventory, level.getMaze());

            // Update the GUI text
            String newItemText = "You picked up an item!";
            if(playerInventoryPair.second().getItems().size()>size && !textMode){
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

        // FIXME: Handle other events as needed

        // Update the GUI char "pixels" as a string
        // You can keep this part if it's relevant to your game
        //String guiText = movementEvents.setGuiTextOnMovementKeysPressed(keyCode, movement, maze, gui);
        String guiText = Gui.updateGuiString(level.getMaze());
        if (guiText != null && !textMode) {
            textArea.setText(guiText);
        }

    }
}
