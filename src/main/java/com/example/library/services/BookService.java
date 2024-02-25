package com.example.library.services;

import com.example.library.models.DTOs.BookDTO;

import java.util.List;

public interface BookService {
    List<BookDTO> getBooks();

    List<BookDTO> searchBooks(String query);
    List<BookDTO> searchBooksIgnoreCase(String query);

}
