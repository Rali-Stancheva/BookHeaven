package com.example.library.services.impl;

import com.example.library.models.DTOs.ReviewDTO;
import com.example.library.models.entities.Book;
import com.example.library.models.entities.Review;
import com.example.library.models.entities.User;
import com.example.library.repositories.BookRepository;
import com.example.library.repositories.ReviewRepository;
import com.example.library.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private  final BookRepository bookRepository;
    private  final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }


    @Override
    public void addCommentToBooks(Long bookId, Review review, User user) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book with id " + bookId + " not found"));

        if (review == null || review.getComments() == null || review.getComments().isEmpty()) {
            throw new IllegalArgumentException("Comment must not be empty");
        }

        review.setBook(book);
        review.setUser(user);
        review.setPostDate(LocalDate.now());

        reviewRepository.save(review);
    }

    @Override
    public List<ReviewDTO> getCommentsForBook(Long bookId) {
        List<Review> reviews = reviewRepository.findByBookId(bookId);

        List<ReviewDTO> commentDTOs = new ArrayList<>();

        for (Review review : reviews) {
            ReviewDTO reviewDTO = new ReviewDTO();

            reviewDTO.setId(review.getId());
            reviewDTO.setComments(review.getComments());
            reviewDTO.setPostDate(review.getPostDate());
            reviewDTO.setUser(review.getUser());

            commentDTOs.add(reviewDTO);
        }

        return commentDTOs;
    }

    @Override
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }
}
