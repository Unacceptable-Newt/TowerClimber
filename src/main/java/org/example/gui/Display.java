package org.example.gui;
import org.example.Game;
import org.example.IO.JsonLoad;
import org.example.IO.JsonSave;
import org.example.entity.Enemy;
import org.example.gameLogic.Approach;
import org.example.entity.Position;
import org.example.interaction.EnemyFighter;
import org.example.belonging.Inventory;
import org.example.entity.Player;
import org.example.interaction.ItemPicker;
import org.example.interaction.NpcTalker;
import org.example.move.Movement;
import org.example.util.Pair;
import org.example.gameLogic.Level;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import javax.swing.JTextPane;

import static org.example.gui.MovementEvents.isInKeySet;

/**
 * GUI to visualise the game.
 * Also defines the keys triggering the events
 * @author Austin Zerk
 * @author Yucheng Zhu
 * @author Rong Sun
 * @author Yue Zhu
 */
public class Display extends JFrame {
    private static JTextPane textArea;
    private static Level level;
    private static ItemPicker itemPicker;
    private static Inventory inventory;

    private HashSet<Integer> movementKeys;
    private static JLabel additionalLabel;
    private static String inventoryString = "";
    private static String displayMaze = "";
    private static String dialog = "";
    private static JsonSave saver = new JsonSave();

    //interacting with enemy
    private static EnemyFighter enemyFighter = new EnemyFighter();

    public boolean isMovementKeys(int key) {
        return movementKeys.contains(key);
    }

    /**
     * GUI to visualise the game
     * @author Austin Zerk
     * @author Yucheng Zhu
     * @author Rong Sun
     */
    private void setMovementKeys() {
        this.movementKeys = new HashSet<>();
        this.movementKeys.add(KeyEvent.VK_W);
        this.movementKeys.add(KeyEvent.VK_S);
        this.movementKeys.add(KeyEvent.VK_A);
        this.movementKeys.add(KeyEvent.VK_D);
    }

    public static void TextDisplay(){
        // initialise the movement objects
        initialiseMovementObjects(true);

        // initialise the picker objects
        initialisePickerObjects();

        displayMaze = Gui.updateGuiString(level.getMaze());
        System.out.println(displayMaze);

        InputStreamReader sin = new InputStreamReader(System.in);
        char ch;
        while (true){
            try {
                ch = (char) sin.read();
                dialog = "";
                if (ch == '\n') continue;
                if (ch == 'q') break;
                // move the player if movement keys are pressed passing the keycode for jPane compatibility
                else if (ch == 'w') MovementEvents.setGuiTextOnMovementKeysPressed(87, level.getMaze());
                else if (ch == 'a') MovementEvents.setGuiTextOnMovementKeysPressed(65, level.getMaze());
                else if (ch == 's') MovementEvents.setGuiTextOnMovementKeysPressed(83, level.getMaze());
                else if (ch == 'd') MovementEvents.setGuiTextOnMovementKeysPressed(68, level.getMaze());

                // interaction events
                else if (ch == 'e') {
                    Object frontalObject = Movement.getPlayerFrontalObject(level.getMaze());

                    if (frontalObject == null) {
                        dialog = "Nothing to interact with.\nTip: you must stand beside a letter and face it to interact with it";
                    }
                    // check if player is facing exit;
                    if (frontalObject instanceof Position && level.getLevel() == Game.MAX_LEVEL) {
                        dialog = "Congratulations, you've won the game!";
                        // resets game once final exit is reached
                        saver.emptyCurFolder();
                    }else {
                        level = ExitEvent.exit(level);
                    }
                    // check if player is facing item
                    pickStuff(69);
                    // check if player is facing NPC
                    try {
                        dialog = NpcTalker.interactWithAdjacent(inventory, level, dialog);
                    } catch (IOException e){
                        System.err.println("failed to find dialog from adjacent NPC");
                        System.err.println(e.getMessage());
                    }
                    enemyFighter.interactWithAdjacent(inventory,level.getMaze());
                }
                // keys for selecting items
                else if (ch == '1') ItemPicker.selectItemFromKeyCode(49,true,inventory,level);
                else if (ch == '2') ItemPicker.selectItemFromKeyCode(50,true,inventory,level);
                else if (ch == '3') ItemPicker.selectItemFromKeyCode(51,true,inventory,level);
                else if (ch == '4') ItemPicker.selectItemFromKeyCode(52,true,inventory,level);
                else if (ch == '5') ItemPicker.selectItemFromKeyCode(53,true,inventory,level);

                // saves if p is pressed
                else if (ch == 'p') {saver.saveCurrentProgress(level); saver.saveInventory(inventory);}

                // display enemy info if close to enemy
                if (Approach.isNearby(level.getMaze().getPlayer(), level.getMaze().getEnemies()) != null) {
                    Enemy selectedEnemy = Approach.isNearby(
                            level.getMaze().getPlayer(),
                            level.getMaze().getEnemies()
                    );
                    dialog = selectedEnemy.enemyStatistics(selectedEnemy);
                }

                // displays death message if player dies
                Pair<Player,Inventory> combatResults = enemyFighter.interactWithAdjacent(inventory, level.getMaze());
                if (combatResults.first() == null){ //check if the player is at the respawn position
                    dialog = "You are dead";
                }
                // update the maze string
                displayMaze = Gui.updateGuiString(level.getMaze());
                // update the inventory string
                inventoryString = ItemPicker.displayInventory(inventory,true,level).toString();

                // print the display
                System.out.println(inventoryString);
                System.out.println(displayMaze);
                System.out.println(dialog);

            // if getting the input fails inform the user but do not stop the program
            } catch (IOException e){
                System.err.println("Failed to get character from input");
            }
        }
    }

    /**
     * Main GUI method which displays the game on screen
     * @param width GUI window width
     * @param height GUI window height
     * @author Yucheng Zhu
     * @author Austin Zerk
     * @author Rong Sun
     * @author Yue Zhu
     */
    public Display(int width, int height) {
        // Set movement keys only once
        setMovementKeys();

        // Create GUI
        this.setTitle("The Secret of the Princess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setResizable(false);

        JPanel panel = new JPanel(new BorderLayout());
        // Add text area
        textArea = new JTextPane();

        // Create and add a label for displaying the additional string
        additionalLabel = new JLabel("Inventory");
        additionalLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));

        // Set the preferred size to control the width of the label
        Dimension labelSize = additionalLabel.getPreferredSize();
        labelSize.height = 100; // Set the desired width here
        additionalLabel.setPreferredSize(labelSize);

        // revalidate and repaint
        additionalLabel.revalidate();
        additionalLabel.repaint();

        // Make all characters evenly sized
        SimpleAttributeSet spacingSet = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(spacingSet, -0.25f);
        textArea.setParagraphAttributes(spacingSet, false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        // Add the scroll pane with textArea to the panel
        panel.add(scrollPane, BorderLayout.CENTER);
        // Add the additional label to the panel
        panel.add(additionalLabel, BorderLayout.NORTH);
        this.add(panel);

        // initialise the movement objects
        initialiseMovementObjects(true);
        // initialise the picker objects
        initialisePickerObjects();
        textArea.setText(Gui.updateGuiString(level.getMaze()));
        additionalLabel.setText(ItemPicker.displayInventory(inventory, false,level).toString());

        // If user press "ctrl+p" game will save
        //initialise user input for saving
        saveGame();

        loadNewGame();

        // Call the press 1-5 key, and choose the item
        // initialises user input for picking up objects
        chooseStuff();

        // Listen to key events
        textArea.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                String dialogueText = ""; // text displayed in the dialogue box
                String guiText;
                int keyCode = e.getKeyCode();
                // Get the text after considering change brought by movement
                if (isInKeySet(movementKeys, keyCode)) { // Movement events
                    // Movement
                    MovementEvents.setGuiTextOnMovementKeysPressed(keyCode, level.getMaze());

                    // Check if enemy is adjacent
                    if (Approach.isNearby(level.getMaze().getPlayer(), level.getMaze().getEnemies()) != null) {
                          Enemy selectedEnemy = Approach.isNearby(
                            level.getMaze().getPlayer(),
                            level.getMaze().getEnemies()
                        );
                          dialogueText  =  selectedEnemy.enemyStatistics(selectedEnemy);
                    }

                } else if (keyCode == KeyEvent.VK_E) { // exit event
                    Object frontalObject = Movement.getPlayerFrontalObject(level.getMaze());
                    // No object to interact with
                    if (frontalObject == null) {
                        dialogueText = "Nothing to interact with.\nTip: you must stand beside a letter and face it to interact with it";
                    }

                    //interacting with exit
                    if (frontalObject instanceof Position && level.getLevel() == Game.MAX_LEVEL) {
                        dialogueText = "Congratulations, you've won the game!";
                        //resets game once final exit is reached
                        saver.emptyCurFolder();
                    } else {
                        level = ExitEvent.exit(level);
                    }

                    //interacting with enemy
                    EnemyFighter enemyFighter = new EnemyFighter();

                    Pair<Player,Inventory> combatResults = enemyFighter.interactWithAdjacent(inventory, level.getMaze());
                    if (combatResults.first() == null) { // check if the player is at the respawn position
                        dialogueText = "You are dead";
                    }
                    enemyFighter.interactWithAdjacent(inventory, level.getMaze());

                    // interacting with NPC
                    try {
                        dialogueText = NpcTalker.interactWithAdjacent(inventory, level, dialogueText);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                // interacting with items on map
                // Call pick stuff function and put the picked stuff in the inventory system
                pickStuff(e.getKeyCode());

                //updates the inventory display
                additionalLabel.setText(ItemPicker.displayInventory(inventory,false,level).toString());

                // Update the GUI char "pixels" as a string
                guiText = Gui.updateGuiString(level.getMaze(), dialogueText);
                textArea.setText(guiText);
                // DON'T CHANGE OR SET `guiText` BELOW
            }
        });

        // Make the GUI visible
        this.setVisible(true);
    }

    /**
     * @author Austin Zerk
     * @author Yucheng Zhu
     * @author Xin Chen
     * Stubbing player's data to test movement.
     */
    public static void initialiseMovementObjects(boolean newGame) {

       // Load from file instead with the implemented load
        JsonLoad loader = new JsonLoad();

        File directory = new File("src/cache/progress/current");

        // false to restart, true to continue
        if(newGame) {

            if (directory.exists() && directory.isDirectory()) {
                // if exists
                String[] files = directory.list();

                if (files != null && files.length > 0) {
                    // if it has file
                    try {
                        level = loader.loadCurLevelData();
                    } catch (Exception e) {
                        level = loader.loadStartMap();
                    }
                } else {
                    // if it is empty
                    level = loader.loadStartMap();
                }
            } else {
                // if folder is not there, create new one and start new
                directory.mkdirs();
                level = loader.loadStartMap();
            }
        }else {

            level = loader.loadStartMap();
        }
    }

    /**
     * Initialise the pick helper instance and inventory system
     * @author Xin Chen
     * @author Rong Sun
     * @author Austin Zerk
     */
    public static void initialisePickerObjects() {
        JsonLoad loader = new JsonLoad();
        inventory = loader.loadInventory();
        itemPicker  = new ItemPicker();

    }

    /**
     * @author Rong Sun
     * Initialise the pick helper instance and inventory system
     * @param keyCode the key code that allow key E pressed and activity catched
     */
    public static void pickStuff(int keyCode){

        // Check if the "E" key is pressed
        if (keyCode == KeyEvent.VK_E) {
            int size = inventory.getItems().size();
            // Trigger the ItemPicker operation
            // You might call your ItemPicker method here
            Pair<Player, Inventory> playerInventoryPair = itemPicker.interactWithAdjacent(inventory, level.getMaze());
        }

        saver.saveInventory(inventory);
    }

    /**
     * Adds a listener to the text area that updates the selected weapon of the player
     * Press 1-5 to choose the weapon 1-5 displayed in the inventory system
     * @author Rong Sun
     */
    public void chooseStuff(){

        textArea.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                String text;
                int keyCode = e.getKeyCode();

                ItemPicker.selectItemFromKeyCode(keyCode, false, inventory,level);
                additionalLabel.setText(ItemPicker.displayInventory(inventory, false,level).toString());
            }
        });
    }

    /**
     * Save game when the player presses Ctrl + P
     * @author Xin Chen
     */
    private void saveGame(){
        textArea.addKeyListener(new KeyAdapter() {
            private boolean isSaving = false;

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_P && !isSaving) {
                    isSaving = true;

                    saver.saveCurrentProgress(level);
                    saver.saveInventory(inventory);
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    isSaving = false;
                }
            }
        });
    }

    /**
     * to restart the whole game
     *
     * @author Xin Chen
     */
    private void loadNewGame(){
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N) {
                    JsonSave saver = new JsonSave();
                    saver.emptyCurFolder();
                    inventory = new Inventory(5);
                    saver.saveInventory(inventory);
                    initialiseMovementObjects(false);
                    e.consume();
                }
            }
        });

    }
}
