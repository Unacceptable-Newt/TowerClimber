package org.example.entity.dialogue;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Load dialogue for the dialogue currently shown on the screen
 */
public class DialogueLoader {


    public static String loadDialogue(DialogueKey dialogueKey) throws IOException {
        Path dialoguesTextsFilePath = Paths
                .get("src/data/dialogues.json")
                .toAbsolutePath();
        System.out.println(dialoguesTextsFilePath);
        String content = Files.readString(dialoguesTextsFilePath);
        JSONObject json = new JSONObject(content);
        return json.get(dialogueKey.toString()).toString();
    }
}
