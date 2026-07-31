package com.example.databasework.service;

import com.example.databasework.entity.Authors;
import com.example.databasework.entity.Status;
import com.example.databasework.entity.Todo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TodoRowMapper implements RowMapper<Todo> {

    @Override
    public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {

        Todo todo = new Todo();
        Authors author = new Authors();
        Status status = new Status();

        todo.setId(rs.getInt("ID"));
        todo.setCreatedAt(rs.getTimestamp("Created_at").toInstant());
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