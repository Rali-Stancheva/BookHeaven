package com.example.library.controllers;

import com.example.library.models.DTOs.NewsDTO;
import com.example.library.models.entities.News;
import com.example.library.services.LikesService;
import com.example.library.services.NewsService;
import com.example.library.services.UserService;
import com.example.library.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    private final UserService userService;

    private final LikesService likesService; //new

    private final CurrentUser currentUser;   //new


    @Autowired
    public NewsController(NewsService newsService, UserService userService, LikesService likesService, CurrentUser currentUser) {
        this.newsService = newsService;
        this.userService = userService;
        this.likesService = likesService;
        this.currentUser = currentUser;
    }


    @GetMapping("/allNews")
    public String getNews(Model model){
        List<NewsDTO> allNews = newsService.getNews().stream()
                .sorted(Comparator.comparing(NewsDTO::getDate).reversed())
                .collect(Collectors.toList());
        model.addAttribute("allNews", allNews);
        return "news";
    }


    @GetMapping("/{id}")
    public String getNewsDetails(Model model, @PathVariable Long id) {
        NewsDTO news = newsService.getNewsById(id);
        boolean isLiked = likesService.isNewsLikedByUser(id, currentUser.getId());


        model.addAttribute("news", news);
        model.addAttribute("newsId", id);
        model.addAttribute("isLiked", isLiked);

        return "news-details";
    }



    @GetMapping("/add-form")
    public String showAddNewsForm(Model model) {
        model.addAttribute("news", new News());
        return "add-news";
    }


    @PostMapping("/add")
    public String addNews(@ModelAttribute NewsDTO newsDTO, Model model) {
        String title = newsDTO.getTitle();
        String shortDescription = newsDTO.getShortDescription();
        String content = newsDTO.getContent();
        LocalDate date = newsDTO.getDate();
        String imageUrl = newsDTO.getImageUrl();


        News news = new News(title,shortDescription, content, date,imageUrl);
        newsService.addNews(news);

        model.addAttribute("successMessage", "The book has been successfully added!");
        return "add-news";
    }



}


