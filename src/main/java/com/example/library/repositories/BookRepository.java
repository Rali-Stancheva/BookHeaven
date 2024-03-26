package com.example.library.repositories;

import com.example.library.models.DTOs.BookDTO;
import com.example.library.models.entities.Book;
import com.example.library.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAll();
    List<Book> findByTitleContainingIgnoreCase(String query);

    List<Book> findByCategory(Category category);

}
