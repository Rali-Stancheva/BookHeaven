package com.example.library.repositories;

import com.example.library.models.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByName(String name);


}
