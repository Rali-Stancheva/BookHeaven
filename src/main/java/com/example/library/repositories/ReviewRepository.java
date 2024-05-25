package com.example.library.repositories;

import com.example.library.models.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByBookId(Long bookId);

    void deleteByBookId(Long bookId);
}
