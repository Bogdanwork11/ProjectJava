package com.example.databasework.service;

import com.example.databasework.Role;
import com.example.databasework.dto.MainDto;
import com.example.databasework.entity.TodoEntity;
import com.example.databasework.service.TodoRowMapper;
import com.sun.tools.javac.Main;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.util.List;

@Primary
@Service
public class TodoJdbcService implements TodoService {

    private final JdbcTemplate jdbcTemplate;


    public TodoJdbcService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //GET
    @Override
    public List<TodoEntity> getAllTodos(Role role) {

        String sql = """
                SELECT * FROM TODO_ENTITY
                """;

        if (role == Role.ADMIN) {
            return jdbcTemplate.query(sql, new TodoRowMapper());
        }


        if (role == Role.USER) {
            return jdbcTemplate.query(sql, new TodoRowMapper());
        }

        return List.of();
    }

    //POST
    @Override
    public ResponseEntity<?> addinTodos(Role role, MainDto newTodo) {
        if (role == Role.ADMIN) {

            String sql = """
                    
                        INSERT INTO TODO_ENTITY
                    (USER_ID, TITLE, COMPLETED)
                    VALUES (?, ?, ?)
                    
                    """;
            jdbcTemplate.update(
                    sql,
                    newTodo.userId,
                    newTodo.title,
                    newTodo.

                            completed
            );

            return ResponseEntity.ok("Ваша todoshка добавлена");

        }
        if (role == Role.USER) {
            return ResponseEntity.status(403)
                    .body("Ты что тут делаешь юзер, только админ может добавлять пользователей");
        }
        return ResponseEntity.status(403).build();
    }

    //DELETE
    public ResponseEntity<?> deleteTodo(Role role, int id) {
        if (role == Role.ADMIN) {
            String sql = """
                    DELETE FROM TODO_ENTITY
                    WHERE ID = ?
                    """;
            jdbcTemplate.update(sql, id);
            return ResponseEntity.ok(id);
        }
        if (role == Role.USER) {
            return ResponseEntity.status(403)
                    .body("Ты что тут делаешь юзер, только админ может удалять пользователей");
        }

        return ResponseEntity.status(403).build();
    }

    //PATCH
    @Override
    public ResponseEntity<?> updateTodo(Role role, MainDto updateData, int id) {
        if (role == Role.ADMIN) {
            String sql = """
                    UPDATE TODO_ENTITY
                    SET USER_ID = ?,
                        TITLE = ?,
                        COMPLETED = ?
                    WHERE ID = ?
                    """;
            jdbcTemplate.update(
                    sql,
                    updateData.userId,
                    updateData.title,
                    updateData.completed,
                    id
            );
            return ResponseEntity.ok("Обновлено todo с id: " + id);
        }

        if (role == Role.USER) {
            return ResponseEntity.status(403)
                    .body("Незя тебе быть здесь пользователь, это дело Админов");
        }
        return ResponseEntity.status(403).build();

    }
}
