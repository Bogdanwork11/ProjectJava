package com.example.databasework.role;


public enum Role {
    ADMIN("Администратор ⭐"),
    USER("Пользователь ★");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }

    public static Role fromString(String value){
        if(value == null) {
            throw new RuntimeException("Роль пуста");
        }
        return Role.valueOf(value.toUpperCase());
    }

}
