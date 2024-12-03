package com.lumios.lumiosservice.api.repositories;

import com.lumios.lumiosservice.api.models.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT t FROM Book t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(t.author) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(t.genre) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Book> findBySearchStringInTitleOrAuthorOrGenreIgnoreCaseContaining(String searchString, Pageable pageable);

    @Query(value = "SELECT * FROM Book t " +
            "ORDER BY t.rating DESC " +
            "LIMIT 5",
            nativeQuery = true)
    List<Book> findTrendingBooks();
}
