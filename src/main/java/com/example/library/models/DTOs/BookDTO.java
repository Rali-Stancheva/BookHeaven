package com.example.library.models.DTOs;

import com.example.library.models.entities.Author;
import com.example.library.models.entities.Category;

import java.time.LocalDate;

public class BookDTO {
    private Long id;
    private String title;
    private LocalDate publicationDate;
    private String description;
    private Double rating;
    private Author author;
    private Category category;
    private String image;
    private String language;
    private String publisher;
    private String ISBN;


    public BookDTO(Long id, String title, LocalDate publicationDate, String description, Double rating, Author author, Category category, String image, String language, String publisher, String ISBN) {
        this.id = id;
        this.title = title;
        this.publicationDate = publicationDate;
        this.description = description;
        this.rating = rating;
        this.author = author;
        this.category = category;
        this.image = image;
        this.language = language;
        this.publisher = publisher;
        this.ISBN = ISBN;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}
