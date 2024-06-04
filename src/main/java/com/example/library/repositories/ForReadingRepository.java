package com.example.library.repositories;

import com.example.library.models.entities.ForReading;
import com.example.library.models.entities.Readed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ForReadingRepository extends JpaRepository<ForReading, Long> {

    boolean existsByBookIdAndUserId(Long bookId, Long userId);

    List<ForReading> findAllByUserId(Long userId);

    void deleteByBookIdAndUserId(Long bookId, Long userId);

    void deleteByBookId(Long bookId);


    Optional<ForReading> findByBookIdAndUserId(Long bookId, Long userId);
    void deleteByUserIdAndBookId(Long userId, Long bookId);
}
