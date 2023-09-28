import org.example.entity.dialogue.DialogueKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Yucheng Zhu
 * Test DialogueKey
 */
public class DialogueKeyTest {
    DialogueKey dialogueKey;

    @BeforeEach
    public void testCreateDialogueText() {
        dialogueKey = new DialogueKey("Alice", 1, 2);
    }

    @Test
    public void testToString() {
        Assertions.assertEquals("Alice,1,2", dialogueKey.toString());
    }

    /**
     * @author Yucheng Zhu
     * Test reset dialogue key to 1
     */
    @Test
    public void testResetCount() {
        dialogueKey.resetCount();
        Assertions.assertEquals("Alice,1,1", dialogueKey.toString());
    }
}
