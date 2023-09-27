import org.example.entity.Enemy;
import org.example.entity.Player;
import org.example.entity.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ApproachTest {
/*
 * @AUTHOR Yue Zhu
 * @Intention: this test is designed to test if the game logic, approach, can function well or not
 * @parameters: player and enemy
 * 
 */

 //data preparation

 Position p1Position = new Position(0, 0);
 Position p2Position = new Position(5, 3);
 Position p3Position = new Position(4, 3);
 Player player1 = new Player(0, 100, 1, p1Position);
 Player player2 = new Player(0, 100, 1, p2Position);
 Player player3 = new Player(0, 100, 1, p3Position);
 Enemy enemy = new Enemy("null", 0, 0, 0);
 

 @Test
 public void testNearby1 (){
    Assertions.assertEquals(false, org.example.gameLogic.Approach.isNearby(player1,enemy));
 }
 @Test
 public void testNearby2 (){
    Assertions.assertEquals(true, org.example.gameLogic.Approach.isNearby(player2,enemy));
 }
 @Test
 public void testNearby3 (){  
    Assertions.assertEquals(true, org.example.gameLogic.Approach.isNearby(player3,enemy));
 }

}
