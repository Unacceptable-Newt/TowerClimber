package org.example.entity.dialogue;

/**
 * @author Yucheng Zhu
 * Used to uniquely identify each dialogue
 */
public class DialogueKey {
    private final String entityInteracted;
    private final int mazeLevel;
    private int count; // dialogue count, allowing each NPC to speak more than once

    /**
     * @author Yucheng Zhu
     * Create a dialogue object to uniquely identity each dialogue text.
     * Each text will be shown in one screen. Don't make it longer than the screen can display.
     * @param entityInteracted The entity being talked to or being interacted and triggers the text
     * @param mazeLevel The maze level the entity is found in
     * @param count The dialogue count from the current NPC, allowing each NPC to speak more than once.
     *                 This uniquely identifies each dialogue.
     *              Different NPCs or the same NPC at a different floor can have the same number
     */
    public DialogueKey(String entityInteracted, int mazeLevel, int count) {
        this.entityInteracted = entityInteracted;
        this.mazeLevel = mazeLevel;
        this.count = count;
    }

    /**
     * @author Yucheng Zhu
     * Reset the dialogue count to 1. Allowing an NPC to repeat his lines.
     */
    public void resetCount() {
        this.count = 1;
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
                count;
    }
}
