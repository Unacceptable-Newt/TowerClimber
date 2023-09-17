package org.example.entity.dialogue;

public class DialogueKey {
    private final String entityInteracted;
    private final int mazeLevel;
    private final int id;

    /**
     * @author Yucheng Zhu
     * Create a dialogue object to uniquely identity each dialogue text.
     * Each text will be shown in one screen. Don't make it longer than the screen can display.
     * @param entityInteracted The entity being talked to or being interacted and triggers the text
     * @param mazeLevel The maze level the entity is found in
     * @param id A unique id for that entity at that level.
     *           It's OK to have the same number as a different entity
     *           or for the same entity at a different level
     */
    public DialogueKey(String entityInteracted, int mazeLevel, int id) {
        this.entityInteracted = entityInteracted;
        this.mazeLevel = mazeLevel;
        this.id = id;
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
                "," + id;
    }
}
