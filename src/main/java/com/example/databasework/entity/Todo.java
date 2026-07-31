package com.example.databasework.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Instant;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "CREATED_AT")
    private Instant createdAt;

    @Column(name = "UPDATED_AT")
    private Instant updated_at;

    @Column(name = "TEXT")
    private String text;

    @ManyToOne
    @JoinColumn(name = "STATUS_ID")
    private StatusEntity status;

    @Column(name = "IS_VISIBLE")
    private Boolean is_visible;

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID")
    private AuthorEntity author;}



