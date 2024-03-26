package com.example.library.services;

import com.example.library.models.DTOs.NewsDTO;
import com.example.library.models.entities.News;

import java.util.List;

public interface NewsService {
    List<NewsDTO> getNews();

    NewsDTO convertToDTO(News news);
}
