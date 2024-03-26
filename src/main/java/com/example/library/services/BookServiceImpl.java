package com.example.library.services;

import com.example.library.models.DTOs.BookDTO;
import com.example.library.models.entities.Book;
import com.example.library.models.entities.Category;
import com.example.library.models.entities.ForReading;
import com.example.library.models.entities.Readed;
import com.example.library.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final ReadRepository readRepository;

    private final ForReadingRepository forReadingRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, UserRepository userRepository, ReadRepository readRepository, ForReadingRepository forReadingRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.readRepository = readRepository;
        this.forReadingRepository = forReadingRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<BookDTO> getBooks() {

        return this.bookRepository
                .findAll()
                .stream()
                .map(this::convertToDto)
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
                      book.getPublication_date(),
                      book.getDescription(),
                      book.getRating(),
                      book.getAuthor(),
                      book.getCategory(),
                      book.getImageUrl()
              )).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> searchBooksIgnoreCase(String query) {
        return this.searchBooks(query).stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(query))
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long id) {
        Optional<Book>bookOptional = bookRepository.findById(id);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            return convertToDto(book);
        } else {
            throw new NoSuchElementException("Book not found with id: " + id);
        }
    }

    @Override
    public BookDTO convertToDto(Book book){
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getPublication_date(),
                book.getDescription(),
                book.getRating(),
                book.getAuthor(),
                book.getCategory(),
                book.getImageUrl());
    }

    @Override
    public void addToReadList(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book with id=" + bookId + " was not found!"));


        boolean alreadyExists = readRepository.existsByBookIdAndUserId(bookId, userId);
        if (alreadyExists) {
            throw new IllegalStateException("The book has been already added.");
        }


        Readed readed = new Readed();
        readed.setBook(book);

        readed.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id=" + userId + " was not found!")));

        readRepository.save(readed);
    }

    @Override
    public void addToForReadingList(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book with id=" + bookId + " was not found!"));

        boolean alreadyExists = forReadingRepository.existsByBookIdAndUserId(bookId, userId);
        if (alreadyExists) {
            throw new IllegalStateException("The book has been already added.");
        }

        ForReading forReading = new ForReading();
        forReading.setBook(book);

        forReading.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id=" + userId + " was not found!")));

        forReadingRepository.save(forReading);
    }

    @Override
    public List<Book> getAllReadBooksForUser(Long userId) {
        List<Readed> reads = readRepository.findAllByUserId(userId);

        List<Book> readBooks = new ArrayList<>();

        for (Readed read : reads) {
            readBooks.add(read.getBook());
        }
        return readBooks;

    }

    @Override
    public List<Book> getAllForReadingBooksForUser(Long userId) {
        List<ForReading> forReadings = forReadingRepository.findAllByUserId(userId);

        List<Book> reading = new ArrayList<>();

        for (ForReading r : forReadings){
            reading.add(r.getBook());
        }

        return reading;
    }

    @Transactional
    @Override
    public void removeFromReadList(Long bookId, Long userId) {
        readRepository.deleteByBookIdAndUserId(bookId, userId);
    }

    @Transactional
    @Override
    public void removeFromForReadingList(Long bookId, Long userId) {
        forReadingRepository.deleteByBookIdAndUserId(bookId, userId);
    }

    @Override
    public List<BookDTO> getBooksByCategoryName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        return bookRepository
                .findByCategory(category)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

}
