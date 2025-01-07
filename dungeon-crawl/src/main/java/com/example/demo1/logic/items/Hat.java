package com.example.demo1.logic.items;

import com.example.demo1.logic.Cell;

public class Hat extends Item {


    public Hat(Cell cell) {
        super(cell);
        setDefense(5);
        setHealth(10);
    }

    @Override
    public String getTileName() {
        return "hat";
    }
}
