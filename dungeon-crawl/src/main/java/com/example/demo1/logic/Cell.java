package com.example.demo1.logic;

import com.example.demo1.logic.actors.Actor;
import com.example.demo1.logic.items.Item;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cell implements Drawable {
    private CellType type;
    private Actor actor;
    private Item item;
    private GameMap gameMap;
    private int x, y;

    Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;
    }


    public Cell getNeighbor(int dx, int dy) {
        return gameMap.getCell(x + dx, y + dy);
    }

    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public void resetCell() {
            actor = null;
            item = null;
        }


}
