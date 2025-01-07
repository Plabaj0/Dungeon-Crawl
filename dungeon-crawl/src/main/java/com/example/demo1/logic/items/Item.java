package com.example.demo1.logic.items;

import com.example.demo1.logic.Cell;
import com.example.demo1.logic.Drawable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Item implements Drawable {
    private Cell cell;

    private int health;
    private int defense;
    private int attack;

    public Item(Cell cell) {
        this.cell = cell;
        this.cell.setItem(this);
    }


}