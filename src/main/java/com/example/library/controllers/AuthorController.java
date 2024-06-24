package com.example.library.controllers;

import com.example.library.models.DTOs.AuthorDTO;
import com.example.library.models.entities.Author;
import com.example.library.models.entities.Book;
import com.example.library.models.entities.Category;
import com.example.library.services.AuthorImageService;
import com.example.library.services.AuthorService;
import com.example.library.services.CategoryService;
import com.example.library.services.impl.FileStorageService;
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
import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final FileStorageService fileStorageService;
    private final AuthorImageService authorImageService;

    @Value("${file.upload-dir}")
    private String uploadDir;


    @Autowired
    public AuthorController(AuthorService authorService, CategoryService categoryService, FileStorageService fileStorageService, AuthorImageService authorImageService) {
        this.authorService = authorService;
        this.categoryService = categoryService;

        this.fileStorageService = fileStorageService;
        this.authorImageService = authorImageService;
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


//    @PostMapping("/add") OLD AND WORK
//    public String addAuthor(@RequestParam("name") String name,
//                          @RequestParam("bio") String bio,
//                          @RequestParam("birthdate") LocalDate birthdate,
//                          @RequestParam("image") MultipartFile file) throws IOException {
//
//        authorService.addAuthor(name,  bio, birthdate,  file);
//
//        return "redirect:/authors/add-form";
//    }


    @PostMapping("/add")
    public String addAuthor(@RequestParam("name") String name,
                            @RequestParam("bio") String bio,
                            @RequestParam("birthdate") LocalDate birthdate,
                            @RequestParam("image") MultipartFile file,
                            @RequestParam("imagesList") List<MultipartFile> files) throws IOException {

        Author author = authorService.saveAuthors(name, bio, birthdate, file);
        authorImageService.saveGalleryImages(files, author);

        return "redirect:/authors/add-form";
    }







//    @PostMapping("/edit/{id}")
//    public String updateAuthor(@ModelAttribute Author updatedAuthor, @RequestParam("file") MultipartFile file) throws IOException {
//        Long authorId = updatedAuthor.getId();
//
//
//        AuthorDTO authorDTO = authorService.getAuthorById(authorId);
//        Author existingAuthor = authorService.convertDtoToAuthor(authorDTO);
//
//        existingAuthor.setName(updatedAuthor.getName());
//        existingAuthor.setBio(updatedAuthor.getBio());
//        existingAuthor.setBirthdate(updatedAuthor.getBirthdate());
//        existingAuthor.setImage(updatedAuthor.getImage());
//
//        if (!file.isEmpty()) {
//
//            String oldFileName = existingAuthor.getImage();
//            if (oldFileName != null && !oldFileName.isEmpty()) {
//                fileStorageService.deleteFile(oldFileName);
//            }
//
//            String fileName = file.getOriginalFilename();
//            Path copyLocation = Paths.get(uploadDir + File.separator + fileName);
//            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
//
//            existingAuthor.setImage(fileName);
//        }
//
//        authorService.updateAuthor(existingAuthor);
//
//        return "redirect:/authors/" + authorId;
//    }

    @PostMapping("/edit/{id}")
    public String updateAuthor(@ModelAttribute Author updatedAuthor,
                               @RequestParam("file") MultipartFile file,
                               @RequestParam("imagesList") List<MultipartFile> files,
                               RedirectAttributes redirectAttributes) throws IOException {
        Long authorId = updatedAuthor.getId();

        AuthorDTO authorDTO = authorService.getAuthorById(authorId);
        Author existingAuthor = authorService.convertDtoToAuthor(authorDTO);

        existingAuthor.setName(updatedAuthor.getName());
        existingAuthor.setBio(updatedAuthor.getBio());
        existingAuthor.setBirthdate(updatedAuthor.getBirthdate());
      //  existingAuthor.setImage(updatedAuthor.getImage());

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

        // Обновяване на галерията
        if (files != null && !files.isEmpty()) {
            authorImageService.updateGalleryImages(files, existingAuthor);
        }

        authorService.updateAuthor(existingAuthor);
        redirectAttributes.addFlashAttribute("message", "Author updated successfully.");

        return "redirect:/authors/allAuthorsInfo";
    }

//    @PostMapping("/delete/{id}")
//    public String deleteAuthor(@PathVariable Long id) {
//
//        authorService.deleteAuthorById(id);
//        return "redirect:/authors/allAuthorsInfo";
//    }

    @PostMapping("/addGalleryImages/{authorId}")
    public String addAuthorGalleryImages(@PathVariable Long authorId, @RequestParam("images") MultipartFile[] images) {
        authorImageService.addAuthorGalleryImages(authorId, images);
        return "redirect:/authors/{authorId}";
    }

    @PostMapping("/deleteGalleryImage/{authorId}/{imageId}")
    public String deleteAuthorGalleryImages(@PathVariable Long authorId, @PathVariable Long imageId) {
        authorImageService.deleteAuthorGalleryImage(authorId, imageId);
        return "redirect:/authors/{authorId}";
    }
}
