package com.example.databasework.repository;

import com.example.databasework.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Integer> {

    List<TodoEntity> findByAuthorIdOrderByCreatedAtDesc(Integer authorId);

    //select * from todo_entity
    //where author_id = 1/2
    //order by created_at desc/asc

}
