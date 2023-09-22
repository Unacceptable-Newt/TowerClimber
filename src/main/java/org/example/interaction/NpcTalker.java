package org.example.interaction;

import org.example.belonging.Inventory;
import org.example.entity.NPC;
import org.example.entity.Player;
import org.example.entity.Position;
import org.example.entity.dialogue.DialogueKey;
import org.example.entity.dialogue.DialogueLoader;
import org.example.gameLogic.Maze;
import org.example.util.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.example.move.Movement.getFrontalPosition;

/**
 * @author
 * Initialises a talk with an adjacent NPC
 */
public class NpcTalker {
    /**
     * @author Yucheng Zhu
     *
     * Interacts with an adjacent NPC to talk to him/her and changes the player and his inventory in the process.
     * @param inventory The player's inventory.
     * @param maze The maze.
     *
     * @return Modified player and inventory
     */
    public String interactWithAdjacent(
          Inventory inventory, Maze maze, String oldTextToDisplay
    ) throws IOException {
        String textToDisplay = null;
        Position frontalPosition = getFrontalPosition(maze.getPlayer().getDirection(), maze.getPlayer().getPosition());

        NPC npc = maze.getNPCAtPosition(frontalPosition);

        if (npc == null ) { // no NPC at this position
            return oldTextToDisplay; // display text from a different entity type
        } else {
            // load dialogue
            DialogueKey dialogueKey =
                    new DialogueKey(
                            "King George's Chief Councillor",
                            1,
                            new Position(4,5)
                    );
            System.out.println(dialogueKey);
            Path dialoguesTextsFilePath = Paths.get("C:\\Users\\admin\\Desktop\\Year 1 Semester 2\\Software Engineering\\Assignments\\Assignment 3\\src\\data\\dialogues.json");
            System.out.println(dialoguesTextsFilePath);

            System.out.println(DialogueLoader.loadDialogue(dialogueKey));
            return "abc";//DialogueLoader.loadDialogue(dialogueKey);
        }
    }
}
