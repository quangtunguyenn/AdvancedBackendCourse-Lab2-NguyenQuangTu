package com.devnguyen.Lab2.service;

import com.devnguyen.Lab2.dto.BookStatisticsDTO;
import com.devnguyen.Lab2.dto.BorrowingHistoryDTO;
import com.devnguyen.Lab2.entity.Book;
import com.devnguyen.Lab2.entity.BorrowingRecord;
import com.devnguyen.Lab2.entity.User; // Added missing User import
import com.devnguyen.Lab2.repository.BookRepository;
import com.devnguyen.Lab2.repository.BorrowingRecordRepository;

import jakarta.persistence.EntityManager;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BorrowingRecordService {
    
    private final BorrowingRecordRepository recordRepository;
    private final BookRepository bookRepo;
    private final EntityManager entityManager;

    // Combined and fixed constructor
    public BorrowingRecordService(BookRepository bookRepo, 
                                  BorrowingRecordRepository recordRepository, 
                                  EntityManager entityManager) {
        this.bookRepo = bookRepo;
        this.recordRepository = recordRepository;
        this.entityManager = entityManager;
    }
    
    @Transactional(rollbackFor = Exception.class)
    public void borrowBook(Integer studentId, Integer bookId) {

        // Fetch book và khóa dòng
        Book book = bookRepo.findByIdWithLock(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Kiểm tra số lượng
        if (book.getAvailableQuantity() <= 0) {
            throw new IllegalStateException("Book is currently out of stock!");
        }

        // Giảm số lượng sách đi 1
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepo.save(book);

        // Tạo bản ghi mượn sách
        // Dùng entityManager.getReference để tạo Object User từ ID mà không tốn thêm
        // câu lệnh SELECT
        User studentRef = entityManager.getReference(User.class, studentId);

        BorrowingRecord record = new BorrowingRecord();
        record.setUser(studentRef);
        record.setBook(book);
        record.setStatus("BORROWED");

        recordRepository.save(record);
    }

    public List<BorrowingHistoryDTO> getBorrowingHistoryByUserId(Integer userId) {
        return recordRepository.findHistoryByUserIdOptimized(userId);
    }

    public List<BookStatisticsDTO> getTopFrequentlyBorrowedBooks(int limit) {
        // PageRequest.of(0, limit) sẽ chuyển thành LIMIT ? OFFSET 0 trong SQL
        return recordRepository.findMostFrequentlyBorrowedBooks(PageRequest.of(0, limit));
    }
}