package com.example.library.models.DTOs;

import com.example.library.models.entities.Author;
import com.example.library.models.entities.Category;

import java.time.LocalDate;

public class BookDTO {
    private Long id;
    private String title;
    private LocalDate publicationDate;
    private String description;
    private Integer rating;
    private Author author;
    private Category category;


    public BookDTO(Long id, String title, LocalDate publicationDate, String description, Integer rating, Author author, Category category) {
        this.id = id;
        this.title = title;
        this.publicationDate = publicationDate;
        this.description = description;
        this.rating = rating;
        this.author = author;
        this.category = category;
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

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
