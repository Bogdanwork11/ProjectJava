package com.example.databasework;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum Role {
    ADMIN,
    USER;

    public static Role fromString(String roleStr){
        if (roleStr == null){
            System.out.println("Роль не может быть null");
        }
        return Role.valueOf(roleStr.toUpperCase());
        @Enumerated(EnumType.STRING)
        private Role role;
    }

}
