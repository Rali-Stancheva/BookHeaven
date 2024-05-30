package com.example.library.services;

import com.example.library.models.DTOs.BookDTO;
import com.example.library.models.DTOs.NewsDTO;
import com.example.library.models.entities.Book;
import com.example.library.models.entities.News;

import java.util.List;

public interface NewsService {
    List<NewsDTO> getNews();

    NewsDTO convertToDTO(News news);

    void addNews(News news);

    NewsDTO getNewsById(Long id);

    NewsDTO convertToDto(News news);

//    void updateNews(NewsDTO newsDTO);
//



}
