package com.example.demo1.logic.items;

import com.example.demo1.logic.Cell;

public class Key extends Item {

    public Key(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "key";
    }


}
