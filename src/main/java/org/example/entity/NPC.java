package org.example.entity;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The NPC class represent user's charactor, and he/she will have money, health, and player level
 * if player was attacked, player's health will decrease
 * .....
 *  @author Rong Sun
 *
 */
public class NPC {
    private String name;
    private Position position;
    private List<String> dialogue;

    public NPC(String name, Position position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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
