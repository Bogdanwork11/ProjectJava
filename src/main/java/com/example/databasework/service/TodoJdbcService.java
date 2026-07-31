package com.example.databasework.service;

import com.example.databasework.dto.MainDto;
import com.example.databasework.entity.Todo;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

//Primary
@Service
public class TodoJdbcService implements TodoService {

    private final JdbcTemplate jdbcTemplate;


    public TodoJdbcService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //GET
    @Override
    public List<Todo> getAllTodos() {

        String sql = """
                SELECT * FROM TODO_VIEW
                """;
            return jdbcTemplate.query(sql, new TodoRowMapper());


        }


    //POST
    @Override
    public ResponseEntity<?> addinTodos( MainDto newTodo) {

            String sql = """

                        INSERT INTO TODO
                    (CREATED_AT, UPDATED_AT, TEXT, STATUS_ID, IS_VISIBLE, AUTHOR_ID)
                    VALUES (?, ?, ?, ?, ?, ?)

                    """;


            jdbcTemplate.update(

                    sql,
                    Instant.now(),
                    Instant.now(),
                    newTodo.title(),
                    newTodo.statusId(),
                    true,
                    newTodo.authorId()


            );

            return ResponseEntity.ok("Ваша todoshка добавлена");

        }


    //DELETE
    @Override
    public ResponseEntity<?> deleteTodo(int id) {
            String sql = """
                    DELETE FROM TODO
                    WHERE ID = ?
                    """;
            jdbcTemplate.update(sql, id);
            return ResponseEntity.ok(id);

    }

    //PATCH
    @Override
    public ResponseEntity<?> updateTodo( MainDto updateData, int id) {

            String sql = """
                    UPDATE TODO
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
                    updateData.title(),
                    updateData.statusId(),
                    true,
                    updateData.authorId(),
                    id

            );
            return ResponseEntity.ok("Обновлено todo с id: " + id);
        }



    //get author id
    public List<Todo> getIdTodo(Integer id) {
            String sql = """
                    SELECT t.*, a.AUTHOR as AUTHOR_NAME, s.STATUS as STATUS_NAME
                    FROM TODO t
                    JOIN AUTHOR a ON t.AUTHOR_ID = a.ID
                    JOIN STATUS s ON t.STATUS_ID = s.ID
                    WHERE t.AUTHOR_ID = ?
                    ORDER BY CREATED_AT DESC;
                    """;
            return jdbcTemplate.query(
                    sql,
                    new TodoRowMapper(),
                    id

            );
    }

    public List<Todo>getIdTodoCriteria(Integer id){
            String sql = """
                    SELECT t.*, a.AUTHOR as AUTHOR_NAME, s.STATUS as STATUS_NAME
                    FROM TODO t
                    JOIN AUTHOR a ON t.AUTHOR_ID = a.ID
                    JOIN STATUS s ON t.STATUS_ID = s.ID
                    WHERE t.AUTHOR_ID = ?
                    ORDER BY CREATED_AT DESC;
                    """;
            return jdbcTemplate.query(
                    sql,
                    new TodoRowMapper(),
                    id

            );
    }

}
