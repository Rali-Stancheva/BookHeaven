package com.example.library.controllers;

import com.example.library.models.DTOs.BookDTO;
import com.example.library.models.DTOs.ReviewDTO;
import com.example.library.models.entities.*;
import com.example.library.services.*;
import com.example.library.services.impl.FileStorageService;
import com.example.library.util.CurrentUser;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private CurrentUser currentUser;
    private final BookService bookService;
    private final UserService userService;
    private final ReviewService reviewService;
    private final AuthorService authorService;
    private final FileStorageService fileStorageService;
    private final CategoryService categoryService;


    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    public BooksController(BookService bookService, UserService userService, ReviewService reviewService, AuthorService authorService, FileStorageService fileStorageService, CategoryService categoryService) {
        this.bookService = bookService;
        this.userService = userService;
        this.reviewService = reviewService;
        this.authorService = authorService;
        this.fileStorageService = fileStorageService;
        this.categoryService = categoryService;
    }

//    @GetMapping("/allBooks")
//    public String getBooks(Model model){
//        List<BookDTO> allBooks = bookService.getBooks();
//        model.addAttribute("allBooks",allBooks);
//        return "books";
//    }

    @GetMapping("/allBooks")
    public String getBooks(Model model, HttpServletResponse response) {
        // Set cache control headers to disable caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        List<BookDTO> allBooks = bookService.getBooks();
        model.addAttribute("allBooks", allBooks);
        return "books";
    }


    @GetMapping("/{id}")
    public String getBookDetails(Model model, @PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        model.addAttribute("book", book);

        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);

        List<Category> allCategories = categoryService.findAll();
        model.addAttribute("allCategories", allCategories);

        CurrentUser currentUser = userService.getCurrentUser();
        int userRating = bookService.getUserRatingForBook(id, currentUser.getId());
        model.addAttribute("userRating", userRating);
        model.addAttribute("currentUser", currentUser);

        double averageRating = bookService.getAverageRatingForMovie(id);
        model.addAttribute("averageRating", averageRating);

        List<ReviewDTO> commentsForBook = reviewService.getCommentsForBook(id);
        model.addAttribute("commentsForBook", commentsForBook);


        return "book-details";
    }


    @GetMapping("/by-category/{categoryName}")
    public String getBooksByCategory(@PathVariable String categoryName, Model model) {
        List<BookDTO> booksByCategory = bookService.getBooksByCategoryName(categoryName);

        model.addAttribute("book", booksByCategory);
        model.addAttribute("categoryName", categoryName);
        return "books-by-category";
    }


    @GetMapping("/top-rated")
    public String getTopRatedBooks(Model model) {
        List<BookDTO> topRatedBooks = bookService.getTopRatedBooks(10);
        model.addAttribute("topRatedBooks", topRatedBooks);

        return "top-rated-books";
    }


    @GetMapping("/read-books")
    public String getReadBooks(Model model) {

        List<Book> readBooks = bookService.getAllReadBooksForUser(currentUser.getId());

        model.addAttribute("readBooks", readBooks);
        return "read-books";
    }


    @GetMapping("/for-reading")
    public String getForReadingBooks(Model model) {

        List<Book> forReading = bookService.getAllForReadingBooksForUser(currentUser.getId());

        model.addAttribute("forReading", forReading);
        return "for-reading";
    }


    @GetMapping("/search")
    public String searchBooks(@RequestParam(value = "query", required = false) String query, Model model) {
        List<BookDTO> searchResults = new ArrayList<>();

        if (query != null && !query.trim().isEmpty()) {
            searchResults = bookService.searchBooksByTitleOrAuthorOrCategory(query);
        }

        if (searchResults.isEmpty()) {
            return "noSuchBook";
        } else {
            model.addAttribute("searchResults", searchResults);
            return "searchResults";
        }
    }


    @PostMapping("/add-to-read/{userId}/{bookId}")
    public String addToReadList(@PathVariable Long userId, @PathVariable Long bookId, RedirectAttributes redirectAttributes) {
        try {
            bookService.addToReadList(bookId, userId);
            redirectAttributes.addFlashAttribute("successMessage", "Success! Book added to your collection.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error! The book has been already added.");
        }

        return "redirect:/books/" + bookId;
    }


    @PostMapping("/add-to-for-reading/{userId}/{bookId}")
    public String addToForReadingList(@PathVariable Long userId, @PathVariable Long bookId, RedirectAttributes redirectAttributes) {
        try {
            bookService.addToForReadingList(bookId, userId);
            redirectAttributes.addFlashAttribute("successMessage", "Success! Book added to your collection.");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error! The book has been already added.");
        }

        return "redirect:/books/" + bookId;
    }


    @PostMapping("/remove-from-read/{bookId}/{userId}")
    public String removeFromReadList(@PathVariable Long bookId, @PathVariable Long userId, RedirectAttributes redirectAttributes) {
        bookService.removeFromReadList(bookId, userId);
        redirectAttributes.addFlashAttribute("successMessage", "Success! Book removed from your collection.");
        return "redirect:/books/read-books";
    }


    @PostMapping("/remove-for-reading/{bookId}/{userId}")
    public String removeFromForReadingList(@PathVariable Long bookId, @PathVariable Long userId, Model model) {
        bookService.removeFromForReadingList(bookId, userId);
        model.addAttribute("successMessage", "Success! Book removed from your collection.");
        return "redirect:/books/for-reading";
    }


    @PostMapping("/rate/{id}")
    public String rateBook(@PathVariable Long id, @RequestParam int rating) {
        CurrentUser currentUser = userService.getCurrentUser();
        User user = new User();
        user.setId(currentUser.getId());

        bookService.rateBook(id, rating, user);

        return "redirect:/books/" + id;
    }


    @PostMapping("/delete-rating/{id}")
    public String deleteRating(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        CurrentUser currentUser = userService.getCurrentUser();
        User user = new User();
        user.setId(currentUser.getId());


        try {
            bookService.removeRating(id, user);
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Rating not found for this book.");
        }

        return "redirect:/books/" + id;
    }


    @PostMapping(value = "comments/{reviewId}")
    public String addCommentToBook(@PathVariable Long reviewId, @RequestParam("comment") String reviewText) {
        CurrentUser currentUser = userService.getCurrentUser();

        Review review = new Review();
        review.setComments(reviewText);
        review.setPostDate(LocalDate.now());

        if (currentUser.isLogged()) {
            User user = new User();
            user.setId(currentUser.getId());

            reviewService.addCommentToBooks(reviewId, review, user);
        } else {
            return "redirect:/users/login";
        }

        return "redirect:/books/" + reviewId;
    }



    @PostMapping("/delete-comment/{commentId}")
    public String deleteReviewById(@PathVariable Long commentId) {
        Review review = reviewService.getReviewById(commentId);

        if (review.getUser().getId().equals(currentUser.getId())) {
            reviewService.deleteReviewById(commentId);
        }

        return "redirect:/books/" + review.getBook().getId();
    }


    @GetMapping("/add-form")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "add-books";
    }


    @PostMapping("/add")
    public String addBooks(@RequestParam("title") String title,
                           @RequestParam("publicationDate") LocalDate publicationDate,
                           @RequestParam("description") String description,
                           @RequestParam("rating") Double rating,
                           @RequestParam("authorId") Long authorId,
                           @RequestParam("categoryId") Long categoryId,
                           @RequestParam("image") MultipartFile file,
                           @RequestParam("language") String language,
                           @RequestParam("publisher") String publisher,
                           @RequestParam("ISBN") String ISBN) throws IOException {


        bookService.addBook(title, publicationDate, description, rating, authorId, categoryId, file, language, publisher, ISBN);

        return "redirect:/books/add-form";
    }



    @PostMapping("/edit/{id}")
    public String updateBook(@ModelAttribute Book updatedBook,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("authorId") Long authorId,
                             @RequestParam("categoryId") Long categoryId) throws IOException {

        Long bookId = updatedBook.getId();

        BookDTO bookDTO = bookService.getBookById(bookId);
        Book existingBook = bookService.convertDtoToBook(bookDTO);

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setPublicationDate(updatedBook.getPublicationDate());
        existingBook.setDescription(updatedBook.getDescription());
        existingBook.setRating(updatedBook.getRating());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setCategory(updatedBook.getCategory());
        existingBook.setISBN(updatedBook.getISBN());
        existingBook.setLanguage(updatedBook.getLanguage());
        existingBook.setPublisher(updatedBook.getPublisher());

        if (!file.isEmpty()) {
            String oldFileName = existingBook.getImage();
            if (oldFileName != null && !oldFileName.isEmpty()) {
                fileStorageService.deleteFile(oldFileName);
            }

            String fileName = file.getOriginalFilename();
            Path copyLocation = Paths.get(uploadDir + File.separator + fileName);
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

            existingBook.setImage(fileName);
        }

        bookService.updateBook(existingBook.getId(),
                existingBook.getTitle(),
                existingBook.getPublicationDate(),
                existingBook.getDescription(),
                existingBook.getRating(),
                authorId,
                categoryId,
                existingBook.getISBN(),
                existingBook.getLanguage(),
                existingBook.getPublisher(),
                existingBook.getImage());

        return "redirect:/books/" + bookId;
    }


    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return "redirect:/books/allBooks";
    }


    @PostMapping("/move-to-read/{bookId}")
    public String moveToReadList(@PathVariable Long bookId, RedirectAttributes redirectAttributes) {
        Long userId = currentUser.getId();

        try {
            bookService.moveToRead(bookId, userId);
            redirectAttributes.addFlashAttribute("successMessage", "The book was moved to the read list.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error moving the book.");
        }

        return "redirect:/books/read-books";
    }

}
