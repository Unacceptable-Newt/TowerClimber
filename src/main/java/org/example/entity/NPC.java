package org.example.entity;

/**
 * @author Jolene Sun, Austin Zerk, Yucheng Zhu
 * The NPC class represents an NPC. The player can talk to him/her
 * @author Rong Sun
 * @author Yucheng Zhu
 *
 */
public class NPC extends Life {
    private String name;
    private int dialogueCount = 1;

    public NPC(String name, Position position) {
        super(100, 100, position, 10, 10);
        this.name = name;
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
     * @author Yucheng Zhu
     * Add dialogue count by 1. Used to load the next dialogue
     */
    public void incrementDialogueCount() {
        this.dialogueCount++;
    }
}
