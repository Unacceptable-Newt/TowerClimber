package org.example.entity;

/**
 * The NPC class represents an NPC. The player can talk to him/her
 *  @author Rong Sun
 *
 */
public class NPC extends Life {
    private String name;
//    private List<String> dialogue;

    public NPC(String name, Position position) {
        super(100, 100, position, 100);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
