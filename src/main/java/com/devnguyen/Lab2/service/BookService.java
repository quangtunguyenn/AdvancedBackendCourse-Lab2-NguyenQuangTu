package com.devnguyen.Lab2.service;

import org.springframework.stereotype.Service;
import com.devnguyen.Lab2.repository.BookRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.devnguyen.Lab2.dto.BookSearchDTO;
import java.util.*;

@Service
public class BookService {
    private final BookRepository bookRepository;
    @PersistenceContext // Hoặc tiêm qua Constructor
    private EntityManager entityManager;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookSearchDTO> findBooksByTitle(String title) {
        return bookRepository.findByTitleOptimized(title);
    }

    public List<BookSearchDTO> findBooksByAuthor(String author) {
        return bookRepository.findByAuthorOptimized(author);
    }
}