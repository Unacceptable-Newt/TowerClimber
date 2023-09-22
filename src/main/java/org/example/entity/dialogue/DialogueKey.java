package org.example.entity.dialogue;

import org.example.entity.Position;

public class DialogueKey {
    private final String entityInteracted;
    private final int mazeLevel;
    private final Position position;

    /**
     * @author Yucheng Zhu
     * Create a dialogue object to uniquely identity each dialogue text.
     * Each text will be shown in one screen. Don't make it longer than the screen can display.
     * @param entityInteracted The entity being talked to or being interacted and triggers the text
     * @param mazeLevel The maze level the entity is found in
     * @param position The position where the entity being talked to is at.
     *                 This uniquely identifies each dialogue.
     */
    public DialogueKey(String entityInteracted, int mazeLevel, Position position) {
        this.entityInteracted = entityInteracted;
        this.mazeLevel = mazeLevel;
        this.position = position;
    }

    /**
     * @author Yucheng Zhu
     * Generate a unique dialogue identifier, used for the JSON file, "dialogues.json"'s, key.
     * @return A json key
     */
    @Override
    public String toString() {
        return entityInteracted +
                "," + mazeLevel +
                "," +
                "(" + position.getX() + "," + position.getY() + ")";
    }
}
