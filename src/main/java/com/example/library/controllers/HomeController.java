package com.example.library.controllers;

        ;
        import com.example.library.models.DTOs.BookDTO;
        import com.example.library.services.BookService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.RequestMapping;

        import java.util.Comparator;
        import java.util.List;
        import java.util.stream.Collectors;

@Controller
@RequestMapping
public class HomeController {
    private final BookService bookService;

    @Autowired
    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getLastPublishedBooks(Model model) {

        List<BookDTO> allBooks = bookService.getBooks();

        List<BookDTO> latestBooks = allBooks.stream()
                .sorted(Comparator.comparing(BookDTO::getPublication_date).reversed())
                .limit(6)
                .collect(Collectors.toList());

        model.addAttribute("lastPublishedBooks", latestBooks);
        return "index";
    }



}

