package org.example.entity.dialogue;

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

    public static String loadDialogue(DialogueKey dialogueKey, String relativePath) throws IOException {
        Path dialoguesTextsFilePath = Paths
                .get(relativePath)
                .toAbsolutePath();
        System.out.println(dialoguesTextsFilePath);
        String content = Files.readString(dialoguesTextsFilePath);
        JSONObject json = new JSONObject(content);
        return json.get(dialogueKey.toString()).toString();
    }

    /**
     * @author Yucheng Zhu
     * Load dialogue for the dialogue currently shown on the screen, used in NpcTalker.
     * Test uses a different path, so this method is not covered.
     */
    public static String loadDialogue(DialogueKey dialogueKey) throws IOException {
        return loadDialogue(dialogueKey, "comp-2120-assignment-3-workshop-10-group-a/src/data/dialogues.json");
    }
}
