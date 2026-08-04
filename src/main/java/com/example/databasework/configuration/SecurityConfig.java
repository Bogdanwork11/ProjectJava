package com.example.databasework.configuration;

import com.example.databasework.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
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




                        .requestMatchers("/login/**", "/oauth2/**", "/h2-console/**").permitAll() //разрешен доступ без аунтмфикации, остальное с аунтификациями
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(Customizer.withDefaults());

        return http.build();
    }
}
