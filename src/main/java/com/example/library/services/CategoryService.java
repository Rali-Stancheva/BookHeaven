package com.example.library.services;

import com.example.library.models.entities.Author;
import com.example.library.models.entities.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    boolean existsByName(String name);

    void addCategory(Category category);
}
