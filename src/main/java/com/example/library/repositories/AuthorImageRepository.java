package com.example.library.repositories;

import com.example.library.models.entities.Author;
import com.example.library.models.entities.AuthorImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorImageRepository extends JpaRepository<AuthorImage, Long> {
    List<AuthorImage> findByAuthor(Author author);
}
