package org.example;

import org.example.entity.Life;
import org.example.entity.Position;
import org.example.gameLogic.Maze;
import org.example.interaction.Direction;

/**
 * @author
 * Changes how player's positions change
 */
public class Movement {

    /**
     * @author Yucheng Zhu
     * Changes if the entity can move to an adjacent cell in the given direction.
     *
     * @param currentPosition Entity's current position
     * @param direction Entity's current position
     * @param maze The maze where the entity finds him-/her-/it-self in
     * @return True if the entity can move into the cell, otherwise false
     */
    private boolean canMove(Position currentPosition, Direction direction, Maze maze) throws IllegalArgumentException {
        return true; // FIXME implement this after Maze is done
    }

    /**
     * @author Yucheng Zhu
     * Changes the entity's position after having moved to an adjacent cell in the given direction.
     * Return the current position if the entity cannot move.
     *
     * @param entityToMove Which creature to change position
     * @param direction Which direction to move
     * @param maze The maze where the entity finds him-/her-/it-self in
     * @return The entity with his/her/its new position after movement
     */
    public Life move(Life entityToMove, Direction direction, Maze maze) throws IllegalArgumentException {
        // Get the entity's current position and direction in the maze
        Position currentPosition = entityToMove.getPosition();

        // Change the player's direction
        entityToMove.setDirection(direction);

        // Return the current position with a changed direction if the entity cannot move.
        if (!canMove(currentPosition, direction, maze)) {
            return entityToMove;
        }

        // Change the player's position
        Position position;
        switch (direction) {
            case UP -> {
                position = new Position(currentPosition.getX(), currentPosition.getY() - 1);
            }
            case DOWN -> {
                position = new Position(currentPosition.getX(), currentPosition.getY() + 1);
            }
            case LEFT -> {
                position = new Position(currentPosition.getX() - 1, currentPosition.getY());
            }
            case RIGHT -> {
                position = new Position(currentPosition.getX() + 1, currentPosition.getY());
            }
            default -> throw new IllegalArgumentException("Direction must be up, down, left xor right");
        }

        // Move the entity's position
        entityToMove.setPosition(position);
        return entityToMove;
    }

    /**
     * @author Yucheng Zhu
     * Changes the entity's position after having moved up.
     * Return the current position if the entity cannot move.
     *
     * @param entityToMove Which creature to change position
     * @param maze The maze where the entity finds him-/her-/it-self in
     * @return The entity with his/her/its new position after movement
     */
    public Life up(Life entityToMove, Maze maze) throws IllegalArgumentException {
        return move(entityToMove, Direction.UP, maze);
    }

    /**
     * @author Yucheng Zhu
     * Changes the entity's position after having moved down.
     * Return the current position if the entity cannot move.
     *
     * @param entityToMove Which creature to change position
     * @param maze The maze where the entity finds him-/her-/it-self in
     * @return The entity with his/her/its new position after movement
     */
    public Life down(Life entityToMove, Maze maze) throws IllegalArgumentException {
        return move(entityToMove, Direction.DOWN, maze);
    }

    /**
     * @author Yucheng Zhu
     * Changes the entity's position after having moved left.
     * Return the current position if the entity cannot move.
     *
     * @param entityToMove Which creature to change position
     * @param maze The maze where the entity finds him-/her-/it-self in
     * @return The entity with his/her/its new position after movement
     */
    public Life left(Life entityToMove, Maze maze) throws IllegalArgumentException {
        return move(entityToMove, Direction.LEFT, maze);
    }

    /**
     * @author Yucheng Zhu
     * Changes the entity's position after having moved right.
     * Return the current position if the entity cannot move.
     *
     * @param entityToMove Which creature to change position
     * @param maze The maze where the entity finds him-/her-/it-self in
     * @return The entity with his/her/its new position after movement
     */
    public Life right(Life entityToMove, Maze maze) throws IllegalArgumentException {
        return move(entityToMove, Direction.RIGHT, maze);
    }

}
