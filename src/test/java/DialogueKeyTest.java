import org.example.entity.Position;
import org.example.entity.dialogue.DialogueKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DialogueKeyTest {
    DialogueKey dialogueKey;

    @BeforeEach
    public void testCreateDialogueText() {
        dialogueKey = new DialogueKey("Alice", 1, new Position(2,3));
    }

    @Test
    public void testToString() {
        Assertions.assertEquals("Alice,1,(2,3)", dialogueKey.toString());
    }
}
