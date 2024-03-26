package com.example.library.controllers;

import com.example.library.models.DTOs.BookDTO;
import com.example.library.models.entities.Book;
import com.example.library.models.entities.Category;
import com.example.library.services.BookService;
import com.example.library.util.CurrentUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private CurrentUser currentUser;
    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/allBooks")
    public String getBooks(Model model){
        List<BookDTO> allBooks = bookService.getBooks();
        model.addAttribute("allBooks",allBooks);
        return "books";
    }

    @GetMapping("/{id}")
    public String getBookDetails(Model model, @PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "book-details";
    }

    @GetMapping("/by-category/{categoryName}")
    public String getBooksByCategory(@PathVariable String categoryName, Model model){
        List<BookDTO> booksByCategory = bookService.getBooksByCategoryName(categoryName);
        model.addAttribute("book",booksByCategory);
        model.addAttribute("categoryName", categoryName);
        return "books-by-category";
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
            // Perform the search only if the query is not empty
            searchResults = bookService.searchBooksIgnoreCase(query);
        }

        if (searchResults.isEmpty()) {
            // Return a view indicating that no search results have been found
            return "noSuchBook";
        } else {
            // Return the search results
            model.addAttribute("searchResults", searchResults);
            return "searchResults";
        }
    }


    @PostMapping("/add-to-read/{userId}/{bookId}")
    public String addToReadList(@PathVariable Long userId, @PathVariable Long bookId,RedirectAttributes redirectAttributes) {
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


}
