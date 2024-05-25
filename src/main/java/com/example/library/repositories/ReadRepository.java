package com.example.library.repositories;

import com.example.library.models.entities.Book;
import com.example.library.models.entities.Readed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadRepository extends JpaRepository<Readed, Long> {
    List<Readed> findAllByUserId(Long userId);

    boolean existsByBookIdAndUserId(Long bookId, Long userId);

    void deleteByBookIdAndUserId(Long bookId, Long userId);

    void deleteByBookId(Long bookId);
}
