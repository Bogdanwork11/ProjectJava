package com.example.databasework.dto;

public record MainDto(
    Integer id,
    Integer userId,
    String title,
    Boolean completed,
    Integer authorId,
    Integer statusId
){}
