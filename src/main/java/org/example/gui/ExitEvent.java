package org.example.gui;

import org.example.IO.JsonLoad;
import org.example.IO.JsonSave;
import org.example.belonging.Inventory;
import org.example.entity.Position;
import org.example.gameLogic.Level;
import org.example.move.LevelStates;
import org.example.move.Movement;

/**
 * @author Yucheng Zhu
 * @author Austin Zerk
 * @author Rong Sun
 * Handle the movement event.
 */
public class ExitEvent {


    public static Level exit(Level level) {
        Object frontalObject = Movement.getPlayerFrontalObject(
                level.getMaze()
        );
        if (frontalObject instanceof Position) {
            level.setLevelStates(LevelStates.EXIT);
            JsonLoad loader = new JsonLoad();

            // start a new level in the next floor
            level = loader.loadNextLevel(level.getLevel());
        }

        return level; // return the level of floor to go
    }
}
