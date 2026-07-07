package com.example.databasework;

public enum Role {
    ADMIN,
    USER;

    public static Role fromString(String roleStr){
        if (roleStr == null){
            System.out.println("Роль не может быть null");
        }
        return Role.valueOf(roleStr.toUpperCase());
    }
}
