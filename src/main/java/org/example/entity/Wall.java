package org.example.entity;

public class Wall {
    private int length;
    private Position start;
    private boolean up;

    public Wall(int length, Position start, boolean up){
        this.length = length;
        this.start = start;
        this.up = up;
    }

    public int getLength() {
        return length;
    }

    public Position getStart() {
        return start;
    }

    public boolean isUp() {
        return up;
    }
}
