package org.example.entity;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The NPC class represents an NPC. The player can talk to him/her
 *  @author Rong Sun
 *
 */
public class NPC extends Life {
    private String name;
    private List<String> dialogue;

    public NPC(String name, Position position,List<String> dialogue) {
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

    public List<String> getDialogue() {
        return dialogue;
    }

    public void setDialogue(List<String> dialogue) {
        this.dialogue = dialogue;
    }

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


}
