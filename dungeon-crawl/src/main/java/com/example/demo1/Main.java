package com.example.demo1;

import com.example.demo1.logic.Cell;
import com.example.demo1.logic.CellType;
import com.example.demo1.logic.GameMap;
import com.example.demo1.logic.MapLoader;
import com.example.demo1.logic.actors.Actor;
import com.example.demo1.logic.actors.Player;
import com.example.demo1.logic.controller.*;
import com.example.demo1.logic.dao.GameDatabaseManager;
import com.example.demo1.logic.items.Item;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Random;

public class Main extends Application {
    GameMap map = MapLoader.loadMap("/map2.txt");
    private static TextArea consoleBattleLog;

    GameDatabaseManager gameDatabaseManager = new GameDatabaseManager();


    Canvas canvas = new Canvas(
            224,
            224);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label("Health: " + map.getPlayer().getHealth());
    Label defenseLabel = new Label("Defense: " + map.getPlayer().getDefense());
    Label attackLabel = new Label("Attack: " + map.getPlayer().getAttack());

    FightingController fightingController = new FightingController();

    ListView<String> inventoryList = new ListView<>();
    int monsterMoveTour = 0;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(300);
        ui.setPadding(new Insets(10));

        String musicFile = "src/main/resources/dungeon.mp3";
        MusicPlayer musicPlayer = new MusicPlayer(musicFile);
        musicPlayer.play();

        consoleBattleLog = new TextArea();
        consoleBattleLog.setEditable(false);
        consoleBattleLog.setPrefHeight(130);
        consoleBattleLog.setStyle("-fx-text-fill: white; -fx-control-inner-background: black;");
        ui.add(new Label("Console: "), 0, 9);
        ui.add(consoleBattleLog, 0, 15);
        consoleBattleLog.setFocusTraversable(false);

        Button pickUpButton = new Button("Pick up");
        pickUpButton.setOnAction(event -> {
            pickUpItem();
            canvas.requestFocus();
        });
        pickUpButton.setFocusTraversable(false);

        healthLabel.setText("Health: " + map.getPlayer().getHealth());
        defenseLabel.setText("Defense: " + map.getPlayer().getDefense());
        attackLabel.setText("Attack: " + map.getPlayer().getAttack());

        ui.add(healthLabel, 0, 0);
        ui.add(defenseLabel, 0, 1);
        ui.add(attackLabel, 0, 2);
        ui.add(pickUpButton, 0, 6);

        inventoryList.setFocusTraversable(false);
        inventoryList.setPrefHeight(100);
        ui.add(new Label("Inventory:"), 0, 7);
        ui.add(inventoryList, 0, 8);


        BorderPane canvasContainer = new BorderPane();
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);
        canvasContainer.setCenter(borderPane);

        Scene scene = new Scene(canvasContainer);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        canvas.requestFocus();

        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

    }

    private void pickUpItem() {
        map.getPlayer().pickUpItem(map.getPlayer());
        System.out.println();

        updateInventoryList();
        inventoryList.refresh();
    }

    public ListView<String> updateInventoryList() {
        clearConsole();
        inventoryList.getItems().clear();
        for (Item item : map.getPlayer().getInventory()) {
            Main.addTextConsole("Picked: " + item.getTileName());
            inventoryList.getItems().add(item.getTileName());
            updateDoorState();
        }
        return inventoryList;
    }

    public void updateDoorState() {
        Cell doorCell = findDoor();
        if (map.getPlayer().hasKey()) {
            doorCell.setType(CellType.OPEN_DOOR);
            refresh();
        } else {
            doorCell.setType(CellType.CLOSED_DOOR);
        }
    }


    public Cell findDoor() {
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Cell cell = map.getCell(x, y);
                if (cell.getType() == CellType.CLOSED_DOOR) {
                    return cell;
                }
            }
        }
        return null;
    }


    private void onKeyPressed(KeyEvent keyEvent) {
        monsterDirection();
        monsterMoveTour++;
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                refresh();
                fightingController.isMonsterInField(map.getPlayer(), MapLoader.monsters);
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                refresh();
                fightingController.isMonsterInField(map.getPlayer(), MapLoader.monsters);
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                refresh();
                fightingController.isMonsterInField(map.getPlayer(), MapLoader.monsters);
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
                refresh();
                fightingController.isMonsterInField(map.getPlayer(), MapLoader.monsters);
                break;
        }

        if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.S) {
            try {
                gameDatabaseManager.setup();
                gameDatabaseManager.savePlayer(map.getPlayer());
                gameDatabaseManager.saveItems(inventoryList);
                addTextConsole("Saved..");
            } catch (SQLException e) {
                addTextConsole("Cannot save");
            }
            keyEvent.consume();
        }

        Cell playerCell = map.getPlayer().getCell();
        if (playerCell.getType() == CellType.OPEN_DOOR) {
            map = MapLoader.loadMap("/map.txt");
            healthLabel.setText("Health: " + map.getPlayer().getHealth());
            defenseLabel.setText("Defense: " + map.getPlayer().getDefense());
            attackLabel.setText("Attack: " + map.getPlayer().getAttack());
            clearConsole();
            updateInventoryList();

        }


        refresh();
        fightingController.isMonsterInField(map.getPlayer(), MapLoader.monsters);
    }


    public void restartGame() {
        map = MapLoader.loadMap("/map2.txt");
        monsterMoveTour = 0;
        map.resetMap();
        map.getPlayer().resetPlayer();
        refresh();
    }

    private static Directions pickDirection() {
        Directions[] directions = Directions.values();
        Random random = new Random();
        int randomIndex = random.nextInt(directions.length);
        return directions[randomIndex];
    }

    private void monsterDirection() {
        if (monsterMoveTour == 3) {
            monsterMoveTour = 0;
        }
        for (Actor monster : MapLoader.monsters) {
            System.out.print(monster.getTileName());
            System.out.println("X: " + monster.getX() + "Y: " + monster.getY());
            System.out.println();
            Directions direction = pickDirection();
            if (monster.getTileName().equals("ghost")) {
                if (monsterMoveTour == 1 || monsterMoveTour == 2 || monsterMoveTour == 3) {
                    Player playerCord = map.getPlayer();
                    int dx = playerCord.getX() - monster.getX();
                    int dy = playerCord.getY() - monster.getY();
                    if (Math.abs(dx) > Math.abs(dy)) {
                        if (dx > 0) {
                            monster.move(1, 0);
                        } else {
                            monster.move(-1, 0);
                        }
                    } else {
                        if (dy > 0) {
                            monster.move(0, +1);
                        } else {
                            monster.move(0, -1);
                        }
                    }
                }
            } else if (monsterMoveTour == 1) {
                switch (direction) {
                    case east:
                        monster.move(0, -1);
                        refresh();
                        break;
                    case south:
                        monster.move(0, 1);
                        refresh();
                        break;
                    case west:
                        monster.move(-1, 0);
                        refresh();
                        break;
                    case north:
                        monster.move(1, 0);
                        refresh();
                        break;
                }
            }
        }
    }

    private void refresh() {
        if (map.getPlayer().getHealth() <= 0) {
            restartGame();
            return;
        }
        int playerX = map.getPlayer().getCell().getX();
        int playerY = map.getPlayer().getCell().getY();
        int viewStartX = Math.max(0, playerX - 3);
        int viewStartY = Math.max(0, playerY - 3);
        int viewEndX = Math.min(map.getWidth() - 1, playerX + 3);
        int viewEndY = Math.min(map.getHeight() - 1, playerY + 3);
        context.setFill(Color.BLACK);
        healthLabel.setText("Health:      " + map.getPlayer().getHealth());
        attackLabel.setText("Attack:      " + map.getPlayer().getAttack());
        defenseLabel.setText("Defense:   " + map.getPlayer().getDefense());
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.translate(-viewStartX * Tiles.TILE_WIDTH, -viewStartY * Tiles.TILE_WIDTH);
        for (int x = viewStartX; x <= viewEndX; x++) {
            for (int y = viewStartY; y <= viewEndY; y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else if (cell.getItem() != null) {
                    Tiles.drawTile(context, cell.getItem(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        context.translate(viewStartX * Tiles.TILE_WIDTH, viewStartY * Tiles.TILE_WIDTH);
    }

    public static void addTextConsole(String text) {
        consoleBattleLog.appendText(text + "\n");
    }

    public static void clearConsole() {
        consoleBattleLog.clear();
    }
}

