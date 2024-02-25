package com.example.library.services;

import com.example.library.models.DTOs.BookDTO;
import com.example.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDTO> getBooks() {

        return this.bookRepository
                .findAll()
                .stream()
                .map(book -> new BookDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getPublicationDate(),
                        book.getDescription(),
                        book.getRating(),
                        book.getAuthor(),
                        book.getCategory()))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> searchBooks(String query) {
      return this.bookRepository
              .findByTitleContainingIgnoreCase(query)
              .stream()
              .map(book -> new BookDTO(
                      book.getId(),
                      book.getTitle(),
                      book.getPublicationDate(),
                      book.getDescription(),
                      book.getRating(),
                      book.getAuthor(),
                      book.getCategory()
              )).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> searchBooksIgnoreCase(String query) {
        return this.searchBooks(query).stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(query))
                .collect(Collectors.toList());
    }
}
