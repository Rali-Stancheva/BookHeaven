package com.example.library.controllers;

import com.example.library.models.DTOs.AuthorDTO;
import com.example.library.models.entities.Author;
import com.example.library.models.entities.Book;
import com.example.library.models.entities.Category;
import com.example.library.services.AuthorService;
import com.example.library.services.CategoryService;
import com.example.library.services.impl.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final FileStorageService fileStorageService;

    @Value("${file.upload-dir}")
    private String uploadDir;


    @Autowired
    public AuthorController(AuthorService authorService, CategoryService categoryService, FileStorageService fileStorageService) {
        this.authorService = authorService;
        this.categoryService = categoryService;

        this.fileStorageService = fileStorageService;
    }



    @GetMapping("/allAuthors")
    public String getAuthors(Model model) {
        List<Author> allAuthors = authorService.findAll();
        model.addAttribute("allAuthors", allAuthors);

        List<Category> allCategories = categoryService.findAll();
        model.addAttribute("allCategories", allCategories);

        return "add-books";
    }

    @GetMapping("/allAuthorsInfo")
    public String getAuthorsInfo(Model model) {
        List<AuthorDTO> allAuthorsInfo = authorService.getAuthors();
        model.addAttribute("allAuthorsInfo", allAuthorsInfo);


        return "authors";
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
    public String addAuthor(@RequestParam("name") String name,
                          @RequestParam("bio") String bio,
                          @RequestParam("birthdate") LocalDate birthdate,
                          @RequestParam("image") MultipartFile file) throws IOException {

        authorService.addAuthor(name,  bio, birthdate,  file);

        return "redirect:/authors/add-form";
    }


//    @PostMapping("/edit/{id}")
//    public String updateAuthor(@PathVariable Long id, @ModelAttribute AuthorDTO authorDTO) {
//
//        authorService.updateAuthor(id, authorDTO.getName(), authorDTO.getBio(), authorDTO.getBirthdate());
//        return "redirect:/authors/allAuthorsInfo";
//    }

    @PostMapping("/edit/{id}")
    public String updateAuthor(@ModelAttribute Author updatedAuthor, @RequestParam("file") MultipartFile file) throws IOException {
        Long authorId = updatedAuthor.getId();


        AuthorDTO authorDTO = authorService.getAuthorById(authorId);
        Author existingAuthor = authorService.convertDtoToAuthor(authorDTO);

        existingAuthor.setName(updatedAuthor.getName());
        existingAuthor.setBio(updatedAuthor.getBio());
        existingAuthor.setBirthdate(updatedAuthor.getBirthdate());
        existingAuthor.setImage(updatedAuthor.getImage());

        if (!file.isEmpty()) {

            String oldFileName = existingAuthor.getImage();
            if (oldFileName != null && !oldFileName.isEmpty()) {
                fileStorageService.deleteFile(oldFileName);
            }

            String fileName = file.getOriginalFilename();
            Path copyLocation = Paths.get(uploadDir + File.separator + fileName);
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

            existingAuthor.setImage(fileName);
        }

        authorService.updateAuthor(existingAuthor);

        return "redirect:/authors/" + authorId;
    }

    @PostMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthorById(id);
        return "redirect:/authors/allAuthorsInfo";
    }
}
