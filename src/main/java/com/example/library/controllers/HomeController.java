package com.example.library.controllers;

        ;
        import com.example.library.models.DTOs.BookDTO;
        import com.example.library.models.DTOs.NewsDTO;
        import com.example.library.services.BookService;
        import com.example.library.services.NewsService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RequestMapping;

        import java.util.Comparator;
        import java.util.List;
        import java.util.stream.Collectors;

@Controller
@RequestMapping
public class HomeController {
    private final BookService bookService;

    private final NewsService newsService;

    @Autowired
    public HomeController(BookService bookService, NewsService newsService) {
        this.bookService = bookService;
        this.newsService = newsService;
    }


    @GetMapping("/")
    public String getLastPublishedBooksAndNews(Model model) {
        List<BookDTO> latestBooks = bookService.getBooks().stream()
                .sorted(Comparator.comparing(BookDTO::getPublicationDate).reversed())
                .limit(6)
                .collect(Collectors.toList());

        model.addAttribute("lastPublishedBooks", latestBooks);



        List<NewsDTO> latestNews = newsService.getNews().stream()
                .sorted(Comparator.comparing(NewsDTO::getDate).reversed())
                .limit(4)
                .collect(Collectors.toList());

        model.addAttribute("lastPublishedNews", latestNews);

        return "index";

    }


}

