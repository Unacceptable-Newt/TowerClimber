
import org.example.entity.Enemy;
import org.example.belonging.Weapon;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.interaction.EnemyFighter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test Respawn
 * @author Yue Zhu
 */
public class RespawnTest {
  Player player = new Player(1, 10, 1, null);

  //according to the definition, the default attack of a player is 1. No default weapon.
  Weapon testWeapon = new Weapon("test weapon", 1, 1, 10);
  Weapon nullWeapon = new Weapon(" null", 0, 0, 0);
  Enemy enemy = new Enemy("Daniel", 10, 1, 10, new Position(8,8));

    /**
     * The player will be defeated by the enemy without the weapon.
     * @author Yue Zhu
     */
    @Test
    public void defeatedByEnemy() {
        player.setCurrentWeapon(nullWeapon);
        boolean defeatOrNot = org.example.gameLogic.Battle.processFights(player, enemy);
        Assertions.assertFalse(defeatOrNot);
    }


    /**
     * if the player equips the test weapon, then he or she can defeat the enemy.
     * @author Yue Zhu
     */
    @Test
    public void defeatedEnemy() {
        player.setCurrentWeapon(testWeapon);
        boolean defeatOrNot = org.example.gameLogic.Battle.processFights(player, enemy);
        Assertions.assertTrue(defeatOrNot);
    }

    /**
     * test the data of a respawned player
     * @author Yue Zhu
     */
    @Test
    public void respawnOrNot() {
        player.setCurrentWeapon(nullWeapon);
        boolean defeatedOrNot = org.example.gameLogic.Battle.processFights(player, enemy);
        // the player will be defeated by enemy, the respwan function will be invoked.
        Position testRespawnePosition = new Position(1, 1);
        Player respawnedPlayer =  EnemyFighter.respawnFunction(testRespawnePosition);
        Assertions.assertEquals(1, respawnedPlayer.getLevel());
        Assertions.assertEquals(testRespawnePosition, respawnedPlayer.getPosition());
        Assertions.assertEquals(0, respawnedPlayer.getMoney());
        }
}
