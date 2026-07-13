package com.example.databasework.repository;

import com.example.databasework.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {

    List<TodoEntity> findByAuthorId(Integer authorId);

    //Sql под капотом
    //select * from todo_entity
    //where author_id = ?

}
