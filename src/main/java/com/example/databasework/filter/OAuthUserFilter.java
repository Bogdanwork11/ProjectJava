package com.example.databasework.filter;

import com.example.databasework.entity.Users;
import com.example.databasework.repository.UserRepository;
import com.example.databasework.service.JWTService;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;


@Component
public class OAuthUserFilter {
    private final UserRepository userRepository;
    private final JWTService jwtService;

    public OAuthUserFilter(UserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public String oauth(@NonNull OAuth2User user) {
        if (user == null) {
            return "Пользователь не авторизован";
        }

        String email = user.getAttribute("email");

        Users foundUser = userRepository.findByLogin(email);

        if (foundUser == null) {
            return "Пользователь не найден";
        }

        String token = jwtService.generateToken(foundUser.getLogin(), foundUser.getRole());
        System.out.println("Токен: " + token);


        return "Здравствуйте, " + user.getAttribute("name")
                + " Ваш email: " + user.getAttribute("email")
                + " Ваша роль: " + foundUser.getRole();

    }

}
