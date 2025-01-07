package com.example.demo1.logic.actors;

import com.example.demo1.logic.Cell;

public class Ghost extends Actor{

    public Ghost(Cell cell){
        super(cell);
        setAttack(20);
        setDefense(5);
        setHealth(20);
    }

    @Override
    public String getTileName() {
        return "ghost";
    }
}