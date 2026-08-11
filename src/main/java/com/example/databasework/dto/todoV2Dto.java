package com.example.databasework.dto;

import java.time.Instant;

public record todoV2Dto(
    Integer id,
    Instant created_at,
    Instant updated_at,
    String text,
    Boolean status,
    Boolean is_visible,
    Integer author) {}

