package com.example.library.controllers;

import com.example.library.models.DTOs.AuthorDTO;
import com.example.library.models.DTOs.BookDTO;
import com.example.library.models.DTOs.CategoryDTO;
import com.example.library.models.entities.Author;
import com.example.library.models.entities.Category;
import com.example.library.services.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/allCategories")
    public String getCategories(Model model, HttpServletResponse response){
        // Set cache control headers to disable caching
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        List<CategoryDTO> allCategories = categoryService.getCategories();
        model.addAttribute("allCategories",allCategories);
        return "categories";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute CategoryDTO categoryDTO) {

        categoryService.updateCategory(id, categoryDTO.getName());
        return "redirect:/category/allCategories";
    }

    @GetMapping("/add-form")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Author());
        return "add-category";
    }


    @PostMapping("/add")
    public String addCategory(@ModelAttribute CategoryDTO categoryDTO, Model model) {
        String name = categoryDTO.getName();

        Category category = new Category(name);

        if (!categoryService.existsByName(name)){
            categoryService.addCategory(category);
            model.addAttribute("successMessage", "The category has been successfully added!");
        }else {
            model.addAttribute("errorMessage", "The category already exist!");
        }



        return "add-category";
    }

}
