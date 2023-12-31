package org.example.gui;

import org.example.Game;
import org.example.IO.JsonLoad;
import org.example.IO.JsonSave;
import org.example.belonging.Inventory;
import org.example.belonging.Weapon;
import org.example.entity.Position;
import org.example.gameLogic.Level;
import org.example.move.LevelStates;
import org.example.move.Movement;

/**
 * Go to the next level.
 * @author Yucheng Zhu
 * @author Austin Zerk
 * @author Rong Sun
 * @author Xin Chen
 */
public class ExitEvent {

    /**
     * Go to the next level
     * @author Yucheng Zhu
     * @author Austin Zerk
     * @author Rong Sun
     * @author Xin Chen
     * @param level Current level
     * @return level Next level (current level + 1)
     */

    public static Level exit(Level level,Inventory inventory) {
        Object frontalObject = Movement.getPlayerFrontalObject(
                level.getMaze()
        );
        if (frontalObject instanceof Position) {
            // -- go to the next level
            level.setLevelStates(LevelStates.EXIT);

            // Load the next level from the JSON file
            JsonLoad loader = new JsonLoad();
            JsonSave saver = new JsonSave();
            saver.saveCurrentProgress(level);
            saver.saveInventory(inventory);

            // start a new level in the next floor
            Weapon playersWeapon = level.getMaze().getPlayer().getCurrentWeapon();
            level = loader.loadNextLevel(level.getLevel());
            level.getMaze().getPlayer().setCurrentWeapon(playersWeapon);
        }

        return level; // return the level of floor to go
    }
}
