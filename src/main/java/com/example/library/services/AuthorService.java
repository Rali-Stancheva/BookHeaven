package com.example.library.services;

import com.example.library.models.entities.Author;

import java.util.List;

public interface AuthorService {

    void addAuthor(Author author);

     boolean existsByName(String name);

    List<Author> findAll();
}
