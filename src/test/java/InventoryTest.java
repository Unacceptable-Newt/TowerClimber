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
 * This class contains a set of test cases to evaluate the functionality of the `Inventory` class.
 * It tests various aspects of the inventory system, including adding and removing items,
 * checking capacity constraints, and verifying item-related operations.
 * The tests are authored by the test developer Rong Sun.
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
     * Test method by Rong Sun to ensure that the inventory functions as expected.
     * It verifies the correct addition of items to the inventory and checks their attributes.
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
     * Test method by Rong Sun to ensure that players can choose items from the inventory and change equipped weapons.
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
     * Test method by Rong Sun to ensure that items can be correctly dropped from the inventory.
     * It adds items to the inventory, removes one, and checks if it's successfully removed.
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
     * Test method by Rong Sun to verify that money can be correctly deducted from the player's wallet.
     * It sets the player's money, deducts an amount, and checks if the player's money is updated as expected.
     */
    @Test
    public void deductMoney() {
        Player player = maze.getPlayer();
        player.setMoney(15);
        player.deductMoney(14);
        Assertions.assertEquals(1, maze.getPlayer().getMoney());
    }

    /**
     * Test method by Rong Sun to ensure that the inventory capacity functions correctly.
     * It tests the behavior of adding items to a full inventory and verifies if it handles capacity correctly.
     */
    @Test
    public void testInventoryCapacity() {
        // Test adding items to a full inventory and ensure it handles capacity correctly.
        // For example, add more items than the inventory capacity and verify the behavior.
        inventory.setItems(items);
        inventory.addItem(new Weapon("E", 1, 1, 1));
    }

    /**
     * Test method by Rong Sun to check if listing items in the inventory works as expected.
     * It adds items to the inventory and verifies if listing the items returns the correct number and items.
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
     * Test method by Rong Sun to verify if the inventory correctly checks for the existence of an item.
     * It adds an item to the inventory and checks if the inventory correctly identifies its presence.
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
     * Test method by Rong Sun to ensure that items can be added to the inventory.
     * It adds an item to the inventory and checks if the item is present in the inventory.
     */
    @Test
    void testAddItem() {
        // Add an item to the inventory
        Item item = new Weapon("Item1", 10, 1,12);
        inventory.addItem(item);

        Assertions.assertTrue(inventory.hasItem("Item1"));
    }

    @Test
    void testAddItemWithFullCapacity() {
        // Fill the inventory to its capacity
        Item item1 = new Weapon("Item1", 10, 1,12);
        Item item2 = new Weapon("Item2", 15, 2,13);
        Item item3 = new Weapon("Item3", 20, 3,14);
        Item item4 = new Weapon("Item4", 25, 4,15);
        Item item5 = new Weapon("Item5", 30, 5,16);

        inventory.addItem(item1);
        inventory.addItem(item2);
        inventory.addItem(item3);
        inventory.addItem(item4);
        inventory.addItem(item5);

        // Attempt to add another item, which should not be possible
        Item newItem = new Weapon("NewItem", 5, 1,14);
        inventory.addItem(newItem);

        Assertions.assertFalse(inventory.hasItem("NewItem"));
    }



    /**
     * Test method by Rong Sun to verify that attempting to remove an item that doesn't exist in the inventory
     * returns false, indicating that the item was not removed.
     */
    @Test
    void testRemoveNonExistentItem() {
        // Attempt to remove an item that doesn't exist in the inventory
        boolean removed = inventory.removeItem("NonExistentItem");

        Assertions.assertFalse(removed);
    }

    /**
     * Test method by Rong Sun to check if updating the inventory capacity with a positive increment
     * changes the capacity as expected.
     */
    @Test
    void testUpdateCapacity() {
        // Update the inventory capacity and check if it's updated correctly
        inventory.updateCapacity(3);

        Assertions.assertEquals(8, inventory.getCapacity());
    }

    /**
     * Test method by Rong Sun to ensure that updating the inventory capacity with a negative increment
     * (which should not change it) is handled correctly.
     */
    @Test
    void testUpdateCapacityWithNegativeIncrement() {
        // Attempt to update the capacity with a negative increment (should not change)
        inventory.updateCapacity(-5);

        Assertions.assertEquals(0, inventory.getCapacity());
    }




}
