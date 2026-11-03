package com.devnguyen.Lab2.dto;

import java.time.LocalDateTime;

public record BorrowingHistoryDTO(
    Integer recordId,
    String bookTitle,
    LocalDateTime borrowDate,
    LocalDateTime returnDate,
    String status
) {}