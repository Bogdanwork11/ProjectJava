package com.example.databasework.entity;

import jakarta.persistence.*;


import java.time.Instant;


@Entity
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "CREATED_AT")
    private Instant created_at;

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
    private AuthorEntity author;



    public TodoEntity() {
    }

    public TodoEntity(Integer id, Instant created_at, Instant updated_at, String text, StatusEntity status, Boolean is_visible, AuthorEntity author) {
        this.id = id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.text = text;
        this.status = status;
        this.is_visible = is_visible;
        this.author = author;


    }

    public Integer getId() {
        return id;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public Instant getUpdated_at() {
        return updated_at;
    }

    public String getText() {
        return text;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public Boolean getIs_visible() {
        return is_visible;
    }

    public AuthorEntity getAuthor() {
        return author;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    ;

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Instant updated_at) {
        this.updated_at = updated_at;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public void setIs_visible(Boolean is_visible) {
        this.is_visible = is_visible;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

}