package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.entity.NPC;
import org.example.entity.Position;
import org.example.entity.dialogue.DialogueKey;
import org.example.entity.dialogue.DialogueLoader;
import org.example.gameLogic.Level;
import org.example.gameLogic.Maze;
import java.io.IOException;

import static org.example.move.Movement.getFrontalPosition;

/**
 * Initialises a talk with an adjacent NPC
 * @author Yucheng Zhu
 */
public class NpcTalker {
    /**
     * Interacts with an adjacent NPC to talk to him/her and changes the player and his inventory in the process.
     * @author Yucheng Zhu
     * @param inventory The player's inventory.
     * @param level The current floor of the dungeon.
     *
     * @return Dialogue text
     */
    public static String interactWithAdjacent(
            Inventory inventory, Level level, String oldTextToDisplay
    ) throws IOException {
        Maze maze = level.getMaze();

        // Get the NPC the player is talking to
        Position frontalPosition = getFrontalPosition(maze.getPlayer().getDirection(), maze.getPlayer().getPosition());
        NPC npc = maze.getNPCAtPosition(frontalPosition);

        if (npc == null ) { // no NPC at this position
            return oldTextToDisplay; // display text from a different entity type
        } else {
            // load dialogue
            DialogueKey dialogueKey =
                    new DialogueKey(
                            npc.getName(),
                            level.getLevel(),
                            npc.getDialogueCount()
                    );

            // increment the dialogue count by 1
            return DialogueLoader.loadDialogue(npc, dialogueKey);
        }
    }
}
