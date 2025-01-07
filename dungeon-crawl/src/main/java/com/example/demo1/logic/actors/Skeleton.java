package com.example.demo1.logic.actors;

import com.example.demo1.logic.Cell;

public class Skeleton extends Actor {

    public Skeleton(Cell cell) {
        super(cell);
        setAttack(10);
        setDefense(5);
        setHealth(20);
    }


    @Override
    public String getTileName() {
        return "skeleton";
    }
}
