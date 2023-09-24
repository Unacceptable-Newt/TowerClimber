package org.example.entity.dialogue;

import org.example.entity.NPC;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Yucheng Zhu
 * Load dialogue for the dialogue currently shown on the screen
 */
public class DialogueLoader {

    public static String loadDialogue(NPC npc, DialogueKey dialogueKey) throws IOException {
        Path dialoguesTextsFilePath = Paths
                .get("src/data/dialogues.json")
                .toAbsolutePath();
        String content = Files.readString(dialoguesTextsFilePath);
        JSONObject json = new JSONObject(content);

        // Reset the dialogue count to 1. Allowing an NPC to repeat his lines.
        if (!json.has(dialogueKey.toString())) {
            npc.resetDialogueCount();
            dialogueKey.resetCount();
        }
        String dialogueText = json.get(dialogueKey.toString()).toString();
        npc.incrementDialogueCount(); // Prepare for the next line. Will be spoken in the next interaction
        return dialogueText;
    }
}
