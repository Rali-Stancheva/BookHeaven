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

//    @GetMapping
//    public String getLastPublishedBooks(Model model) {
//
//        List<BookDTO> allBooks = bookService.getBooks();
//
//        List<BookDTO> latestBooks = allBooks.stream()
//                .sorted(Comparator.comparing(BookDTO::getPublication_date).reversed())
//                .limit(6)
//                .collect(Collectors.toList());
//
//        model.addAttribute("lastPublishedBooks", latestBooks);
//        return "index";
//    }


    @GetMapping("/")
    public String getLastPublishedBooksAndNews(Model model) {
        // Fetch and sort books
        List<BookDTO> latestBooks = bookService.getBooks().stream()
                .sorted(Comparator.comparing(BookDTO::getPublication_date).reversed())
                .limit(6)
                .collect(Collectors.toList());

        // Add books to the model
        model.addAttribute("lastPublishedBooks", latestBooks);

        // Fetch and sort news
        List<NewsDTO> latestNews = newsService.getNews().stream()
                .sorted(Comparator.comparing(NewsDTO::getDate).reversed())
                .limit(4)
                .collect(Collectors.toList());

        // Add news to the model
        model.addAttribute("lastPublishedNews", latestNews);

        // Return the index template
        return "index";
    }


//    @GetMapping("/latestNews")
//    public String getLastPublishedNews(Model model) {
//
//        List<NewsDTO> allNews = newsService.getNews();
//
//        List<NewsDTO> latestNews = allNews.stream()
//                .sorted(Comparator.comparing(NewsDTO::getDate).reversed())
//                .limit(6)
//                .collect(Collectors.toList());
//
//        model.addAttribute("lastPublishedNews", latestNews);
//        return "index";
//    }


}

