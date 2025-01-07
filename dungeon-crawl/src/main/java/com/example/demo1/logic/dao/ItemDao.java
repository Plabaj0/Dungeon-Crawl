package com.example.demo1.logic.dao;

import com.example.demo1.logic.model.GameState;
import com.example.demo1.logic.model.ItemModel;
import javafx.scene.control.ListView;

import java.util.List;

public interface ItemDao {
    void add(ListView<String> list);
}

