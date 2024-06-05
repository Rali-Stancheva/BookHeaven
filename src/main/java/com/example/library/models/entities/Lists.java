package com.example.library.models.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Lists")
public class Lists {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToMany
    @JoinTable(
            name = "books-lists",
            joinColumns = @JoinColumn(name = "lists_id"),
            inverseJoinColumns = @JoinColumn(name = "books_id")
    )
    private List<Book> books = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
