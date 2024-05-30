package com.example.library.services.impl;

import com.example.library.models.entities.Likes;
import com.example.library.models.entities.News;
import com.example.library.models.entities.User;
import com.example.library.repositories.LikesRepository;
import com.example.library.repositories.NewsRepository;
import com.example.library.repositories.UserRepository;
import com.example.library.services.LikesService;
import com.example.library.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    private final CurrentUser currentUser;

    @Autowired
    public LikesServiceImpl(LikesRepository likesRepository, NewsRepository newsRepository, UserRepository userRepository, CurrentUser currentUser) {
        this.likesRepository = likesRepository;
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.currentUser = currentUser;
    }


    @Override
    public boolean likeNews(Long newsId, Long userId) {
        if (likesRepository.existsByNewsIdAndUserId(newsId, userId)) {
            return false;
        }

        News news = newsRepository.findById(newsId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);


        if (news == null || user == null) {
            return false; // Не може да бъде харесана новината
        }


        Likes like = new Likes();
        like.setNews(news);
        like.setUser(user);
        likesRepository.save(like);

        return true;
    }


    @Override
    public boolean isNewsLikedByUser(Long newsId, Long userId) {
        return likesRepository.existsByNewsIdAndUserId(newsId, userId);
    }






}
