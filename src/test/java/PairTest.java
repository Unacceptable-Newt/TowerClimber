import org.example.util.Pair;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;

import java.util.Objects;

/**
 * @author Yucheng Zhu
 * Test the `Pair` class
 */
public class PairTest {
    // Content of the pair
    String str;
    int i;

    // First pair to test
    private Pair<String, Integer> pair;
    // Same Pair to test
    private Pair<String, Integer> pairSame;

    /**
     * @author Yucheng Zhu
     * Create variables before each test
     */
    @BeforeEach
    public void init() {
        str = "hi";
        i = 1;
        pair = new Pair<>(str, i);
        pairSame = new Pair<>(str, i);

    }

    /**
     * @author Yucheng Zhu
     * Test getting the pair's first and second item
     */
    @Test
    public void testPairItems() {
        assertEquals("hi", pair.first());
        assertEquals(1, pair.second());
    }

    /**
     * @author Yucheng Zhu
     * Test if two pairs are equal
     */
    @Test
    public void testPairEquals() {
        // Test self equals self
        assertEquals(pair, pair);

        // Test null does not equal self
        assertNotEquals(null, pair);

        // Test different pair objects with the same content
        assertEquals(pair, pairSame);
    }


}
