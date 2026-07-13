package com.example.databasework.dto;

import org.w3c.dom.Text;

import java.time.Instant;

public class todoV2Dto {
    private Integer id;
    private Instant created_at;
    private Instant updated_at;
    private String text;
    private Boolean status;
    private Boolean is_visible;
    private Integer author;

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

    public Boolean getStatus() {
        return status;
    }

    public Boolean getIs_visible() {
        return is_visible;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Instant updated_at) {
        this.updated_at = updated_at;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setIs_visible(Boolean is_visible) {
        this.is_visible = is_visible;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }
}
