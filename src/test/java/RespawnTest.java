
import org.example.entity.Enemy;
import org.example.belonging.Weapon;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.interaction.EnemyFighter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class RespawnTest {
  Player player = new Player(1, 10, 1, null); 
  //according to the definition, the defualt attack of a player is 1. No defualt weapon.

  Weapon testWeapon = new Weapon("test weapon", 1, 1, 10);

  Weapon nullWeapon = new Weapon(" null", 0, 0, 0);
  
  Enemy enemy = new Enemy("Daniel", 10, 1, 10, new Position(8,8));

  

  // The player will be defeated by the enemy without the weapon.
 @Test
    public void defeatedByEnemy() {
      player.setCurrentWeapon(nullWeapon);
      boolean defeatOrNot = org.example.gameLogic.Battle.processFights(player,enemy);
      Assertions.assertEquals(false,defeatOrNot);
     }


  // if the player equips the test weapon, then he or she can defeat the enemy. 
 @Test
    public void defeatedEnemy() {
      player.setCurrentWeapon(testWeapon);
      boolean defeatOrNot = org.example.gameLogic.Battle.processFights(player,enemy);
      Assertions.assertEquals(true ,defeatOrNot);
     }
// test the data of a respawned player
@Test
    public void respwanOrNot() {
      player.setCurrentWeapon(nullWeapon); 
      boolean defeatedOrNot = org.example.gameLogic.Battle.processFights(player,enemy);
      // the player will be defeated by enemy, the respwan function will be invoked.
      Position testRespawnePosition = new Position(1, 1);
      Player respawnedPlayer =  EnemyFighter.respwanFunction(testRespawnePosition);
      Assertions.assertEquals(1, respawnedPlayer.getLevel());
      Assertions.assertEquals(testRespawnePosition, respawnedPlayer.getPosition());
      Assertions.assertEquals(0, respawnedPlayer.getMoney());
     }



  

  
}
