package org.example.belonging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Rong Sun
 * The Inventory class states
 * the max number of items and
 * details the precise items objects
 */
public class Inventory {
    // Store items
    private HashMap<String, Item> items = new HashMap<>();

    // Max number of weight a player can carry
    private int capacity = 5;


    public Inventory(int capacity) {
        this.capacity = capacity;
    }

    /**
     * @author Rong Sun
     * List all items in the inventory
     * @return All items in the inventory
     */
    public List<Item> listItems() {
        // FIXME
        return new ArrayList<>(items.values());
    }

    /**
     * check whether an item contains in the inventory
     * @param item item want to be checked
     * @return true if the inventory has that item
     */
    public boolean hasItem(Item item){
        if(items.containsValue(item)){
            return true;
        }
        return false;
    }

    /**
     * @author Rong Sun
     * Add an item to the inventory
     * @param itemToAdd The item to add to the inventory
     */
    public void addItem(Item itemToAdd) {
        if(items.size()>=capacity){
            return;
        }else if (items.containsKey(itemToAdd.getName())) {
            return;
        }
        this.items.put(itemToAdd.getName(), itemToAdd);
    }

    /**
     * @author Rong Sun
     * Remove an item from the inventory
     * @param itemToRemove The item to remove from the inventory
     * @return The removed item. Can be used in another task such as calculate the sell price in shopping.
     */
    public Item removeItem(String itemToRemove) {
        // FIXME
        if (!items.containsKey(itemToRemove)) {
            return null; // Project does not exist. Return null
        }

        return items.remove(itemToRemove);
    }


    /**
     * @author Yucheng Zhu
     * Get the capacity of the inventory.
     * Used to calculate whether an inventory reaches the max capacity.
     */
    public int getCapacity() {
        return capacity;
    }


    /**
     * @author Yucheng Zhu
     * Add the capacity of the inventory to the capacity increment.
     * Capacity shall never fall below 0!
     * Used to calculate whether an inventory reaches the max capacity.
     */
    public void updateCapacity(int capacityIncrement) {
        this.capacity = Math.max(0, this.capacity + capacityIncrement);
    }

    public HashMap<String, Item> getItems() {
        return items;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setItems(HashMap<String, Item> items) {
        this.items = items;
    }
}
