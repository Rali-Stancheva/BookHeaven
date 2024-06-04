package com.example.library.services.impl;

import com.example.library.models.DTOs.AuthorDTO;
import com.example.library.models.entities.Author;
import com.example.library.models.entities.Book;
import com.example.library.repositories.AuthorRepository;
import com.example.library.repositories.BookRepository;
import com.example.library.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void addAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public boolean existsByName(String name) {
        return authorRepository.existsByName(name);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        Optional<Author> authorOptional = authorRepository.findById(id);

        if (authorOptional.isPresent()){
            Author author = authorOptional.get();
            return convertToDto(author);
        }else{
            throw new NoSuchElementException("Author not found with id: " + id);
        }
    }

    @Override
    public AuthorDTO convertToDto(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getBio(),
                author.getBirthdate(),
                author.getImageUrl());
    }

    @Override
    public List<Book> getRandomBooksByAuthor(Long authorId) {
        List<Book> books = bookRepository.findByAuthorId(authorId);

        Collections.shuffle(books); // Разбъркваме списъка с книги
        return books.stream().limit(3).collect(Collectors.toList());
    }
}
