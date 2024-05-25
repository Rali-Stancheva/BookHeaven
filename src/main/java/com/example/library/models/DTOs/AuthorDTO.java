package com.example.library.models.DTOs;

import java.time.LocalDate;

public class AuthorDTO {

    private Long id;

    private String name;

    private String bio;

    private LocalDate birthdate;


    public AuthorDTO(Long id, String name, String bio, LocalDate birthdate) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.birthdate = birthdate;
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
}
