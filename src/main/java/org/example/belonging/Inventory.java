package org.example.belonging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Rong Sun
 * represent the Inventory System
 * has the max number of items and
 * details the precise items objects
 */
public class Inventory {
    // Store items by their name
    private HashMap<String, Item> items = new HashMap<>();

    // Max number of weight a player can carry
    private int capacity = 5;



    /**
     * @author Rong Sun
     * Constructs a new inventory with the capacity.
     * @param capacity The maximum number of items
     */
    public Inventory(int capacity) {

        this.capacity = capacity;
        // this.money = 0;
    }

    /**
     * @author Rong Sun
     * show all items in the inventory
     * @return All items in the inventory
     */
    public List<Item> listItems() {
        // FIXME
        return new ArrayList<>(items.values());
    }

    /**
     * @author Rong Sun
     * check whether an item is in the inventory
     * @param item item's name want to be checked
     * @return true if the inventory has that item
     */
    public boolean hasItem(String item){
        if(items.containsKey(item)){
            return true;
        }
        return false;
    }

    /**
     * @author Rong Sun
     * Add an item to the inventory system
     * @param itemToAdd The item to add to the inventory
     */
    public void addItem(Item itemToAdd) {
        if(items.size()>=capacity){
            return;
        }
        this.items.put(itemToAdd.getName(), itemToAdd);
    }






    /**
     * @author Rong Sun
     * Remove an item
     * @param itemToRemove The name of the item to remove
     * @return The removed item.
     * Can be used in another task
     */
    public Boolean removeItem(String itemToRemove) {
        // FIXME
        if (!items.containsKey(itemToRemove)) {
            return false; // Project does not exist. Return false
        }
        items.remove(itemToRemove);

        return true;
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
