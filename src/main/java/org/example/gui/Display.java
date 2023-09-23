package org.example.gui;
import org.example.belonging.Item;
import org.example.belonging.Weapon;
import org.example.gameLogic.Maze;
import org.example.interaction.EnemyFighter;
import org.example.belonging.Inventory;
import org.example.entity.Player;
import org.example.interaction.ItemPicker;
import org.example.interaction.MoneyPicker;
import org.example.util.Pair;
import org.example.gameLogic.Level;


import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JTextPane;

import static org.example.gui.MovementEvents.isInKeySet;
import static org.example.gui.MovementEvents.setMovementKeys;

/**
 * @author Austin Zerk, Yucheng Zhu
 * GUI to visualise the game
 */
public class Display extends JFrame {
    private JTextPane textArea;
    //private JTextArea inventoryTextArea;
    private Level level;
    private ItemPicker itemPicker;
    //private MoneyPicker moneyPicker;
    private Inventory inventory;
    private JLabel additionalLabel;

    // WSAD keys, used to move
    private HashSet<Integer> movementKeys = setMovementKeys();

    public Display(int width, int height) {
        // Set movement keys only once
        setMovementKeys();

        // Create GUI
        this.setTitle("The Secret of the Princess");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);

        JPanel panel = new JPanel(new BorderLayout());
        // Add text area
        textArea = new JTextPane();

        // Create and add a label for displaying the additional string
        additionalLabel = new JLabel("Inventory");
        additionalLabel.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));

        // Set the preferred size to control the width of the label
        Dimension labelSize = additionalLabel.getPreferredSize();
        labelSize.height = 200; // Set the desired width here
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

        // Listen to key events
        textArea.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                String guiText;
                int keyCode = e.getKeyCode();
                // Get the text after considering change brought by movement
                if (isInKeySet(movementKeys, keyCode)) { // Movement events
                    MovementEvents.setGuiTextOnMovementKeysPressed(keyCode, level.getMaze());
                } else if (keyCode == KeyEvent.VK_E) { // exit event
                    level = ExitEvent.exit(level);
                    //interacting with exit
                    EnemyFighter enemyFighter = new EnemyFighter();

                    //interacting with enemy
                    enemyFighter.interactWithAdjacent(inventory, level.getMaze());
                    if (level.getMaze().getPlayer().getHealth() <= 0){
                        //FIXME Player needs to die
                    }
                }

                // FIXME: add other events

                // Update the GUI char "pixels" as a string
                guiText = Gui.updateGuiString(level.getMaze());

                textArea.setText(guiText);
                pickStuff(e);
                additionalLabel.setText(displayInventory(inventory).toString());

            }
        });

        chooseStuff();

        // Make the GUI visible
        this.setVisible(true);
    }

    /**
     * @author Austin Zerk, Yucheng Zhu
     * Stubbing player's data to test movement.
     * TODO: replace this method from objects in the Maze when it finishes.
     */
    public void initialiseMovementObjects() {

        level = new Level(1); // FIXME: load from file instead of creating a stubbed level when load is implemented
    }

    /**
     * Rong Sun
     */

    public void initialisePickerObjects() {
        this.inventory = new Inventory(5);
        this.itemPicker  = new ItemPicker();
      //  this.moneyPicker = new MoneyPicker();

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

    /**
     * @author Rong Sun
     * adds a listener to the text area that updates the selected weapon of the player
     */
    public void chooseStuff(){

        textArea.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                String guiText;
                int keyCode = e.getKeyCode();

                // 获取按键对应的数字
                int selectedWeaponNumber = -1; // 默认为无效编号
                if (keyCode >= KeyEvent.VK_1 && keyCode <= KeyEvent.VK_5) {
                    selectedWeaponNumber = keyCode - KeyEvent.VK_1; // 1-5 映射到 0-4
                }

                // 如果按下了 1-5 键
                if (selectedWeaponNumber >= 0 && selectedWeaponNumber < inventory.listItems().size()) {
                    Item selectedItem = inventory.listItems().get(selectedWeaponNumber);
                    if (selectedItem instanceof Weapon)
                        level.getMaze().getPlayer().setCurrentWeapon((Weapon) selectedItem);

                    // 在这里处理选择的武器，可以显示在文本区域或进行其他操作
                    System.out.println("Selected Weapon: " + selectedItem.getName());
                }

                // 其他事件处理代码...

                // 更新 GUI
                guiText = Gui.updateGuiString(level.getMaze());
                textArea.setText(guiText);
                additionalLabel.setText(displayInventory(inventory).toString());
            }
        });
    }

    // add Inventory display
    public StringBuilder displayInventory(Inventory inventory) {

     /*   int itemNumber = 1;
        StringBuilder builder = new StringBuilder("Inventory:\n");
        for (Item item : inventory.listItems()) {
            if(item instanceof Weapon){
                Weapon weapon = (Weapon)item;
                builder.append(itemNumber + ". " + weapon.getName() + "(Price:" + weapon.getPrice() + " Weight:" + weapon.getWeight() +" Attack:" + weapon.getAttackValue()+")"+ "\n");
            }else{
                builder.append(itemNumber + ". " + item.getName() + "(Price:" + item.getPrice() + " Weight: " + item.getWeight() +")"+ "\n");
            }
            itemNumber++;
        }
        System.out.println(builder);
        return builder;*/
        int itemNumber = 1;
        StringBuilder builder = new StringBuilder("<html>Inventory:<br>");
        for (Item item : inventory.listItems()) {
            if (item instanceof Weapon weapon) {
                if (level.getMaze().getPlayer().getCurrentWeapon() == weapon)
                    builder.append("-- ");
                builder.append(itemNumber + ". " + weapon.getName() + "(Price:" + weapon.getPrice() + " Weight:" + weapon.getWeight() + " Attack:" + weapon.getAttackValue() + ")");
                if (level.getMaze().getPlayer().getCurrentWeapon() == weapon)
                    builder.append(" --");
                builder.append("<br>");
            } else {
                builder.append(itemNumber + ". " + item.getName() + "(Price:" + item.getPrice() + " Weight: " + item.getWeight() + ")<br>");
            }
            itemNumber++;
        }
        builder.append("</html>");
        return builder;
    }




}
