package com.example.databasework.dto;

import java.time.Instant;

public record MainDto(
    Integer id,
    Integer userId,
    String title,
    Boolean completed,
    Integer authorId,
    Integer statusId
){}
