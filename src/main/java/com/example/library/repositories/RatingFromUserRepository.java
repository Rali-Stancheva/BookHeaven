package com.example.library.repositories;

import com.example.library.models.entities.Book;
import com.example.library.models.entities.RatingFromUser;
import com.example.library.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingFromUserRepository extends JpaRepository<RatingFromUser, Long> {

    RatingFromUser findByBookAndUser(Book book, User user);

    RatingFromUser findByBookIdAndUserId(Long bookId, Long userId);

    List<RatingFromUser> findByBookId(Long bookId);

    void deleteByBookId(Long bookId);
}
