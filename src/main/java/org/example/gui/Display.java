package org.example.gui;
import org.example.IO.JsonLoad;
import org.example.IO.JsonSave;
import org.example.belonging.Item;
import org.example.belonging.Weapon;
import org.example.entity.Enemy;
import org.example.gameLogic.Approach;
import org.example.interaction.EnemyFighter;
import org.example.belonging.Inventory;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.interaction.ItemPicker;
import org.example.interaction.NpcTalker;
import org.example.util.Pair;
import org.example.gameLogic.Level;
import org.example.gameLogic.Maze;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import javax.swing.JTextPane;

import static org.example.gui.MovementEvents.isInKeySet;
import static org.example.gui.MovementEvents.setMovementKeys;

/**
 * @author Austin Zerk
 * @author Yucheng Zhu
 * @author Rong Sun
 * @author Yue Zhu
 * GUI to visualise the game.
 * Also defines the keys triggering the events
 */
public class Display extends JFrame {
    private JTextPane textArea;
    //private JTextArea inventoryTextArea;
    private Level level;
    private ItemPicker itemPicker;
    //private MoneyPicker moneyPicker;
    private Inventory inventory;
    private JLabel additionalLabel;
    private JsonSave saver = new JsonSave();

    // WSAD keys, used to move
    private HashSet<Integer> movementKeys = setMovementKeys();

    /**
     * @author Austin Zerk
     * @author Yucheng Zhu
     * @author Rong Sun
     * GUI to visualise the game
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
       // this.add(scrollPane);

        // Add the scroll pane with textArea to the panel
        panel.add(scrollPane, BorderLayout.CENTER);
        // Add the additional label to the panel
        panel.add(additionalLabel, BorderLayout.NORTH);
        this.add(panel);

        // initialise the movement objects
        initialiseMovementObjects();
        // initialise the picker objects
        initialisePickerObjects();
        textArea.setText(Gui.updateGuiString(level.getMaze()));
        additionalLabel.setText(displayInventory(inventory).toString());

        // If user press "ctrl+p" game will save
        saveGame();
        // Call the press 1-5 key, and choose the item
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
                    //interacting with exit
                    level = ExitEvent.exit(level);

                    //interacting with enemy
                    EnemyFighter enemyFighter = new EnemyFighter();

                    Pair<Player,Inventory> combatResults = enemyFighter.interactWithAdjacent(inventory, level.getMaze());
                    if (combatResults.first() == null){ //check if the player is at the respawn position
                        dialogueText = "You are dead";
                    }

                    // interacting with NPC
                    try {
                        dialogueText = NpcTalker.interactWithAdjacent(inventory, level, dialogueText);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                // interacting with items on map
                // Call pick stuff function and put the picked stuff in the inventory system
                pickStuff(e);
                additionalLabel.setText(displayInventory(inventory).toString());

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
     * TODO: replace this method from objects in the Maze when it finishes.
     */
    public void initialiseMovementObjects() {

       // FIXME: load from file instead of creating a stubbed level when load is implemented
        JsonLoad loader = new JsonLoad();

        File directory = new File("src/cache/progress/current");

        if(directory.exists() && directory.isDirectory()) {
            // if exists
            String[] files = directory.list();

            if(files != null && files.length > 0) {
                // if it has file
                try {
                    level = loader.loadCurLevelData();
                }catch (Exception e){
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
    }

    /**
     * @author Rong Sun
     * Initialise the pick helper instance and inventory system
     */
    public void initialisePickerObjects() {
        JsonLoad loader = new JsonLoad();

        this.inventory = loader.loadInventory();
        this.itemPicker  = new ItemPicker();

    }

    /**
     * @author Rong Sun
     * Initialise the pick helper instance and inventory system
     * @param e the key event that allow key E pressed and activity catched
     */
    public void pickStuff(KeyEvent e){

        int keyCode = e.getKeyCode();

        // Check if the "E" key is pressed
        if (keyCode == KeyEvent.VK_E) {
            int size = inventory.getItems().size();
            // Trigger the ItemPicker operation
            // You might call your ItemPicker method here
            Pair<Player, Inventory> playerInventoryPair = itemPicker.interactWithAdjacent(inventory, level.getMaze());
        }

        saver.saveInventory(inventory);

        String guiText = Gui.updateGuiString(level.getMaze());
        textArea.setText(guiText);

    }

    /**
     * @author Rong Sun
     * adds a listener to the text area that updates the selected weapon of the player
     * press 1-5 to choose the weapon 1-5 displayed in the inventory system
     */
    public void chooseStuff(){

        textArea.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                String text;
                int keyCode = e.getKeyCode();

                // get the number of the key
                int chosenWeaponNo = -1; // default is -1 (no use)
                if (keyCode >= KeyEvent.VK_1 && keyCode <= KeyEvent.VK_5) {
                    chosenWeaponNo = keyCode - KeyEvent.VK_1; // 1-5 mapping to 0-4
                }

                // if pressed the key 1-5
                if (chosenWeaponNo >= 0 && chosenWeaponNo < inventory.listItems().size()) {
                    Item selectedItem = inventory.listItems().get(chosenWeaponNo);
                    if (selectedItem instanceof Weapon)
                        level.getMaze().getPlayer().setCurrentWeapon((Weapon) selectedItem);
                }

                // update the GUI
                text = Gui.updateGuiString(level.getMaze());
                additionalLabel.setText(displayInventory(inventory).toString());
            }
        });
    }


    /**
     * @author Rong Sun
     * adds the inventory to the gui display board.
     * press 1-5 to choose the weapon 1-5 displayed in the inventory system
     */
    public StringBuilder displayInventory(Inventory inventory) {
        int itemNumber = 1;
        StringBuilder builder = new StringBuilder("<html>Inventory:<br>");
        //iterate the item, add them by different lines in the display board with the html label
        for (Item item : inventory.listItems()) {
            //make sure item is the instance of weapon
            if (item instanceof Weapon weapon) {
                if (level.getMaze().getPlayer().getCurrentWeapon() == weapon)
                    builder.append("-- ");
                builder.append(itemNumber + ". " + weapon.getName() + "(Price:" + weapon.getPrice() + " Weight:" + weapon.getWeight() + " Attack:" + weapon.getAttackValue() + ")");
                if (level.getMaze().getPlayer().getCurrentWeapon() == weapon)
                    builder.append(" --");

                builder.append("<br>");
            } else {
                //make sure next weapon is in different lines
                builder.append(itemNumber + ". " + item.getName() + "(Price:" + item.getPrice() + " Weight: " + item.getWeight() + ")<br>");
            }
            // label the different item to different numbers
            itemNumber++;
        }

        builder.append("</html>");
        return builder;
    }


    /**
     * @author Xin Chen
     * save game when player press ctrl+p
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

}
