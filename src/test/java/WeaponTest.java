import org.example.belonging.Weapon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Yucheng Zhu
 * Test Weapon
 */
public class WeaponTest {

    /**
     * @author Yucheng Zhu
     * Test set and get weapon attributes
     */
    @Test
    public void testGetAndSetWeapon() {
        Weapon weapon = new Weapon("Sword", 10, 3);
        weapon.setName("Iron Sword");
        weapon.setPrice(15);
        weapon.setAttackValue(5);
        weapon.setWeight(2);

        // Check correct values
        Assertions.assertEquals("Iron Sword", weapon.getName());
        Assertions.assertEquals(15, weapon.getPrice());
        Assertions.assertEquals(5, weapon.getAttackValue());
        Assertions.assertEquals(2, weapon.getWeight());
    }
}
