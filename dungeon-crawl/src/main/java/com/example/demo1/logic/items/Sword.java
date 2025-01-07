package com.example.demo1.logic.items;

import com.example.demo1.logic.Cell;

public class Sword extends Item {

    public Sword(Cell cell) {
        super(cell);
        setAttack(7);
        setDefense(2);
    }

    @Override
    public String getTileName() {
        return "sword";
    }

}
