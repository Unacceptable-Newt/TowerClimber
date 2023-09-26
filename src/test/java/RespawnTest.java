
import org.example.entity.Enemy;
import org.example.belonging.Weapon;
import org.example.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class RespawnTest {
  Player player = new Player(1, 10, 1, null); 
  //according to the definition, the defualt attack of a player is 1. No defualt weapon.

  Weapon testWeapon = new Weapon("test weapon", 1, 1, 10);

  Weapon nullWeapon = new Weapon(" null", 0, 0, 0);
  
  Enemy enemy = new Enemy(10, 1 ,10);

  

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

  

  
}
