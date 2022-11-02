package com.bookshop.dao;

import com.bookshop.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateAfter);

    @Query("""
            SELECT CONCAT(b.title, ' ', b.releaseDate, ' ', b.copies) 
            FROM Book b
            WHERE b.author.firstName = 'George' AND b.author.lastName = 'Powell'
            ORDER BY b.releaseDate DESC, b.title ASC
            """)
    List<String> findAllByAuthorGeorgePowell();
}
