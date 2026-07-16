package com.example.databasework.controller;

import com.example.databasework.dto.LoginRequest;
import com.example.databasework.entity.UserEntity;
import com.example.databasework.repository.UserRepository;
import com.example.databasework.service.JWTService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final JWTService jwtService;
    private final UserRepository userRepository;
    ;

    public LoginController(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        UserEntity user = userRepository.findByLogin(request.login());

        if (user == null) {
            throw new RuntimeException("Пользователь не найден");
        }

        if (!user.getPassword().equals(request.password())) {
            throw new RuntimeException("Не вернвй пароль");
        }
        String token = jwtService.generateToken(user.getLogin(), user.getRole());
        System.out.println("Токен: " + token);

        return jwtService.generateToken(user.getLogin(), user.getRole());
    }

}
