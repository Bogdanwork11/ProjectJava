package com.example.databasework.controller;

import com.example.databasework.filter.OAuthUserFilter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final OAuthUserFilter oAuthUserFilter;

    public AuthController(OAuthUserFilter oAuthUserFilter) {
        this.oAuthUserFilter = oAuthUserFilter;
    }

    @GetMapping("/")
    public String oauth(@AuthenticationPrincipal OAuth2User user) {
        return oAuthUserFilter.oauth(user);

    }
}
