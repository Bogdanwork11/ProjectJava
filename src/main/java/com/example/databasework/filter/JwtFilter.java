package com.example.databasework.filter;

import com.example.databasework.Role;
import com.example.databasework.entity.UserEntity;
import com.example.databasework.repository.UserRepository;
import com.example.databasework.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Component
public class JwtFilter { //todo to read about springboot filters
    private final JWTService jWTService;
    private final UserRepository userRepository;

    @Value("${spring.jwt.secret}")
    private String secret;

    public JwtFilter(JWTService jWTService, UserRepository userRepository) {
        this.jWTService = jWTService;
        this.userRepository = userRepository;
    }

    public Claims extractClaims(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        String login = claims.getSubject();
        String role = claims.get("role", String.class);

        return claims;

    }

    public void validateActiveUser(String token) {

        Claims claims = extractClaims(token);

        String login = claims.getSubject();

        UserEntity user = userRepository.findByLogin(login);

        if (user != null) {
            System.out.println("DB login = " + user.getLogin());
            System.out.println("Active = " + user.getIsActive());
        }

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }

        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is inactive");
        }

    }

    public Role authentificate(String authHeader) {
        String token = authHeader.replace("Bearer", "");
        validateActiveUser(token);
        return Rolecheck(token);
    }

    public Role Rolecheck(String token) {
        Claims claims = extractClaims(token);
        String roleStr = claims.get("role", String.class);
        return Role.fromString(roleStr);
    }
}





