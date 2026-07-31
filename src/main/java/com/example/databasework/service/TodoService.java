package com.example.databasework.service;

import com.example.databasework.dto.MainDto;
import com.example.databasework.entity.Todo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TodoService {

    List<Todo> getAllTodos();

    ResponseEntity<?> addinTodos(MainDto newTodo);

    ResponseEntity<?> deleteTodo(int id);

    ResponseEntity<?> updateTodo(MainDto updateData, int id);

    List<Todo> getIdTodo(Integer authorId);

    List<Todo> getIdTodoCriteria(Integer authorId);


}