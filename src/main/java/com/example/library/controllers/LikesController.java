package com.example.library.controllers;

import com.example.library.models.entities.News;
import com.example.library.services.LikesService;
import com.example.library.services.NewsService;
import com.example.library.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/likes")
public class LikesController {

    private final LikesService likesService;
    private final CurrentUser currentUser;

    private final NewsService newsService;


    @Autowired
    public LikesController(LikesService likesService, CurrentUser currentUser, NewsService newsService) {
        this.likesService = likesService;
        this.currentUser = currentUser;
        this.newsService = newsService;
    }

    @PostMapping("/like")
    public String likeNews(@RequestParam Long newsId, Model model) {
        Long userId = currentUser.getId(); // Получаване на текущия потребител
        boolean success = likesService.likeNews(newsId, userId);

        if (success) {
            model.addAttribute("message", "News liked successfully.");
        } else {
            model.addAttribute("message", "You have already liked this news or you are not logged in.");
        }

        return "redirect:/news/" + newsId; // Пренасочване към страницата на новината
    }


    @GetMapping("/news/{newsId}/{userId}")
    public String getNews(@PathVariable Long newsId, @PathVariable Long userId, Model model) {
        boolean isLiked = likesService.isNewsLikedByUser(newsId, userId);
        model.addAttribute("isLiked", isLiked);

        return "news-details"; // Име на Thymeleaf шаблон за страницата на новината
    }



}
