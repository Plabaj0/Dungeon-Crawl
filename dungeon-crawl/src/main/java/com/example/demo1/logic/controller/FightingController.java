package com.example.demo1.logic.controller;

import com.example.demo1.Main;
import com.example.demo1.logic.MapLoader;
import com.example.demo1.logic.actors.Actor;
import com.example.demo1.logic.actors.Player;
import javafx.application.Platform;

import java.util.List;
import java.util.Random;

public class FightingController {

    public void isMonsterInField(Player player, List<Actor> skeletons) {
        for (Actor skeleton : skeletons) {
            int playerX = player.getX();
            int playerY = player.getY();
            int skeletonX = skeleton.getX();
            int skeletonY = skeleton.getY();
            if (playerX == skeletonX && playerY == skeletonY) {
                battleLog(player, skeleton);
            }
        }
    }

    private void battleLog(Player player, Actor skeleton) {
        Random random = new Random();

        Main.clearConsole();
        while (player.getHealth() > 0 && skeleton.getHealth() > 0) {
            int playerFactor = random.nextInt(16);
            int monsterFactor = random.nextInt(14);

            int playerAttack = player.getAttack();
            int playerDefense = player.getDefense();
            int playerHealth = player.getHealth();

            int skeletonAttack = skeleton.getAttack();
            int skeletonDefense = skeleton.getDefense();
            int skeletonHealth = skeleton.getHealth();

            int monsterCurrentHealth = skeletonHealth - Math.max(0, (playerFactor + playerAttack) - skeletonDefense);
            Main.addTextConsole("monster health:  " + monsterCurrentHealth);
            skeleton.setHealth(Math.max(0, monsterCurrentHealth));


            int playerCurrentHealth = playerHealth - Math.max(0, (monsterFactor + skeletonAttack) - playerDefense);
            player.setHealth(Math.max(0, playerCurrentHealth));
            Main.addTextConsole("player health:  " + playerCurrentHealth);
            Main.addTextConsole("----------------");


            if (playerCurrentHealth <= 0) {
                Main.addTextConsole("Player defeated!");
                break;
            }
            if (monsterCurrentHealth <= 0) {
                Main.addTextConsole("Skeleton defeated!");
                MapLoader.monsters.remove(skeleton);
                break;
            }
        }
    }
}
