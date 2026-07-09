package com.example.databasework.controller;

import com.example.databasework.Role;
import com.example.databasework.dto.DtoTranzaktion;
import com.example.databasework.dto.MainDto;
import com.example.databasework.entity.TodoEntity;
import com.example.databasework.filter.JwtFilter;
import com.example.databasework.service.TodoService;
import com.example.databasework.service.V3serviceTranz;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@RestController
@RequestMapping("/todos")
public class  TodoController {

    private final RestTemplate restTemplate = new RestTemplate();
    //private final TodoHibernateService todoService;
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
    public List<TodoEntity> getAllTodos(
        @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        jwtFilter.validateActiveUser(token);
        Claims claims = jwtFilter.extractClaims(token);
        String roleStr = claims.get("role", String.class);

        Role role = Role.fromString(roleStr);

        return todoService.getAllTodos(role);
    }

    @PostMapping
    public ResponseEntity<?> addinTodos(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody MainDto newTodo) {
        System.out.println("1");
        String token = authHeader.replace("Bearer ", "");
        System.out.println("2");
        jwtFilter.validateActiveUser(token);
        System.out.println("3");
        Claims claims = jwtFilter.extractClaims(token);
        System.out.println("4");
        String roleStr = claims.get("role", String.class);
        Role role = Role.fromString(roleStr);
        System.out.println("5");

        return todoService.addinTodos(role, newTodo);


    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTodo(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable int id){
        String token = authHeader.replace("Bearer ", "");
        jwtFilter.validateActiveUser(token);
        Claims claims = jwtFilter.extractClaims(token);
        String roleStr = claims.get("role", String.class);
        Role role = Role.fromString(roleStr);

        return todoService.deleteTodo(role, id);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> updateTodo(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody MainDto updateData,
            @PathVariable int id){
        String token = authHeader.replace("Bearer ", "");
        jwtFilter.validateActiveUser(token);
        Claims claims = jwtFilter.extractClaims(token);
        String roleStr = claims.get("role", String.class);
        Role role = Role.fromString(roleStr);


        return todoService.updateTodo(role, updateData, id);
    }

    //транзакции эндпоинты
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



}
