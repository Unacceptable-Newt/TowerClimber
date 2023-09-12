import org.example.belonging.Inventory;
import org.example.belonging.Item;
import org.example.belonging.Weapon;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Maze;
import org.example.interaction.Direction;
import org.example.interaction.ItemPicker;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryTest {

    private List<Item> itemsName = new ArrayList<>();


    private HashMap<String, Item> items = new HashMap<>();

    private ItemPicker itemPicker = new ItemPicker();
    private Maze maze = new Maze(33,33,new Position(2,2));
    private Player mainPlayer = new Player(12,12,12,new Position(1,1));
    private Inventory inventory = new Inventory(5);

    @Before
    void addItem(){
        items.put("A", new Weapon("A", 24, 12));
        items.put("B", new Weapon("B", 15, 18));
        items.put("C", new Weapon("C", 20, 100));
        items.put("D", new Weapon("D", 21, 1));
    }

    /**
     * @author RS
     * Make sure it works as expected
     */
    @Test
    void testInventory() {

        items.put("A", new Weapon("A", 12,12));
        inventory.setItems(items);

        // Check correct values
        Assertions.assertEquals("A", inventory.getItems().get("A").getName());
        Assertions.assertEquals(12, inventory.getItems().get("A").getPrice());
        Assertions.assertEquals(12, inventory.getItems().get("A").getWeight());

    }

    /**
     * @author RS
     * Make sure it works as expected
     */
    @Test
    public void testPickInventory() {
        maze.addItem(new Position(1, 1), new Weapon("newItem",100,100));


        itemPicker.interactWithAdjacent(mainPlayer,inventory,maze);

        // Check correct values
        Assertions.assertEquals("newItem", inventory.getItems().get("newItem").getName());
        Assertions.assertEquals(100, inventory.getItems().get("newItem").getPrice());
        Assertions.assertEquals(100, inventory.getItems().get("newItem").getWeight());

    }

    /**
     * @author RS
     * Make sure it works as expected
     */
    @Test
    public void testChooseInventory() {

    }
}
