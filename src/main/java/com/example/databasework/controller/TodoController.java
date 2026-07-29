package com.example.databasework.controller;

import com.example.databasework.Role;
import com.example.databasework.dto.DtoTranzaktion;
import com.example.databasework.dto.MainDto;
import com.example.databasework.entity.TodoEntity;
import com.example.databasework.entity.UserEntity;
import com.example.databasework.filter.JwtFilter;
import com.example.databasework.repository.UserRepository;
import com.example.databasework.service.JWTService;
import com.example.databasework.service.TodoService;
import com.example.databasework.service.V3serviceTranz;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final TodoService todoService;
    private final JwtFilter jwtFilter;
    private final V3serviceTranz V3service;
    private final UserRepository userRepository;
    private final JWTService jwtService;

    public TodoController(TodoService todoService, JwtFilter jwtFilter, V3serviceTranz service, UserRepository userRepository, JWTService jwtService) {
        this.todoService = todoService;
        this.jwtFilter = jwtFilter;
        this.V3service = service;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Value("${external-api.base-url}")
    private String Todos_url;


//    @PostMapping("/users")
//    public ResponseEntity<?> addInUsers(
//
//    )

    @GetMapping
    public List<TodoEntity> getAllTodos(
            @RequestHeader("Authorization") String authHeader) {
        Role role = jwtFilter.authentificate(authHeader);
        return todoService.getAllTodos(role);
    }

    @PostMapping
    public ResponseEntity<?> addinTodos(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody MainDto newTodo) {
        Role role = jwtFilter.authentificate(authHeader);
        return todoService.addinTodos(role, newTodo);
    }

    //fixme to move that from here
    //fixme to move that from here
    //todo to read about how we can extract role at filter and pin that to invocation (current thread) context

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTodo(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable int id) {
        Role role = jwtFilter.authentificate(authHeader);
        return todoService.deleteTodo(role, id);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> updateTodo(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody MainDto updateData,
            @PathVariable int id) {
        Role role = jwtFilter.authentificate(authHeader);
        return todoService.updateTodo(role, updateData, id);
    }

    //----------------------------------------------------------

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

    //----------------------------------------------------------

    @GetMapping("/{authorId}")
    public List<TodoEntity> getIdTodo(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Integer authorId) {

        Role role = jwtFilter.authentificate(authHeader);

        return todoService.getIdTodo(authorId, role);
    }

    @GetMapping("/criteria/{authorId}")
    public List<TodoEntity> getIdTodoCriteria(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Integer authorId) {
        Role role = jwtFilter.authentificate(authHeader);

        return todoService.getIdTodoCriteria(authorId, role);
    }

    //----------------------------------------------------------
//авторизацую в отдельный контроллер поместить

    @GetMapping("/")
    public String oauth(@AuthenticationPrincipal OAuth2User user) {
        return todoService.oauth(user);

//        return "Здравствуйте, " + user.getAttribute("name")
//                + " Ваш email: " + user.getAttribute("email");


    }


}
