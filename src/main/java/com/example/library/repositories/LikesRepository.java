package com.example.library.repositories;

import com.example.library.models.entities.Likes;
import com.example.library.models.entities.News;
import com.example.library.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByNewsIdAndUserId(Long newsId, Long userId);

    boolean existsByNewsIdAndUserId(Long newsId, Long userId);



}