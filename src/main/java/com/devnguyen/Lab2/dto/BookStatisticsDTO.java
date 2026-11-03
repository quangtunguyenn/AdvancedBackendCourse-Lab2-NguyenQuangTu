package com.devnguyen.Lab2.dto;

public record BookStatisticsDTO(
    Integer bookId,
    String title,
    String author,
    Long borrowCount
) {}