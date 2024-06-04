package com.example.library.services;

import com.example.library.models.DTOs.AuthorDTO;
import com.example.library.models.DTOs.BookDTO;
import com.example.library.models.entities.Author;
import com.example.library.models.entities.Book;

import java.util.List;

public interface AuthorService {

    void addAuthor(Author author);

     boolean existsByName(String name);

    List<Author> findAll();

    AuthorDTO getAuthorById(Long id);

    AuthorDTO convertToDto(Author author);

    List<Book> getRandomBooksByAuthor(Long authorId);
}
