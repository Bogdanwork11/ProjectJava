package com.example.databasework.controller;

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
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {

        UserEntity user = userRepository.findByLogin(request.getLogin());

        if (user == null) {
            return ResponseEntity.status(401).body("Пользователь не найден");
        }

        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).body("Не верный пароль");
        }

        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new RuntimeException("User is inactive");
        }

        String token = jwtService.generateToken(user.getLogin(), user.getRole());

        return ResponseEntity.ok(token);
    }
}
