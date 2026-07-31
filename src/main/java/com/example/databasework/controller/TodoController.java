package com.example.databasework.controller;

import com.example.databasework.dto.DtoTranzaktion;
import com.example.databasework.dto.MainDto;
import com.example.databasework.entity.Todo;
import com.example.databasework.filter.JwtFilter;
import com.example.databasework.service.TodoService;
import com.example.databasework.service.V3serviceTranz;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/todos")
public class TodoController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final TodoService todoService;
    private final JwtFilter jwtFilter;
    private final V3serviceTranz V3service;

    public TodoController(TodoService todoService, JwtFilter jwtFilter, V3serviceTranz service) {
        this.todoService = todoService;
        this.jwtFilter = jwtFilter;
        this.V3service = service;
    }

    @Value("${external-api.base-url}")
    private String Todos_url;


    @GetMapping
    public List<Todo> getAllTodos(
            HttpServletRequest request) {
        return todoService.getAllTodos();
    }

    @PostMapping
    public ResponseEntity<?> addinTodos(
            @RequestBody MainDto newTodo) {
        return todoService.addinTodos(newTodo);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTodo(
            @PathVariable int id) {
        return todoService.deleteTodo(id);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> updateTodo(
            @RequestBody MainDto updateData,
            @PathVariable int id) {
        return todoService.updateTodo(updateData, id);
    }

    //----------------------------------------------------------

    //эндпоинты транзакции
    @PatchMapping("/transfer")
    public String transfer(@RequestBody DtoTranzaktion request) {
        V3service.transfer(request);
        return "Транзакция была выполнена...";
    }

    @GetMapping("/information")
    public String getInfo() {
        V3service.getInfo();
        return V3service.getInfo();
    }

    //----------------------------------------------------------

    @GetMapping("/{authorId}")
    public List<Todo> getIdTodo(
            @PathVariable Integer authorId) {
        return todoService.getIdTodo(authorId);
    }

    @GetMapping("/criteria/{authorId}")
    public List<Todo> getIdTodoCriteria(
            @PathVariable Integer authorId) {
        return todoService.getIdTodoCriteria(authorId);
    }



}
