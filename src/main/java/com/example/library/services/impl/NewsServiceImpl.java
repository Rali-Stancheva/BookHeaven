package com.example.library.services.impl;

import com.example.library.models.DTOs.NewsDTO;
import com.example.library.models.entities.News;
import com.example.library.models.entities.User;
import com.example.library.repositories.LikesRepository;
import com.example.library.repositories.NewsRepository;
import com.example.library.repositories.UserRepository;
import com.example.library.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;



    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, UserRepository userRepository, LikesRepository likesRepository) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.likesRepository = likesRepository;
    }


    @Override
    public List<NewsDTO> getNews() {
        return this.newsRepository
                .findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public NewsDTO convertToDTO(News news) {
        return new NewsDTO(
                news.getId(),
                news.getTitle(),
                news.getShortDescription(),
                news.getContent(),
                news.getDate(),
                news.getImageUrl()
        );
    }

    @Override
    public void addNews(News news) {
        newsRepository.save(news);
    }

    @Override
    public NewsDTO getNewsById(Long id) {
        Optional<News> newsOptional = newsRepository.findById(id);

        if (newsOptional.isPresent()) {
            News news = newsOptional.get();
            return convertToDto(news);
        } else {
            throw new NoSuchElementException("News not found with id: " + id);
        }
    }

    @Override
    public NewsDTO convertToDto(News news) {
        return new NewsDTO(
                news.getId(),
                news.getTitle(),
                news.getShortDescription(),
                news.getContent(),
                news.getDate(),
                news.getImageUrl());
    }



}
