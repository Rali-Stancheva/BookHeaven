package com.example.library.models.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "News")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "title")
    private String title;


    @Column(nullable = false, name = "short-description")
    private String shortDescription;


    @Column(nullable = false, name = "content")
    private String content;


    @Column(nullable = false, name = "date")
    private LocalDate date;


    @Column(name = "image_URL")
    private String imageUrl;


    public News() {
    }

    public News(String title,String shortDescription, String content, LocalDate date, String imageUrl) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.content = content;
        this.date = date;
        this.imageUrl = imageUrl;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }



}
