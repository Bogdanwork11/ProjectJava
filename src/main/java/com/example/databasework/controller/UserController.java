package com.example.databasework.controller;

//OAuth2AuthenticationToken получение авторизации пользователя

import com.example.databasework.dto.UserProfileDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getAuthenUser(
            OAuth2AuthenticationToken authentification){

        String provider = authentification.getAuthorizedClientRegistrationId(); //google
        OAuth2User principal = authentification.getPrincipal();
        String email = principal.getAttribute("email");
        String name = principal.getAttribute("name");

        UserProfileDto response = new UserProfileDto(name, email, provider);
        return ResponseEntity.ok(response);
    }
}
