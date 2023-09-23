import org.example.belonging.Inventory;
import org.example.belonging.Item;
import org.example.belonging.Weapon;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Maze;
import org.example.interaction.ItemPicker;
import org.example.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

/**
 * test the pick item from the maze
 */
public class PickerTest {
    private HashMap<String, Item> items = new HashMap<>();

    private ItemPicker itemPicker = new ItemPicker();
   // private MoneyPicker moneyPicker = new MoneyPicker();
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
        maze.createNewPlayer(new Position(1,2));
    }

    @BeforeEach
    void setMoneyPosition(){
        maze.addMoney(new Position(1,1),100);
    }

    /**
     * @author Rong Sun
     * see if picked item exist in the player's inventory system
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
     * if player's inventory is already full.
     * test if cannot pick the item successfully
     */
    @Test
    public void testPickFull() {
        // Fill the player's inventory to capacity
        for (Item item : items.values()) {
            inventory.addItem(item);
        }
        inventory.addItem(new Weapon("Eric",1,1,1));

        maze.addItem(new Position(1, 1), new Weapon("newItem", 1, 1, 19));

        Pair<Player, Inventory> playerInventoryPair = itemPicker.interactWithAdjacent(inventory, maze);
        Assertions.assertEquals(5, playerInventoryPair.second().listItems().size());
        Assertions.assertNull(playerInventoryPair.second().getItems().get("newItem"));
    }

    /**
     * if player already has the same item.
     * cannot pick the duplicate item up
     */
    @Test
    public void testPickDuplicate() {
        // Add the same item to the player's inventory
        Item duplicateItem = new Weapon("A", 24, 12, 14);
        inventory.addItem(duplicateItem);

        maze.addItem(new Position(1, 1), new Weapon("A", 1, 1, 1));
        Pair<Player, Inventory> playerInventoryPair = itemPicker.interactWithAdjacent(inventory, maze);
        Assertions.assertEquals(1, playerInventoryPair.second().listItems().size());
        Assertions.assertEquals(24,playerInventoryPair.second().getItems().get("A").getPrice());
    }




}
