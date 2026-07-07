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
        todo.setUserId(rs.getInt("USER_ID"));
        todo.setTitle(rs.getString("TITLE"));
        todo.setCompleted(rs.getBoolean("COMPLETED"));

        return todo;
    }
}