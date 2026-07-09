package com.example.databasework.service;

import com.example.databasework.entity.TodoEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TodoRowMapper implements RowMapper<TodoEntity> {

    @Override
    public TodoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        TodoEntity todo = new TodoEntity();

        todo.setId(rs.getInt("ID"));
        todo.setCreated_at(rs.getTimestamp("Created_at").toInstant());
        todo.setUpdated_at(rs.getTimestamp("Updated_at").toInstant());
        todo.setText(rs.getString("Text"));
        todo.setStatus(rs.getBoolean("Status"));
        todo.setIs_visible(rs.getBoolean("Is_visible"));
        todo.setAuthor(rs.getString("Author"));

        return todo;
    }
}