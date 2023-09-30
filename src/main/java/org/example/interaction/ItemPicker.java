package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.belonging.Item;
import org.example.belonging.Weapon;
import org.example.entity.Player;
import org.example.gameLogic.Level;
import org.example.gameLogic.Maze;
import org.example.gui.Display;
import org.example.move.Movement;
import org.example.util.Pair;

import java.awt.event.KeyEvent;

/**
 * @author Rong Sun
 * Initialises picking up an adjacent item
 */
public class ItemPicker implements Interaction {


    /**
     * @author Rong Sun
     * Interacts with an adjacent item to pick it up and changes the player and his inventory in the process.
     * @param inventory The player's inventory.
     * @param maze The maze.
     * @return New updated player and inventory
     */
    @Override
    public Pair<Player, Inventory> interactWithAdjacent(Inventory inventory, Maze maze) {
        //create the instance of movement
        Movement movement = new Movement();
        //get the front object
        Object frontalObject = movement.getPlayerFrontalObject(maze);
        //if the object is an istance of item, and if there is item in that position
        if(frontalObject instanceof Item){
            maze.pickItemAtPosition(movement.getFrontalPosition(maze.getPlayer().getDirection()
                    ,maze.getPlayer().getPosition()));


                // add the item into the inventory
                inventory.addItem((Item) frontalObject);

        }


        // return the new player and inventory
        return new Pair<>(maze.getPlayer(), inventory);
    }

    /**
     * @author Rong Sun
     * adds the inventory to the gui display board.
     * press 1-5 to choose the weapon 1-5 displayed in the inventory system
     */
    public static StringBuilder displayInventory(Inventory inventory, boolean textMode, Level level) {
        int itemNumber = 1;
        StringBuilder builder = textMode ? new StringBuilder("Inventory:\n") : new StringBuilder("<html>Inventory:<br>");
        //iterate the item, add them by different lines in the display board with the html label
        for (Item item : inventory.listItems()) {
            //make sure item is the instance of weapon
            if (item instanceof Weapon weapon) {
                if (level.getMaze().getPlayer().getCurrentWeapon() == weapon)
                    builder.append("-- ");
                builder.append(itemNumber + ". " + weapon.getName() + "(Price:" + weapon.getPrice() + " Weight:" + weapon.getWeight() + " Attack:" + weapon.getAttackValue() + ")");
                if (level.getMaze().getPlayer().getCurrentWeapon() == weapon)
                    builder.append(" --");

                if (textMode) builder.append("\n"); else builder.append("<br>");
            } else {
                //make sure next weapon is in different lines
                builder.append(itemNumber + ". " + item.getName() + "(Price:" + item.getPrice() + " Weight: " + item.getWeight());
                if (textMode) builder.append("\n"); else builder.append(")<br>");
            }
            // label the different item to different numbers
            itemNumber++;
        }

        if (!textMode) builder.append("</html>");
        return builder;
    }

    /**
     * adds a listener to the text area that updates the selected weapon of the player
     * press 1-5 to choose the weapon 1-5 displayed in the inventory system
     */
    public static void selectItemFromKeyCode(int keyCode, boolean textMode, Inventory inventory, Level level){
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
        //if (textMode) inventoryString = displayInventory(inventory).toString();
        //else additionalLabel.setText(displayInventory(inventory).toString());
    }




}
