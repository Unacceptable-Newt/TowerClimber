import org.example.entity.dialogue.DialogueKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DialogueKeyTest {
    DialogueKey dialogueKey;

    @BeforeEach
    public void testCreateDialogueText() {
        dialogueKey = new DialogueKey("Alice", 1, 5);
    }

    @Test
    public void testToString() {
        Assertions.assertEquals("Alice,1,5", dialogueKey.toString());
    }
}
