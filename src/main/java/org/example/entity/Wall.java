package org.example.entity;

public class Wall {
    private int length;
    private Position start;
    private boolean down;

    public Wall(int length, Position start, boolean up){
        this.length = length;
        this.start = start;
        this.down = up;
    }

    public int getLength() {
        return length;
    }

    public Position getStart() {
        return start;
    }

    public boolean isUp() {
        return down;
    }
}
