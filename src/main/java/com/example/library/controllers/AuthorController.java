package com.example.library.controllers;

import com.example.library.models.DTOs.AuthorDTO;
import com.example.library.models.entities.Author;
import com.example.library.models.entities.Book;
import com.example.library.models.entities.Category;
import com.example.library.services.AuthorService;
import com.example.library.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final CategoryService categoryService;

    @Autowired
    public AuthorController(AuthorService authorService, CategoryService categoryService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

//
//    @GetMapping("/allAuthors")
//    public String getAuthors(Model model) {
//        List<Author> allAuthors = authorService.findAll();
//        model.addAttribute("allAuthors", allAuthors);
//        return "add-books";
//    }


    @GetMapping("/allAuthors")
    public String getAuthors(Model model) {
        List<Author> allAuthors = authorService.findAll();
        model.addAttribute("allAuthors", allAuthors);

        List<Category> allCategories = categoryService.findAll();
        model.addAttribute("allCategories", allCategories);

        return "add-books";
    }


    @GetMapping("/{id}")
    public String getAuthorDetails(Model model, @PathVariable Long id){
        AuthorDTO author = authorService.getAuthorById(id);
        model.addAttribute("author", author);

        List<Book> randomBooks = authorService.getRandomBooksByAuthor(id);
        model.addAttribute("randomBooks", randomBooks);

        return "author-details";
    }


    @GetMapping("/add-form")
    public String showAddAuthorsForm(Model model) {
        model.addAttribute("authors", new Author());
        return "add-author";
    }


    @PostMapping("/add")
    public String addAuthors(@ModelAttribute AuthorDTO authorDTO, Model model) {
        String name = authorDTO.getName();
        String bio = authorDTO.getBio();
        LocalDate birthdate = authorDTO.getBirthdate();
        String imageUrl = authorDTO.getImageUrl();

        Author author = new Author(name, bio, birthdate, imageUrl);

        if (!authorService.existsByName(name)){
            authorService.addAuthor(author);
            model.addAttribute("successMessage", "The author has been successfully added!");
        }else {
            model.addAttribute("errorMessage", "The author already exist!");
        }



        return "add-author";
    }



}
