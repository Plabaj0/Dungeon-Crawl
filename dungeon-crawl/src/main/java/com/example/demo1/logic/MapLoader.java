package com.example.demo1.logic;

import com.example.demo1.logic.actors.Actor;
import com.example.demo1.logic.actors.Ghost;
import com.example.demo1.logic.actors.Player;
import com.example.demo1.logic.actors.Skeleton;
import com.example.demo1.logic.items.Armor;
import com.example.demo1.logic.items.Hat;
import com.example.demo1.logic.items.Key;
import com.example.demo1.logic.items.Sword;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MapLoader {
    public static ArrayList<Actor> monsters = new ArrayList<>();
    public static GameMap loadMap(String filePath) {
        InputStream is = MapLoader.class.getResourceAsStream(filePath);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine();

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            monsters.add(new Skeleton(cell));
                            System.out.println("tyle szkieletow");
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            if (map.getPlayer() == null) {
                                map.setPlayer(new Player(cell));
                            } else {
                                map.getPlayer().setCell(cell);
                            }
                            break;
                        case 'X':
                            cell.setType(CellType.FLOOR);
                                map.getPlayer().setCell(cell);
                            break;
                        case 'w':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                            break;
                        case 'k':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case '-':
                            cell.setType(CellType.CLOSED_DOOR);
                            break;
                        case 'G':
                            cell.setType(CellType.FLOOR);
                            monsters.add(new Ghost(cell));
                            System.out.println("tyle duchow");
                            break;
                        case 'A':
                            cell.setType(CellType.FLOOR);
                            new Armor(cell);
                            break;
                        case 'H':
                            cell.setType(CellType.FLOOR);
                            new Hat(cell);
                            break;
                        case 'F':
                            cell.setType(CellType.FOREST);
                            break;

                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }
}
