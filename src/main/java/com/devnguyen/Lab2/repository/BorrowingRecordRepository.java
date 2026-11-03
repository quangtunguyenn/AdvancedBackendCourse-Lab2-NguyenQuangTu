package com.devnguyen.Lab2.repository;

import com.devnguyen.Lab2.dto.BookStatisticsDTO;
import com.devnguyen.Lab2.dto.BorrowingHistoryDTO;
import com.devnguyen.Lab2.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Integer> {

    // Tối ưu hoá: Dùng JOIN và DTO Projection để tránh SELECT * và N+1 queries
    @Query("SELECT new com.devnguyen.Lab2.dto.BorrowingHistoryDTO(" +
           "r.recordId, b.title, r.borrowDate, r.returnDate, r.status) " +
           "FROM BorrowingRecord r JOIN r.book b " +
           "WHERE r.user.userId = :userId " +
           "ORDER BY r.borrowDate DESC")
    List<BorrowingHistoryDTO> findHistoryByUserIdOptimized(@Param("userId") Integer userId);

    @Query("SELECT new com.devnguyen.Lab2.dto.BookStatisticsDTO(" +
           "b.bookId, b.title, b.author, COUNT(r.recordId)) " +
           "FROM BorrowingRecord r JOIN r.book b " +
           "GROUP BY b.bookId, b.title, b.author " +
           "ORDER BY COUNT(r.recordId) DESC")
    List<BookStatisticsDTO> findMostFrequentlyBorrowedBooks(PageRequest pageable);
}