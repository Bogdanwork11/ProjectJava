package com.example.databasework.service;

import com.example.databasework.entity.AuthorEntity;
import com.example.databasework.entity.StatusEntity;
import com.example.databasework.entity.TodoEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TodoRowMapper implements RowMapper<TodoEntity> {

    @Override
    public TodoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {

        TodoEntity todo = new TodoEntity();
        AuthorEntity author = new AuthorEntity();
        StatusEntity status = new StatusEntity();

        todo.setId(rs.getInt("ID"));
        todo.setCreated_at(rs.getTimestamp("Created_at").toInstant());
        todo.setUpdated_at(rs.getTimestamp("Updated_at").toInstant());
        todo.setText(rs.getString("Text"));
        todo.setIs_visible(rs.getBoolean("Is_visible"));
        author.setId(rs.getInt("AUTHOR_ID"));
        author.setAuthor(rs.getString("AUTHOR_NAME"));
        status.setId(rs.getInt("STATUS_ID"));
        status.setStatus(rs.getBoolean("STATUS_NAME"));

        todo.setAuthor(author);
        todo.setStatus(status);




        return todo;
    }
}