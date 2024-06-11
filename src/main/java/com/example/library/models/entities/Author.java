package com.example.library.models.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(name = "bio")
    private String bio;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "image")
    private String image;


    public Author() {

    }

    public Author(String name, String bio, LocalDate birthdate,String image) {
        this.name = name;
        this.bio = bio;
        this.birthdate = birthdate;
        this.image = image;
    }

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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
