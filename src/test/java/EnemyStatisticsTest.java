import org.example.entity.Enemy;
import org.example.entity.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/*
     * @author Yue Zhu
     * this test is aimed to test whether the function, enemyStatistics, can display the enemy statistics well .
     * @param enemy The enemy
     * @return True if the output matches with the expected string.
     */


public class EnemyStatisticsTest {
  
    Position position = new Position(5, 4);
    Enemy enemy1 = new Enemy("Tom", 2, 2, 2, position);
    Enemy enemy2 = new Enemy("James", 1, 1, 1, position);
    String expectedString1 = " Tom ATT: 2 DEF: 2 HP: 2.";
    boolean areEqual1 = expectedString1.equals(enemy1.enemyStatistics(enemy1));
    String expectedString2 = " James ATT: 1 DEF: 1 HP: 1.";
    boolean areEqual2 = expectedString2.equals(enemy2.enemyStatistics(enemy2));
    
    @Test
    public void areEqual1(){
        Assertions.assertTrue(areEqual1);
    }

    @Test
    public void areEqual2(){
        Assertions.assertTrue(areEqual2);
    }



}
