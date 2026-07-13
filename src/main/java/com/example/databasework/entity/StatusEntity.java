package com.example.databasework.entity;

import jakarta.persistence.*;

@Entity
public class StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "STATUS")
    private Boolean status;

    public StatusEntity(){
    }

    public StatusEntity(Integer id, Boolean status){
        this.id = id;
        this.status = status;


    }

    public Integer getId() {
        return id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
