package com.example.library.services;

import com.example.library.models.DTOs.BookDTO;
import com.example.library.models.entities.Book;
import com.example.library.models.entities.Category;

import java.util.List;

public interface BookService {
    List<BookDTO> getBooks();

    List<BookDTO> searchBooks(String query);

    List<BookDTO> searchBooksIgnoreCase(String query);

    BookDTO getBookById(Long id);

    BookDTO convertToDto(Book book);

    void addToReadList(Long bookId, Long userId);

    void addToForReadingList(Long bookId, Long userId);

    List<Book> getAllReadBooksForUser(Long userId);

    List<Book> getAllForReadingBooksForUser(Long userId);

    void removeFromReadList(Long bookId, Long userId);

    void removeFromForReadingList(Long bookId, Long userId);

    List<BookDTO> getBooksByCategoryName(String category);
}
