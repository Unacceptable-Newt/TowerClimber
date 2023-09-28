import org.example.entity.NPC;
import org.example.entity.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author Yucheng Zhu
 * Test NPC
 */
public class NPCTest {
    NPC npc;

    @BeforeEach
    public void init() {
        npc = new NPC("John", new Position(2, 3));
    }

    /**
     * @author Yucheng Zhu
     * Test set an NPC's name
     */
    @Test
    public void testNpcSetName() throws IOException {
        npc.setName("William");
        Assertions.assertEquals("William", npc.getName());
    }

    /**
     * @author Yucheng Zhu
     * Test reset an NPC's dialogue's count, so that s/he/it loops from the first dialogue
     */
    @Test
    public void testResetDialogueCount() {
        // Initially it's always 1
        Assertions.assertEquals(1, npc.getDialogueCount());

        // increment his dialogue count
        npc.incrementDialogueCount();
        Assertions.assertEquals(2, npc.getDialogueCount());

        // After reset, it backs to 1 again
        npc.resetDialogueCount();
        Assertions.assertEquals(1, npc.getDialogueCount());
    }

    /**
     * @author Yucheng Zhu
     * Test set an NPC's max health. Inherited from Life
     */
    @Test
    public void testSetMaxHealth() {
        npc.setMaxHealth(200);
        Assertions.assertEquals(200, npc.getMaxHealth());
    }

    /**
     * @author Yucheng Zhu
     * Test restore an NPC to full health. Inherited from Life
     */
    @Test
    public void testRestoreFullHealth() {
        // Increased max health, but health remains the same
        npc.setMaxHealth(200);
        Assertions.assertEquals(100, npc.getHealth());

        // Restores health to max health
        npc.restoreFullHealth();
        Assertions.assertEquals(200, npc.getHealth());
    }

    /**
     * @author Yucheng Zhu
     * Set an NPC's attack value. Inherited from Life
     */
    @Test
    public void testSetAttack() {
        npc.setAttack(100);
        Assertions.assertEquals(100, npc.getAttack());
    }

}
