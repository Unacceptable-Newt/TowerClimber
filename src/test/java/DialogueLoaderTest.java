import org.example.entity.dialogue.DialogueLoader;
import org.example.entity.dialogue.DialogueKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class DialogueLoaderTest {

    @Test
    public void testToString() throws IOException {
        DialogueKey dialogueKey =
                new DialogueKey(
                        "King George's Chief Councillor",
                        1,
                        "Councillor on kidnapping"
                );

        Assertions.assertEquals(
                "King George's Chief Councillor: 'Tis the fifth time that the princess was kidnapped! Methinks a great mystery surrounds her. Oh mighty hero, thou art the last hope of our land and ... the 5689th person to accept his majesty's missions.",
                DialogueLoader.loadDialogue(dialogueKey)
        );
    }

}
