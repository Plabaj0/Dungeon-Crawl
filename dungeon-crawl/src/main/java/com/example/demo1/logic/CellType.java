package com.example.demo1.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    OPEN_DOOR("openDoor"),
    CLOSED_DOOR("closedDoor"),
    FOREST("forest1");



    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
