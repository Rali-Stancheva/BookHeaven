package com.example.library.repositories;

import com.example.library.models.entities.Lists;
import com.example.library.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListsRepository extends JpaRepository<Lists, Long> {
    List<Lists> findByUser(User user);
}
