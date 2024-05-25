package com.example.library.services;

import com.example.library.models.DTOs.ReviewDTO;
import com.example.library.models.entities.Review;
import com.example.library.models.entities.User;

import java.util.List;

public interface ReviewService {
    void addCommentToBooks(Long bookId, Review review, User user);

    List<ReviewDTO> getCommentsForBook(Long bookId);
}
