package org.example.entity;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Jolene Sun, Austin Zerk, Yucheng Zhu
 * The NPC class represents an NPC. The player can talk to him/her
 *  @author Rong Sun
 *
 */
public class NPC extends Life {
    private String name;
    private int dialogueCount = 1;

    public NPC(String name, Position position,List<String> dialogue) {
        super(100,100,position, 100);
        this.name = name;
        this.dialogue = dialogue;
    }
    public NPC(String name, Position position) {
        super(100,100,position, 100);
        this.name = name;
        this.dialogue = dialogue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDialogueCount() {
        return dialogueCount;
    }

    /**
     * @author Yucheng Zhu
     * Reset the dialogue count to 1. Allowing an NPC to repeat his lines.
     */
    public void resetDialogueCount() {
        this.dialogueCount = 1;
    }

    /**
     * It can load all the dialogue from the json file
     * @param filePath the json file's path
     */
    public void loadDialogueFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dialogue.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Yucheng Zhu
     * Add dialogue count by 1. Used to load the next dialogue
     */
    public void incrementDialogueCount() {
        this.dialogueCount++;
    }
}
