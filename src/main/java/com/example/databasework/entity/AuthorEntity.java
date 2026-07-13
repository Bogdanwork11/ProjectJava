package com.example.databasework.entity;

import jakarta.persistence.*;

@Entity
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "AUTHOR")
    private String author;

    public AuthorEntity() {
    }

    public AuthorEntity(Integer id, String author){
        this.id = id;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
