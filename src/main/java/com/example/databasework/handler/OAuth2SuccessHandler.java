package com.example.databasework.handler;

import com.example.databasework.entity.Users;
import com.example.databasework.repository.UserRepository;
import com.example.databasework.service.JWTService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
////помечен как управляющий компонент жизненным циклом когда нужно будет запустить методы а когда нужно уничтожить обьект при закрытии
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JWTService jwtService;

    public OAuth2SuccessHandler(
            UserRepository userRepository,
            JWTService jwtService
    ) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        OAuth2AuthenticationToken oauthToken =
                (OAuth2AuthenticationToken) authentication;

        OAuth2User oauthUser = oauthToken.getPrincipal();

        String email = oauthUser.getAttribute("email");

        Users user = userRepository.findByEmail(email);

        if (user == null) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Пользователя нет в локальной бдшке"
            );
            return;
        }

        String jwt = jwtService.generateToken(
                user.getEmail(),
                user.getRole()
        );

        response.setContentType("application/json");
        response.getWriter().write(
                """
                        {
                            "message": "Авторизация успешна",
                            "email": "%s",
                            "role": "%s",
                            "token": "%s"
                        }
                        """.formatted(
                        user.getEmail(),
                        user.getRole(),
                        jwt
                )
        );
    }
}