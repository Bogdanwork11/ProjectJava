package com.example.databasework.filter;

import com.example.databasework.entity.Users;
import com.example.databasework.repository.UserRepository;
import com.example.databasework.service.JWTService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter { //todo to read about springboot filters
    private final JWTService jwtService;
    private final UserRepository userRepository;

    public JwtFilter(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {



        log.info(request.getMethod());
        log.info(request.getRequestURI());
        log.info(request.getHeader("Authorization"));


        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            logger.info("success Bogdanchick now token ->" + authHeader);
            String token = authHeader.substring(7);

            System.out.println("Token validdd" + jwtService.isTokenValid(token));

            if (jwtService.isTokenValid(token)) {

                Claims claims = jwtService.extractClaims(token);



                String email = claims.getSubject();

                Users user = userRepository.findByLogin(email);

                String role = user.getRole();

//                String role = claims.get("role", String.class);






                var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
                var authToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }

        }

        System.out.println("Выведение до генерика токена = "
                + SecurityContextHolder.getContext().getAuthentication());

        filterChain.doFilter(request, response);

        System.out.println("Выведение после генерика токена = "
                + SecurityContextHolder.getContext().getAuthentication());



    }


}





