import org.example.belonging.Weapon;
import org.example.entity.Enemy;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.gameLogic.Battle;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class BattleTest {
    
    /**
     * @author Yue Zhu
     * this test is aimed to test whether the function can work well or not.
     * @param player The player
     * @param enemy The enemy
     * @return True if the enemy dies. Otherwise, return false.
     */    

     // data preparation
    Position position = new Position(1, 1);
    Player player1 = new Player(0, 10, 1, position) ;
    Enemy enemy = new Enemy("null", 10, 10, 1, position);
    Weapon weapon = new Weapon("Test Weapon", 10, 1, 10);

    @Test
    public void withoutWeaponTest(){
        Assertions.assertFalse(Battle.processFights(player1, enemy));
    }

    @Test
    public void withWeaponTest(){
        player1.setCurrentWeapon(weapon);
        Assertions.assertTrue(Battle.processFights(player1, enemy));
    }
    }


