package com.example.library.controllers;

import com.example.library.models.DTOs.NewsDTO;
import com.example.library.models.entities.News;
import com.example.library.repositories.NewsRepository;
import com.example.library.services.LikesService;
import com.example.library.services.NewsService;
import com.example.library.services.UserService;
import com.example.library.services.impl.FileStorageService;
import com.example.library.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    private final UserService userService;

    private final LikesService likesService;

    private final CurrentUser currentUser;

    private final NewsRepository newsRepository;

    private final FileStorageService fileStorageService;

    @Value("${file.upload-dir}")
    private String uploadDir;


    @Autowired
    public NewsController(NewsService newsService, UserService userService, LikesService likesService, CurrentUser currentUser, NewsRepository newsRepository, FileStorageService fileStorageService) {
        this.newsService = newsService;
        this.userService = userService;
        this.likesService = likesService;
        this.currentUser = currentUser;
        this.newsRepository = newsRepository;
        this.fileStorageService = fileStorageService;
    }


    @GetMapping("/allNews")
    public String getNews(Model model) {
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
    public String addNews(@RequestParam("title") String title,
                          @RequestParam("shortDescription") String shortDescription,
                          @RequestParam("content") String content,
                          @RequestParam("date") LocalDate date,
                          @RequestParam("image") MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        Path copyLocation = Paths.get(uploadDir + File.separator + fileName);
        Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);


        News news = new News();
        news.setTitle(title);
        news.setShortDescription(shortDescription);
        news.setContent(content);
        news.setDate(date);
        news.setImage(fileName);

        newsRepository.save(news);

        return "redirect:/news/add-form";
    }




//    @PostMapping("/edit")
//    public String updateNews(@ModelAttribute News news, @RequestParam("file") MultipartFile file) throws IOException {
//        Long newsId = news.getId();
//
//        NewsDTO newsDTO = newsService.getNewsById(newsId);
//        News existingNews = newsService.convertDtoToNews(newsDTO);
//
//        existingNews.setTitle(news.getTitle());
//        existingNews.setContent(news.getContent());
//        existingNews.setDate(news.getDate());
//        existingNews.setShortDescription(news.getShortDescription());
//
//        // Проверка дали е получен файл
//        if (!file.isEmpty()) {
//
//            String oldFileName = existingNews.getImage();
//            if (oldFileName != null && !oldFileName.isEmpty()) {
//                fileStorageService.deleteFile(oldFileName);
//            }
//
//            String fileName = file.getOriginalFilename();
//            Path copyLocation = Paths.get(uploadDir + File.separator + fileName);
//            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
//
//            existingNews.setImage(fileName);
//        }
//
//        newsService.updateNews(existingNews);
//
//        return "redirect:/news/" + newsId;
//    }




}


