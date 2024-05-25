package com.example.library.controllers;

import com.example.library.models.DTOs.AuthorDTO;
import com.example.library.models.DTOs.CategoryDTO;
import com.example.library.models.entities.Author;
import com.example.library.models.entities.Category;
import com.example.library.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
