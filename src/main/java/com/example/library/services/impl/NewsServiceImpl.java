package com.example.library.services.impl;

import com.example.library.models.DTOs.NewsDTO;
import com.example.library.models.entities.News;
import com.example.library.repositories.LikesRepository;
import com.example.library.repositories.NewsRepository;
import com.example.library.repositories.UserRepository;
import com.example.library.services.NewsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
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

    private final FileStorageService fileStorageService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, UserRepository userRepository, LikesRepository likesRepository, FileStorageService fileStorageService) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.likesRepository = likesRepository;
        this.fileStorageService = fileStorageService;
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
                news.getImage()
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
            return convertToDTO(news);
        } else {
            throw new NoSuchElementException("News not found with id: " + id);
        }
    }


    @Override
    public News convertDtoToNews(NewsDTO newsDTO) {
        News news = new News();

        news.setId(newsDTO.getId());
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        news.setDate(newsDTO.getDate());
        news.setShortDescription(newsDTO.getShortDescription());
        news.setImage(newsDTO.getImage());


        return news;
    }


    @Override
    public void updateNews(News updatedNews) {
        Optional<News> newsOptional = newsRepository.findById(updatedNews.getId());

        if (newsOptional.isPresent()) {
            News news = newsOptional.get();
            news.setTitle(updatedNews.getTitle());
            news.setContent(updatedNews.getContent());
            news.setDate(updatedNews.getDate());
            news.setShortDescription(updatedNews.getShortDescription());
            news.setImage(updatedNews.getImage());

            newsRepository.save(news);
        } else {
            throw new NoSuchElementException("News not found with id: " + updatedNews.getId());
        }
    }

    @Override
    @Transactional
    public void deleteNewsById(Long id) {
        News news = newsRepository.findById(id).orElseThrow(() -> new RuntimeException("News not found"));
        String photoFileName = news.getImage();

        likesRepository.deleteByNewsId(id);
        newsRepository.deleteById(id);

        if (photoFileName != null && !photoFileName.isEmpty()) {
            fileStorageService.deleteFile(photoFileName);
        }
    }


}
