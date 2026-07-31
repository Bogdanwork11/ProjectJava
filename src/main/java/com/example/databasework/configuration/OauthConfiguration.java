package com.example.databasework.configuration;


import com.example.databasework.filter.JwtFilter;
import com.example.databasework.service.JWTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;


//создание обьекта, который хранит в себе всю информацию, необходимую для подключения к google по протоколу oauth
@Configuration
@EnableWebSecurity

public class OauthConfiguration {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientid;


    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        System.out.println(clientid);
        System.out.println(clientSecret);
        return new InMemoryClientRegistrationRepository(
                ClientRegistration.withRegistrationId("google")
                        .clientId(clientid)
                        .clientSecret(clientSecret)
                        .scope("openid", "profile", "email")
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                        .issuerUri("https://accounts.google.com")
                        .redirectUri("http://localhost:8080/login/oauth2/code/google")
                        .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                        .tokenUri("https://oauth2.googleapis.com/token")
                        .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                        .userNameAttributeName("sub")
                        .build()
        );
    }

    @Bean
    public JwtFilter jwtFilter(JWTService jwtService) {
        return new JwtFilter(jwtService);
    }


}