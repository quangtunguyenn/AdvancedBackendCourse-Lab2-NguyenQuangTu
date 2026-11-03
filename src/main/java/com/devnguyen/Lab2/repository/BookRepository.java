package com.devnguyen.Lab2.repository;

import com.devnguyen.Lab2.dto.BookSearchDTO;
import com.devnguyen.Lab2.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    
    // Kích hoạt Pessimistic Lock (tạo lệnh SELECT ... FOR UPDATE)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Book b WHERE b.bookId = :id")
    Optional<Book> findByIdWithLock(@Param("id") Integer id);

    
@Query("SELECT new com.devnguyen.Lab2.dto.BookSearchDTO(b.bookId, b.title, b.author) " +
           "FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<BookSearchDTO> findByTitleOptimized(@Param("title") String title);

    @Query("SELECT new com.devnguyen.Lab2.dto.BookSearchDTO(b.bookId, b.title, b.author) " +
           "FROM Book b WHERE LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))")
    List<BookSearchDTO> findByAuthorOptimized(@Param("author") String author);
}