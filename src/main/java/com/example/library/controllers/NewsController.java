package com.example.library.controllers;

import com.example.library.models.DTOs.NewsDTO;
import com.example.library.models.entities.News;
import com.example.library.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
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

    @GetMapping("/add-form")
    public String showAddNewsForm(Model model) {
        model.addAttribute("news", new News());
        return "add-news";
    }

    @PostMapping("/add")
    public String addNews(@ModelAttribute NewsDTO newsDTO, Model model) {
        String title = newsDTO.getTitle();
        String content = newsDTO.getContent();
        LocalDate date = newsDTO.getDate();

        News news = new News(title, content, date);
        newsService.addNews(news);

        model.addAttribute("successMessage", "The book has been successfully added!");
        return "add-news";
    }
}


