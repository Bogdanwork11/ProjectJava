package com.example.databasework.entity;

import jakarta.persistence.*;


@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public UserEntity() {
    }

    public UserEntity(Integer id, String login, String password, String role, Boolean isActive) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public Boolean getIsActive() {return isActive;}



    public void setId(Integer id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setIsActive(Boolean isActive) {this.isActive = isActive;}


}
