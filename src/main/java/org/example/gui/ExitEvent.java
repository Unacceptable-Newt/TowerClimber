package org.example.gui;

import org.example.entity.Position;
import org.example.gameLogic.Level;
import org.example.move.LevelStates;
import org.example.move.Movement;

/**
 * @author Yucheng Zhu
 * Handle the movement event.
 */
public class ExitEvent {
    public static Level exit(Level level) {
        Object frontalObject = Movement.getPlayerFrontalObject(
                level.getMaze()
        );
        if (frontalObject instanceof Position) {
            level.setLevelStates(LevelStates.EXIT);

            // start a new level in the next floor
            level = new Level(level.getLevel() + 1);
        }

        return level; // return the level of floor to go
    }
}
