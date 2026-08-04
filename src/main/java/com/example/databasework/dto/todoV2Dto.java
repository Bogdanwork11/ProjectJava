package com.example.databasework.dto;

import org.w3c.dom.Text;

import java.time.Instant;

public record todoV2Dto(
    Integer id,
    Instant created_at,
    Instant updated_at,
    String text,
    Boolean status,
    Boolean is_visible,
    Integer author) {}

