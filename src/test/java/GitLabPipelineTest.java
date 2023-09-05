import org.example.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GitLabPipelineTest {
    /**
     * @author Yucheng Zhu
     * Test that the GitLab Pipeline passes correct tests.
     */
    @Test
    public void testGitLabPipelineWhenTestFails() {
        Main main = new Main();

        // This shall fail, because `2 != 1`
        assertEquals(2, main.testPipelineWorks());
    }
}
