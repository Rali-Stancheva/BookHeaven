package com.example.library.repositories;

import com.example.library.models.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findAll();



}
