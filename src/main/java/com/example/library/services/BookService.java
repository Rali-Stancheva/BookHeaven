package com.example.library.services;

import com.example.library.models.DTOs.BookDTO;
import com.example.library.models.entities.Author;
import com.example.library.models.entities.Book;
import com.example.library.models.entities.Category;
import com.example.library.models.entities.User;

import java.time.LocalDate;
import java.util.List;

public interface BookService {
    List<BookDTO> getBooks();

   // List<BookDTO> searchBooks(String query);

    List<BookDTO> searchBooksByTitleOrAuthorOrCategory(String query);

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

    void rateBook(Long bookId, int rating, User user);

    void removeRating(Long bookId, User user);

    int getUserRatingForMovie(Long bookId, Long userId);

    double getAverageRatingForMovie(Long bookId);

    Book convertDtoToBook(BookDTO bookDTO);

    void updateBook(Long id, String newTitle, Double newRating, String newDescription, Long newAuthorId);

    void addBook(String title, LocalDate publicationDate, String description, Double rating, Long authorId, Long categoryId, String imageUrl);

    void deleteBookById(Long id);

    List<BookDTO> getTopRatedBooks(int limit);


}
