package org.example.entity;

/**
 * @author Austin Zerk
 * Wall in the maze.
 * Wall can be vertical or horizontal.
 * If it's vertical, it starts from the top. Then we have its length along the vertical line.
 * Else if it's horizontal, it starts from the left. Then we have its length along the horizontal line.
 */
public class Wall {
    private int length;
    private Position start;
    private boolean down;

    public Wall(int length, Position start, boolean up){
        this.length = length;
        this.start = start;
        this.down = up;
    }


    /**
     * @author Austin Zerk
     * Get the wall's length
     * @return The wall's length
     */
    public int getLength() {
        return length;
    }

    /**
     * @author Austin Zerk
     * Get the wall's starting location
     * @return The wall's starting location
     */
    public Position getStart() {
        return start;
    }

    /**
     * @author Austin Zerk
     * Is the wall vertical? If not's it's horizontal
     * @return True if the wall is vertical. Otherwise, it's horizontal.
     */
    public boolean isUp() {
        return down;
    }
}
