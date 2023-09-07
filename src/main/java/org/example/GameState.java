package org.example;

/**
 * @author
 * Save the game to the storage to be played later;
 * and load the saved game to resume the previously saved game.
 */
public class GameState {

    private String parseBelongingToJson() {
        // FIXME
        return null;
    }

    private String parseEntitiesToJson() {
        // FIXME
        return null;
    }

    /**
     * @author
     * Save all the persistent data in the game to the storage
     * as a JSON file.
     * This method saves a game so that it can be replayed later.
     * @param filePath Path to the JSON file to be saved
     */
    public void save(String filePath) {
        // FIXME
    }

    private String loadJson(String filePath) {
        // FIXME
        return null;
    }

    private Object[] parseJsonToBelonging(String Json) {
        // FIXME
        return null;
    }

    private Object[] parseJsonToEntities(String Json) {
        // FIXME
        return null;
    }

    /**
     * @author
     * Load the saved JSON file, and convert them to game variables and objects.
     * This method loads a game to play from where the player previously left off.
     *
     * @param filePath Path to the saved JSON file
     * @return All the persistent data in the game. Suffice to resume a game.
     */
    public Object[] load(String filePath) {
        // FIXME
        return null;
    }

}
