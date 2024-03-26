package com.example.library.controllers;

import com.example.library.models.DTOs.BookDTO;
import com.example.library.models.DTOs.NewsDTO;
import com.example.library.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

@GetMapping("/allNews")
    public String getNews(Model model){
        List<NewsDTO> allNews = newsService.getNews();
        model.addAttribute("allNews",allNews);
        return "news";
    }
}

