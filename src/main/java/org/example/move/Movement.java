package org.example.move;

import org.example.belonging.Item;
import org.example.entity.*;
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
        Position nextPosition = getPosition(direction, currentPosition);

        if ( // not outside the boundary
                nextPosition.getY() < 0 ||
                        nextPosition.getY() >= maze.getRows() ||
                        nextPosition.getX() < 0 ||
                        nextPosition.getX() >= maze.getColumns()
        ) {
            return false;
        } else if (maze.isWall(nextPosition)) { // not a wall
            return false;
        } else if (maze.getEnemies().containsKey(nextPosition)) { // not an enemy
            return false;
        } else if (maze.getNPCs().containsKey(nextPosition)) { // not an NPC
            return false;
        } else { // not an exit
            return !maze.getExit().equals(nextPosition);
        }
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
        Position position = getPosition(direction, currentPosition);

        // Move the entity's position
        entityToMove.setPosition(position);
        return entityToMove;
    }

    /**
     * @author Yucheng Zhu
     * Changes the entity's position after having moved to an adjacent cell in the given direction.
     * Return the current position if the entity cannot move.
     *
     * @param direction The direction the entity is facing
     * @param currentPosition The position the entity is standing at before movement
     * @return The entity's new position after movement
     */
    private Position getPosition(Direction direction, Position currentPosition) {
        Position position;
        switch (direction) {
            case UP ->
                position = new Position(currentPosition.getX(), currentPosition.getY() - 1);
            case DOWN ->
                position = new Position(currentPosition.getX(), currentPosition.getY() + 1);
            case LEFT ->
                position = new Position(currentPosition.getX() - 1, currentPosition.getY());
            case RIGHT ->
                position = new Position(currentPosition.getX() + 1, currentPosition.getY());
            default -> throw new IllegalArgumentException("Direction must be up, down, left xor right");
        }
        return position;
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

    /**
     * @author Yucheng Zhu
     * @param maze The maze where the player is in
     * @param position The position to get the object. The object is not removed.
     * @return Object at the position.  null if nothing is at the position.
     */
    public Object getObjectAtPosition(Maze maze, Position position) {
        // Get all possible stuff on the maze
        Item item = maze.getItemAtPosition(position);
        Enemy enemy = maze.getEnemyAtPosition(position);
        NPC npc = maze.getNPCAtPosition(position);
        Player player = maze.getPlayer();

        if (item != null) { // get item at the position
            return item;
        } else if (enemy != null) { // get enemy at the position
            return enemy;
        } else if (npc != null) { // get npc at the position
            return npc;
        } else if (maze.getExit().equals(position)) { // get exit at the position
            return maze.getExit();
        } else if (
                player != null &&
                        player.getPosition() != null &&
                        player.getPosition().equals(position)) { // get player at the position

            return player;
        }

        return null;
    }

    /**
     * @author Yucheng Zhu
     * @param maze The maze where the play is in
     * @param direction The direction the player is staring at.
     * @return Object at the position. null if nothing is at the position.
     */
    public Object getFrontalObject(Maze maze, Direction direction) {
        Position currentPosition = maze.getPlayer().getPosition();
        return getObjectAtPosition(maze, getPosition(direction, currentPosition));
    }
}
