package com.example.library.repositories;

import com.example.library.models.DTOs.BookDTO;
import com.example.library.models.entities.Book;
import com.example.library.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAll();

    List<Book> findByTitleContainingIgnoreCaseOrAuthorNameContainingIgnoreCaseOrCategoryNameContainingIgnoreCase(String title, String authorName, String categoryName);

    List<Book> findTop10ByOrderByRatingDesc();

    List<Book> findByCategory(Category category);




}
