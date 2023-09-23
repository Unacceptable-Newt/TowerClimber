import org.example.entity.NPC;
import org.example.entity.Position;
import org.example.entity.dialogue.DialogueLoader;
import org.example.entity.dialogue.DialogueKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author Yucheng Zhu
 * Tests DialogueLoader
 */
public class DialogueLoaderTest {

    /**
     * @author Yucheng Zhu
     * Tests DialogueLoader
     * Test uses a different path than NpcTalker, so the one-parameter overloaded method is not covered.
     */
    @Test
    public void testToString() throws IOException {
        NPC npc = new NPC("King George's Chief Councillor", new Position(1, 2));

        DialogueKey dialogueKey =
                new DialogueKey(
                        "King George's Chief Councillor",
                        1,
                        1
                );

        Assertions.assertEquals(
                "King George's Chief Councillor: 'Tis the fifth time that the princess was kidnapped! Methinks a great mystery surrounds her. Oh mighty hero, thou art the last hope of our land and ... the 5689th person to accept his majesty's missions.",
                DialogueLoader.loadDialogue(npc, dialogueKey)
        );
    }

}
