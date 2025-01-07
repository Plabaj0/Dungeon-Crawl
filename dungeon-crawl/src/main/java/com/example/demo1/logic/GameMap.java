package com.example.demo1.logic;

import com.example.demo1.logic.actors.Player;
import com.example.demo1.logic.actors.Skeleton;
import lombok.Getter;
import lombok.Setter;

public class GameMap {
    @Getter
    private int width;
    @Getter
    private int height;
    private Cell[][] cells;

    private Cell centerCell;

    @Setter
    @Getter
    private Player player;

    @Setter
    @Getter
    private Skeleton skeleton;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell repositionCenter() {
        int nextX;
        int nextY;
        if (player.getCell().getX() <= 10) {
            nextX = 10;
        } else nextX = Math.min(player.getCell().getX(), width - 11);

        if (player.getCell().getY() <= 10) {
            nextY = 10;
        } else nextY = Math.min(player.getCell().getY(), height - 11);

        return centerCell = cells[nextX][nextY];
    }



    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void resetMap() {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Cell cell = cells[y][x];
                    cell.resetCell();

            }
        }
    }
}
