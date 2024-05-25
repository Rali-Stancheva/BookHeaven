package com.example.library.models.DTOs;


import java.time.LocalDate;

public class NewsDTO {

    private Long id;

    private String title;

    private String content;

    private LocalDate date;


    public NewsDTO(Long id, String title, String content,  LocalDate date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
