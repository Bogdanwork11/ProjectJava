package com.example.databasework.configuration;

import com.example.databasework.filter.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import com.example.databasework.handler.OAuth2SuccessHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    public SecurityConfig(OAuth2SuccessHandler oAuth2SuccessHandler) {
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
    }

    //------цепочка построения фильтров-----
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {

        http

                .csrf(csrf -> csrf.disable()
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.GET, "/api/use/me")
                        .hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.GET, "/todos")
                        .hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.POST, "/todos")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/todos/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PATCH, "/todos/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/todos/**")
                        .hasAnyRole("ADMIN", "USER")

                        .requestMatchers(HttpMethod.GET, "todos/criteria/**")
                        .hasAnyRole("ADMIN", "USER")


                        .requestMatchers("/login/**", "/oauth2/**", "/h2-console/**", "/error").permitAll() //разрешен доступ без аунтмфикации, остальное с аунтификациями
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                .oauth2Login(oauth -> oauth
                        .successHandler(oAuth2SuccessHandler)

                );

        return http.build();
    }
}