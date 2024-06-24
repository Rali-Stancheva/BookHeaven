package com.example.library.repositories;

import com.example.library.models.entities.Book;
import com.example.library.models.entities.Lists;
import com.example.library.models.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ListsRepository extends JpaRepository<Lists, Long> {
    List<Lists> findByUser(User user);

    void deleteByBooksId(Long bookId);

//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM books_lists WHERE books_id IN :bookIds", nativeQuery = true)
//    void deleteByBooksIdIn(@Param("bookIds") List<Long> bookIds);


}
