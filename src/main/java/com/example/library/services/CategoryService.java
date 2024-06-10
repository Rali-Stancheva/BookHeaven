package com.example.library.services;

import com.example.library.models.DTOs.BookDTO;
import com.example.library.models.DTOs.CategoryDTO;
import com.example.library.models.entities.Author;
import com.example.library.models.entities.Book;
import com.example.library.models.entities.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    boolean existsByName(String name);

    void addCategory(Category category);

    List<CategoryDTO> getCategories();

    CategoryDTO convertToDto(Category category);

    void updateCategory(Long id, String newName);
}
