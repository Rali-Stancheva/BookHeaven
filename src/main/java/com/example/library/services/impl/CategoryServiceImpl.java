package com.example.library.services.impl;

import com.example.library.models.DTOs.BookDTO;
import com.example.library.models.DTOs.CategoryDTO;
import com.example.library.models.entities.Author;
import com.example.library.models.entities.Book;
import com.example.library.models.entities.Category;
import com.example.library.repositories.CategoryRepository;
import com.example.library.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryDTO> getCategories() {
        return this.categoryRepository
                .findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO convertToDto(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName());
    }

    @Override
    public void updateCategory(Long id, String newName) {
        Category category = categoryRepository.findById(id).orElse(null);

        if (category != null){
            category.setName(newName);

            categoryRepository.save(category);
        }
    }
}
