package com.bookshop.dao;

import com.bookshop.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("""
            SELECT CONCAT(a.firstName, ' ', a.lastName,' ', COUNT(b.author.id)) 
            FROM Book b
            JOIN Author a ON a.id = b.author.id 
            GROUP BY b.author.id 
            ORDER BY COUNT(b.author.id) DESC
            """)
    List<String> findAllByBooksSizeDescending();
}
