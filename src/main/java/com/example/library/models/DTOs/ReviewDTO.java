package com.example.library.models.DTOs;

import com.example.library.models.entities.Book;
import com.example.library.models.entities.User;
import jakarta.persistence.*;

import java.time.LocalDate;

public class ReviewDTO {

    private Long id;

    private String comments;

    private LocalDate postDate;

    private User user;

    private Book book;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
