package com.example.library.services;

import com.example.library.models.DTOs.BookDTO;
import com.example.library.models.entities.Author;
import com.example.library.models.entities.Book;
import com.example.library.models.entities.Category;
import com.example.library.models.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    int getUserRatingForBook(Long bookId, Long userId);

    double getAverageRatingForMovie(Long bookId);

    Book convertDtoToBook(BookDTO bookDTO);

    void updateBook(Long id, String newTitle, LocalDate newPublicationDate, String newDescription, Double newRating,
                    Long newAuthorId, Long newCategoryId, String newISBN, String newLanguage, String newPublisher,
                    String newImage);

    void addBook(String title, LocalDate publicationDate, String description, Double rating, Long authorId, Long categoryId, MultipartFile file, String language, String publisher, String ISBN) throws IOException;

    void deleteBookById(Long id);

    List<BookDTO> getTopRatedBooks(int limit);

    void moveToRead(Long bookId, Long userId);

}
