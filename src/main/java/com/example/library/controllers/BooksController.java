package com.example.library.controllers;

import com.example.library.models.DTOs.BookDTO;
import com.example.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

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

//    @GetMapping("/search")
//    public String searchBooks(@RequestParam("query") String query, Model model){
//        List<BookDTO> searchResults = bookService.searchBooks(query);
//        model.addAttribute("searchResults", searchResults);
//
//        return "searchResults";
//    }

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



}
