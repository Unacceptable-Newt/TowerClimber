import org.example.belonging.Inventory;
import org.example.belonging.Item;
import org.example.belonging.Weapon;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Maze;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
/**
 * To evaluate the functionality of the `Inventory` class, including adding and removing items,
 * also the capacity constraints, and item-related operations.
 * @author Rong Sun.
 */
public class InventoryTest {

    private HashMap<String, Item> items = new HashMap<>();


    private Maze maze = new Maze(33,33,new Position(2,2));
    private Inventory inventory = new Inventory(5);

    @BeforeEach
    void addItem(){
        items.put("A", new Weapon("A", 24, 12, 14));
        items.put("B", new Weapon("B", 15, 18,15));
        items.put("C", new Weapon("C", 20, 100,100));
        items.put("D", new Weapon("D", 21, 1,20));
    }
    @BeforeEach
    void addPlayer(){
        maze.createNewPlayer(new Position(1,1));
    }

    @BeforeEach
    void setMoneyPosition(){
        maze.addMoney(new Position(1,1),100);

    }

    /**
     * @Author Rong Sun
     * test to put weapon in the in the inventory
     */
    @Test
    void testInventory() {

        // Initialize the inventory with items
        inventory.setItems(items);
        Item newItem = inventory.getItems().get("A");
        if (newItem instanceof Weapon) {
            Weapon weapon = (Weapon) newItem;
            Assertions.assertEquals(14, weapon.getAttackValue());
        }
        // Check correct values
        Assertions.assertEquals("A", newItem.getName());
        Assertions.assertEquals(24, newItem.getPrice());
        Assertions.assertEquals(12, newItem.getWeight());

    }

    /**
     * ensure that players can choose items
     * make sure they can change current weapons.
     * It checks if the player can correctly select and switch between inventory items.
     */
    @Test
    public void testChooseInventory() {
        // Initialize the inventory with items
        inventory.addItem(items.get("B"));
        inventory.addItem(items.get("A"));

        Player player = maze.getPlayer();
        player.setCurrentWeapon((Weapon) items.get("A"));

        player.changeItem("B",inventory);

        Assertions.assertEquals("B", maze.getPlayer().getCurrentWeapon().getName());
        Assertions.assertEquals(15, maze.getPlayer().getCurrentWeapon().getPrice());
        Assertions.assertEquals(18, maze.getPlayer().getCurrentWeapon().getWeight());
        Assertions.assertEquals(15, maze.getPlayer().getCurrentWeapon().getAttackValue());
    }

    /**
     * Ensure that items can be choosed and dropped by users
     * @Author Rong Sun
     */
    @Test
    public void dropInventory() {
        // Add an item to the inventory
        inventory.addItem(items.get("C"));
        inventory.addItem(items.get("D"));

        // Remove the item and check if it's removed
        Boolean c = inventory.removeItem("C");
        HashMap<String, Item> newInventory = inventory.getItems();

        Assertions.assertTrue(c);
        Assertions.assertTrue(inventory.hasItem("D"));
        Assertions.assertFalse(inventory.hasItem("C"));

    }

    /**
     * Ensure that money can be  dropped by users
     * @Author Rong Sun
     */
    @Test
    public void dropMoney() {
        Player player = maze.getPlayer();
        player.setMoney(15);
        player.deductMoney(14);
        Assertions.assertEquals(1, maze.getPlayer().getMoney());
    }


    /**
     * Check if listing items in the inventory works as expected.
     */
    @Test
    void testListItems() {
        // Add some items to the inventory
        Item item1 = new Weapon("Item1", 10, 1, 15);
        Item item2 = new Weapon("Item2", 15, 2, 20);

        inventory.addItem(item1);
        inventory.addItem(item2);

        List<Item> items = inventory.listItems();
        Assertions.assertEquals(2, items.size());
        Assertions.assertTrue(items.contains(item1));
        Assertions.assertTrue(items.contains(item2));
    }

    /**
     * Verify if the inventory  checks for the existence of an item.
     */
    @Test
    void testHasItem() {
        // Add an item to the inventory
        Item item = new Weapon("Item1", 10, 1, 12);
        inventory.addItem(item);

        Assertions.assertTrue(inventory.hasItem("Item1"));
        Assertions.assertFalse(inventory.hasItem("Item2"));
    }

    /**
     * test single items can be added to the inventory.
     */
    @Test
    void testAddItem() {
        // Add an item to the inventory
        Item item = new Weapon("Item1", 10, 1,12);
        inventory.addItem(item);

        Assertions.assertTrue(inventory.hasItem("Item1"));
    }

    /**
     * test the capacity by full
     */
    @Test
    void testAddItemWithFullCapacity() {
        // Fill the inventory to its capacity
        Item item1 = new Weapon("Item1", 9, 1,12);
        Item item2 = new Weapon("Item2", 15, 2,13);
        Item item3 = new Weapon("Item3", 20, 3,14);
        Item item4 = new Weapon("Item4", 26, 4,15);
        Item item5 = new Weapon("Item5", 32, 5,16);

        inventory.addItem(item1);
        inventory.addItem(item2);
        inventory.addItem(item3);
        inventory.addItem(item4);
        inventory.addItem(item5);

        //add the not allowed 6th item
        Item newItem = new Weapon("NewItem", 5, 1,14);
        inventory.addItem(newItem);

        Assertions.assertFalse(inventory.hasItem("NewItem"));
    }



    /**
     * Remove an item that doesn't exist in the inventory
     */
    @Test
    void testRemoveNonExistentItem() {
        // Attempt to remove an item that doesn't exist in the inventory
        boolean removed = inventory.removeItem("XXX");

        Assertions.assertFalse(removed);
    }

    /**
     *  check if updating the inventory capacity with a positive increment
     */
    @Test
    void testUpdateCapacity() {

        inventory.updateCapacity(3);

        Assertions.assertEquals(8, inventory.getCapacity());
    }

    /**
     *  updating the inventory capacity with a negative increment
     * (which should not change it) is handled correctly.
     */
    @Test
    void testUpdateCapacityWithNegativeIncrement() {
        // update with neagtive number
        inventory.updateCapacity(-5);

        Assertions.assertEquals(0, inventory.getCapacity());
    }

    @Test
    void setCapacity1() {
        // update with neagtive number
        inventory.setCapacity(6);

        Assertions.assertEquals(6, inventory.getCapacity());
    }




}
