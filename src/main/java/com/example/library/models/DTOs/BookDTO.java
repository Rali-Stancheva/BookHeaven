package com.example.library.models.DTOs;

import com.example.library.models.entities.Author;
import com.example.library.models.entities.Category;
import com.example.library.models.entities.Lists;

import java.time.LocalDate;

public class BookDTO {
    private Long id;
    private String title;
    private LocalDate publication_date;
    private String description;
    private Double rating;
    private Author author;
    private Category category;
    private String imageUrl;



    public BookDTO(Long id, String title, LocalDate publicationDate, String description, Double rating, Author author, Category category, String imageUrl) {
        this.id = id;
        this.title = title;
        this.publication_date = publicationDate;
        this.description = description;
        this.rating = rating;
        this.author = author;
        this.category = category;
        this.imageUrl = imageUrl;

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

    public LocalDate getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(LocalDate publication_date) {
        this.publication_date = publication_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}
