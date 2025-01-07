package com.example.demo1.logic.dao;

import com.example.demo1.Main;
import com.example.demo1.logic.items.Item;
import com.example.demo1.logic.model.ItemModel;
import com.example.demo1.logic.model.PlayerModel;
import javafx.scene.control.ListView;

import javax.sql.DataSource;
import java.sql.*;

public class ItemDaoJdbc implements ItemDao {

    private DataSource dataSource;

    public ItemDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(ListView<String> list) {
        try (Connection conn = dataSource.getConnection()) {
            for (String item : list.getItems()) {
                String sql = "INSERT INTO items (item_name, player_id) VALUES (?,?)";
                PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, item);
                statement.setInt(2, 1);
                statement.executeUpdate();
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}

