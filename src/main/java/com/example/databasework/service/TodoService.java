package com.example.databasework.service;

import com.example.databasework.Role;
import com.example.databasework.dto.MainDto;
import com.example.databasework.entity.TodoEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TodoService {

    List<TodoEntity> getAllTodos(Role role);

    ResponseEntity<?> addinTodos(Role role, MainDto newTodo);

    ResponseEntity<?> deleteTodo(Role role, int id);

    ResponseEntity<?> updateTodo(Role role, MainDto updateData, int id);

    List<TodoEntity> getIdTodo(Integer authorId, Role role);

    List<TodoEntity> getIdTodoCriteria(Integer authorId, Role role);
}