package com.example.library.services.impl;

import com.example.library.models.DTOs.BookDTO;
import com.example.library.models.entities.*;
import com.example.library.repositories.*;
import com.example.library.services.BookService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final ReadRepository readRepository;

    private final ForReadingRepository forReadingRepository;

    private final CategoryRepository categoryRepository;

    private final RatingFromUserRepository ratingFromUserRepository;

    private final AuthorRepository authorRepository;

    private final ReviewRepository reviewRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, UserRepository userRepository, ReadRepository readRepository, ForReadingRepository forReadingRepository, CategoryRepository categoryRepository, RatingFromUserRepository ratingFromUserRepository, AuthorRepository authorRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.readRepository = readRepository;
        this.forReadingRepository = forReadingRepository;
        this.categoryRepository = categoryRepository;
        this.ratingFromUserRepository = ratingFromUserRepository;
        this.authorRepository = authorRepository;
        this.reviewRepository = reviewRepository;
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
    public List<BookDTO> searchBooksByTitleOrAuthorOrCategory(String query) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCaseOrAuthorNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(query, query, query);
        return books.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    @Override
    public List<BookDTO> searchBooksIgnoreCase(String query) {
        return this.searchBooksByTitleOrAuthorOrCategory(query).stream()
                .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }


    @Override
    public BookDTO getBookById(Long id){
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
                book.getImageUrl(),
                book.getLanguage(),
                book.getPublisher(),
                book.getISBN());
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


    @Override
    public void rateBook(Long bookId, int rating, User user) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + bookId));

        RatingFromUser existingRating = ratingFromUserRepository.findByBookAndUser(book, user);

        if (existingRating != null) {
            existingRating.setRating(rating);
            ratingFromUserRepository.save(existingRating);
        } else {
            RatingFromUser newRating = new RatingFromUser();
            newRating.setBook(book);
            newRating.setUser(user);
            newRating.setRating(rating);
            ratingFromUserRepository.save(newRating);
        }
    }


    @Override
    public void removeRating(Long bookId, User user) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + bookId));

        RatingFromUser ratingFromUser = ratingFromUserRepository.findByBookAndUser(book, user);

        if (ratingFromUser != null) {
            ratingFromUserRepository.delete(ratingFromUser);
        } else {
            throw new IllegalArgumentException("Rating not found for bookId: " + bookId + " and userId: " + user.getId());
        }
    }


    @Override
    public int getUserRatingForBook(Long bookId, Long userId) {
        RatingFromUser rating = ratingFromUserRepository.findByBookIdAndUserId(bookId, userId);
        return rating != null ? rating.getRating() : 0;
    }


    @Override
    public double getAverageRatingForMovie(Long bookId) {
        List<RatingFromUser> ratings = ratingFromUserRepository.findByBookId(bookId);
        if (ratings.isEmpty()) {
            return 0.0;
        }

        int totalRating = 0;
        for (RatingFromUser rating : ratings) {
            totalRating += rating.getRating();
        }
        return  totalRating * 1.0 / ratings.size();
    }


    @Override
    public void addBook(String title, LocalDate publicationDate, String description, Double rating, Long authorId, Long categoryId, String imageUrl) {
        Book book = new Book();
        book.setTitle(title);
        book.setPublication_date(publicationDate);
        book.setDescription(description);
        book.setRating(rating);
        book.setImageUrl(imageUrl);

        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new NoSuchElementException("Author not found with id: " + authorId));
        book.setAuthor(author);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + categoryId));
        book.setCategory(category);

        bookRepository.save(book);
    }


    @Override
    @Transactional
    public void deleteBookById(Long id) {
        reviewRepository.deleteByBookId(id);
        ratingFromUserRepository.deleteByBookId(id);
        readRepository.deleteByBookId(id);
        forReadingRepository.deleteByBookId(id);

        bookRepository.deleteById(id);
    }


    @Override
    public List<BookDTO> getTopRatedBooks(int limit) {
        List<Book> topRatedBooks = bookRepository.findTop10ByOrderByRatingDesc();


        List<BookDTO> topRatedBookDTOs = new ArrayList<>();

        for (Book book : topRatedBooks) {
            BookDTO bookDTO = convertToDto(book);
            topRatedBookDTOs.add(bookDTO);
        }

        return topRatedBookDTOs;
    }



    @Override
    public void moveToRead(Long bookId, Long userId) {

        Optional<Readed> existingReadedBook = readRepository.findByBookIdAndUserId(bookId, userId);

        if (existingReadedBook.isPresent()) {
            readRepository.delete(existingReadedBook.get());
        }

        ForReading forReading = forReadingRepository.findByBookIdAndUserId(bookId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found in 'for reading' list"));

        Readed readed = new Readed();
        readed.setBook(forReading.getBook());
        readed.setUser(forReading.getUser());

        readRepository.save(readed);

        forReadingRepository.delete(forReading);
    }



    @Override
    public Book convertDtoToBook(BookDTO bookDTO) {
        Book book = new Book();

        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setPublication_date(bookDTO.getPublication_date());
        book.setDescription(bookDTO.getDescription());
        book.setRating(bookDTO.getRating());
        book.setAuthor(bookDTO.getAuthor());
        book.setCategory(bookDTO.getCategory());
        book.setImageUrl(bookDTO.getImageUrl());


        return book;
    }


    @Override
    public void updateBook(Long id, String newTitle,Double newRating, String newDescription, Long newAuthorId) {
        Book book = bookRepository.findById(id).orElse(null);

        if (book != null){
            book.setTitle(newTitle);
            book.setRating(newRating);
            book.setDescription(newDescription);

            if (newAuthorId != null) {
                Author author = authorRepository.findById(newAuthorId).orElse(null);
                if (author != null) {
                    book.setAuthor(author);
                }
            }

            bookRepository.save(book);
        }
    }


}
