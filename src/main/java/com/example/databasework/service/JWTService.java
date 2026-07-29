package com.example.databasework.service;

import com.example.databasework.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    //to read why it is bad to keep secrets or password in String
    //private String secret = "mySuperSecretKeyThatIsAtLeast32CharactersLong!!!!";
    @Value("${jwt.secret}")
    private String secret;
    //генерация токена
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role );

        return createToken(claims, username);

    }



    //создание токена
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 часов
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    //Переместить в фильтр? Спросить у gpt?
    //claim из токена
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    //роль из токена
    public String extractRole(String token) {
        Claims claims = extractClaims(token);
        return claims.get("role", String.class);
    }

    //просрочен ли токен
    public boolean isTokenValid(String token) {
        Claims claims = extractClaims(token);
        Date expiration = claims.getExpiration();
        return expiration.after(new Date());
    }

}
