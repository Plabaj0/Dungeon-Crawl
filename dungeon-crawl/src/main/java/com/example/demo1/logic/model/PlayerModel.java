package com.example.demo1.logic.model;

import com.example.demo1.logic.actors.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerModel extends BaseModel {
    private int hp;
    private int x;
    private int y;
    private int attack;
    private int defense;

    public PlayerModel(Player player) {
        this.x = player.getX();
        this.y = player.getY();
        this.hp = player.getHealth();
        this.attack = player.getAttack();
        this.defense = player.getDefense();

    }
}
