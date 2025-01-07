package com.example.demo1.logic.actors;

import com.example.demo1.Main;
import com.example.demo1.logic.Cell;
import com.example.demo1.logic.items.Item;
import com.example.demo1.logic.items.Key;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor {
    private List<Item> inventory;


    public Player(Cell cell) {
        super(cell);
        setAttack(22);
        setDefense(10);
        setHealth(90);
        inventory = new ArrayList<>();

    }


    public String getTileName() {
        return "player";
    }

    public void pickUpItem(Player player) {
        Main.clearConsole();
        Cell actorCell = getCell();
        Item item = actorCell.getItem();
        if (item != null) {
            actorCell.setItem(null);
            inventory.add(item);
            if (item.getHealth() != 0){
                player.setHealth(player.getHealth() + item.getHealth());
            }
            if (item.getDefense() != 0) {
                player.setDefense(player.getDefense() + item.getDefense());
            }
            if (item.getAttack() != 0){
                player.setAttack(player.getAttack() + item.getAttack());
            }
        }
    }

    public boolean hasKey() {
        for (Item item : inventory) {
            if (item instanceof Key) {
                return true;
            }
        }
        return false;
    }


    public Item[] getInventory() {
        return inventory.toArray(new Item[0]);
    }


    public void resetPlayer() {
        setHealth(90);
        setAttack(22);
        setDefense(10);
        inventory.clear();
    }
}