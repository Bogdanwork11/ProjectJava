package com.example.databasework.service;

import com.example.databasework.Role;
import com.example.databasework.dto.MainDto;

import com.example.databasework.entity.TodoEntity;


import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.ArrayList;

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
                SELECT * FROM TODO_VIEW
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
                    (CREATED_AT, UPDATED_AT, TEXT, STATUS_ID, IS_VISIBLE, AUTHOR_ID)
                    VALUES (?, ?, ?, ?, ?, ?)
                    
                    """;
//            System.out.println(newTodo.statusId + "Это statusId");
//            System.out.println(newTodo.authorId + "Это authorId");


            jdbcTemplate.update(

                    sql,
                    Instant.now(),
                    Instant.now(),
                    newTodo.title,
                    newTodo.statusId,
                    true,
                    newTodo.authorId


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
                    SET UPDATED_AT = ?,
                        TEXT = ?,
                        STATUS_ID = ?,
                        IS_VISIBLE = ?,
                        AUTHOR_ID = ?
                    WHERE ID = ?
                    """;
            jdbcTemplate.update(
                    sql,
                    Instant.now(),
                    updateData.title,
                    updateData.statusId,
                    true,
                    updateData.authorId,
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

    //get author id
    public List<TodoEntity> getIdTodo(Integer id, Role role) {
        if (role == Role.ADMIN) {
            String sql = """
                    SELECT t.*, a.AUTHOR as AUTHOR_NAME, s.STATUS as STATUS_NAME
                    FROM TODO_ENTITY t
                    JOIN AUTHOR_ENTITY a ON t.AUTHOR_ID = a.ID
                    JOIN STATUS_ENTITY s ON t.STATUS_ID = s.ID
                    WHERE t.AUTHOR_ID = ?
                    """;
            return jdbcTemplate.query(
                    sql,
                    new TodoRowMapper(),
                    id

            );
        }

        return new ArrayList<>();
    }
}
