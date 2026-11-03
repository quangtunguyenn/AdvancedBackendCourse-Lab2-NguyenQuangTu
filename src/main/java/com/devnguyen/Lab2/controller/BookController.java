package com.devnguyen.Lab2.controller;

import com.devnguyen.Lab2.dto.BookSearchDTO;
import com.devnguyen.Lab2.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/search-by-title")
    public List<BookSearchDTO> searchBooksByTitle(@RequestParam String title) {
        return bookService.findBooksByTitle(title);
    }

    @GetMapping("/search-by-author")
    public List<BookSearchDTO> searchBooksByAuthor(@RequestParam String author) {
        return bookService.findBooksByAuthor(author);
    }
}