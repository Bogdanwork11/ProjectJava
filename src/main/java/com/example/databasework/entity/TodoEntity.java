package com.example.databasework.entity;

import jakarta.persistence.*;


@Entity
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "COMPLETED", nullable = false)
    private Boolean completed;


    public TodoEntity() {
    }

    public TodoEntity(Integer id, Integer userId, String title, Boolean completed) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.completed = completed;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getCompleted() {
        return completed;
    }



    public void setId(Integer id) {
        this.id = id;
    }

    ;

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }



}