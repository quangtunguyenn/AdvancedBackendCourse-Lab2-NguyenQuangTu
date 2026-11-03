package com.devnguyen.Lab2.controller;

import com.devnguyen.Lab2.dto.BookStatisticsDTO;
import com.devnguyen.Lab2.dto.BorrowingHistoryDTO;
import com.devnguyen.Lab2.service.BorrowingRecordService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/borrowing-records")
public class BorrowingRecordController {

    private final BorrowingRecordService borrowingRecordService;

    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    /**
     * API thực hiện mượn sách
     * POST /api/v1/borrowing/borrow?studentId=1&bookId=3
     */
    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestParam Integer studentId, @RequestParam Integer bookId) {
        try {
            borrowingRecordService.borrowBook(studentId, bookId);
            return ResponseEntity.ok("Borrow book successfully!");
        } catch (IllegalStateException e) {
            // Lỗi hết sách hoặc trạng thái không hợp lệ
            return ResponseEntity.badRequest().body("Failed: " + e.getMessage());
        } catch (RuntimeException e) {
            // Lỗi không tìm thấy Sách / Sinh viên
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public List<BorrowingHistoryDTO> getUserBorrowingHistory(@PathVariable Integer userId) {
        return borrowingRecordService.getBorrowingHistoryByUserId(userId);
    }

    @GetMapping("/statistics/frequent-books")
    public List<BookStatisticsDTO> getFrequentBooks(
            @RequestParam(defaultValue = "10") int limit) {
        return borrowingRecordService.getTopFrequentlyBorrowedBooks(limit);
    }
}