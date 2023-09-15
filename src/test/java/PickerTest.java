import org.example.belonging.Inventory;
import org.example.belonging.Item;
import org.example.belonging.Weapon;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Maze;
import org.example.interaction.ItemPicker;
import org.example.interaction.MoneyPicker;
import org.example.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;


public class PickerTest {
    private HashMap<String, Item> items = new HashMap<>();

    private ItemPicker itemPicker = new ItemPicker();
    private MoneyPicker moneyPicker = new MoneyPicker();
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
     * @author RS
     * Make sure it works as expected
     */
    @Test
    public void testPickInventory() {
        maze.addItem(new Position(1, 1), new Weapon("newItem",100,100,155));
        Pair<Player, Inventory> playerInventoryPair = itemPicker.interactWithAdjacent(inventory, maze);
        Item newItem = playerInventoryPair.second().getItems().get("newItem");
        if (newItem instanceof Weapon) {
            Weapon weapon = (Weapon) newItem;
            Assertions.assertEquals(155, weapon.getAttackValue());
        }
        // Check correct values
        Assertions.assertEquals("newItem", newItem.getName());
        Assertions.assertEquals(100, newItem.getPrice());
        Assertions.assertEquals(100, newItem.getWeight());
        Assertions.assertEquals(false, maze.getItems().containsKey(new Position(1,1)));

    }

    /**
     * Test case to check if money picker works as expected when picking up money.
     */
    @Test
    public void testPickMoney() {
        // Ensure there's money at the player's current position
        Assertions.assertTrue(maze.getMoney().containsKey(new Position(1, 1)));

        // Interact with adjacent money
        Player updatedPlayer = moneyPicker.interactWithAdjacent(maze);

        // Check that the player's money has been updated correctly
        Assertions.assertEquals(100, updatedPlayer.getMoney());

        // Ensure that the money has been removed from the maze
        Assertions.assertFalse(maze.getMoney().containsKey(new Position(1, 1)));
    }

    /**
     * Test case to check if the item picker handles a scenario where the player's inventory is already full.
     */
    @Test
    public void testPickInventoryFull() {
        // Fill the player's inventory to capacity
        for (Item item : items.values()) {
            inventory.addItem(item);
        }
        inventory.addItem(new Weapon("E",1,1,1));

        // Add a new item to the maze
        maze.addItem(new Position(1, 1), new Weapon("newItem", 100, 100, 155));

        // Interact with adjacent item
        Pair<Player, Inventory> playerInventoryPair = itemPicker.interactWithAdjacent(inventory, maze);

        // Check that the player's inventory remains full
        Assertions.assertEquals(5, playerInventoryPair.second().listItems().size());

        // Ensure that the item has not been added to the player's inventory
        Assertions.assertNull(playerInventoryPair.second().getItems().get("newItem"));
    }

    /**
     * Test case to check if the item picker handles a scenario where the player already has the same item.
     */
    @Test
    public void testPickDuplicateItem() {
        // Add the same item to the player's inventory
        Item duplicateItem = new Weapon("A", 24, 12, 14);
        inventory.addItem(duplicateItem);

        // Add a new item with the same name to the maze
        maze.addItem(new Position(1, 1), new Weapon("A", 100, 100, 155));

        // Interact with adjacent item
        Pair<Player, Inventory> playerInventoryPair = itemPicker.interactWithAdjacent(inventory, maze);

        // Check that the player's inventory remains unchanged
        Assertions.assertEquals(1, playerInventoryPair.second().listItems().size());

        // Ensure that the duplicate item has not been added to the player's inventory
        Assertions.assertEquals(24,playerInventoryPair.second().getItems().get("A").getPrice());
    }





    @Test
    public void pickMoney(){
        Player player = maze.getPlayer();
        moneyPicker.interactWithAdjacent(maze);

        Assertions.assertEquals(100, maze.getPlayer().getMoney());
        // Assertions.assertEquals(100, inventory.getMoney());
        Assertions.assertEquals(false, maze.getMoney().containsKey(new Position(1,1)));


    }




}
