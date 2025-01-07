package com.example.demo1.logic.actors;

import com.example.demo1.logic.Cell;
import com.example.demo1.logic.CellType;
import com.example.demo1.logic.Drawable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Actor implements Drawable {
    private Cell cell;

    private int health;
    private int defense;
    private int attack;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        Actor actor = getCell().getActor();
        if (actor != null && actor.getTileName().equals("ghost")) {
            moveActor(nextCell);
        } else {
            if (nextCell.getType() != CellType.WALL && nextCell.getType() != CellType.CLOSED_DOOR) {
                moveActor(nextCell);
            }
        }
    }

    private void moveActor(Cell nextCell) {
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }
}

