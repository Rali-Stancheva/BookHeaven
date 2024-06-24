package com.example.library.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "AuthorImage")
public class AuthorImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public AuthorImage() {}

    public AuthorImage(String image, Author author) {
        this.image = image;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}