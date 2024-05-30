package com.example.library.services;

import com.example.library.models.entities.News;

import java.util.List;

public interface LikesService {

    boolean likeNews(Long newsId, Long userId);

    boolean isNewsLikedByUser(Long newsId,  Long userId);


}
