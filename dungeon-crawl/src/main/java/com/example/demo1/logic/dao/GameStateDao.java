package com.example.demo1.logic.dao;


import com.example.demo1.logic.model.GameState;

import java.util.List;

public interface GameStateDao {
    void add(GameState state);
    void update(GameState state);
    GameState get(int id);
    List<GameState> getAll();
}
