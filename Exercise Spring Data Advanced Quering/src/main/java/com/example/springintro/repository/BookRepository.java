package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findByAgeRestriction(AgeRestriction restriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowerThan, BigDecimal greaterThan);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate before, LocalDate after);

    List<Book> findAllByTitleContainingIgnoreCase(String text);

    List<Book> findByAuthorLastNameStartingWith(String prefix);

    @Query("""
            SELECT b
            FROM Book b
            WHERE LENGTH(b.title) > :length
            """)
    List<Book> findBooksForTitleGreaterThan(@Param(value = "length") int length);

    @Query("""
            SELECT b.copies
            FROM Book b
            WHERE b.author.firstName LIKE :aFirstName AND b.author.lastName LIKE :aLastName
            """)
    List<Integer> findTotalCopiesPerAuthor(@Param(value = "aFirstName") String firstName, @Param(value = "aLastName") String lastName);

    @Query("""
            SELECT b.title, b.editionType, b.ageRestriction, b.price
            FROM Book b
            WHERE b.title LIKE :givenTitle
            """)
    List<Object[]> findBookInformationByTitle(@Param("givenTitle") String title);

}
