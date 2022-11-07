package com.example.springintro.service;

import com.example.springintro.model.entity.Book;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllTitlesByAgeRestriction(String ageRestriction);

    List<String> findAllTitlesForGoldenEdition();

    List<String> findAllByPrice();

    List<String> findAllByNotReleasedYear(int year);

    List<String> findAllByReleaseDateBefore(LocalDate date);

    List<String> findAllByNameContains(String text);

    List<String> findAllByAuthorLastNameStartsWithPrefix(String prefix);

    int booksCountWithTitleLengthGreaterThan(int length);

    int findTotalBookCopies(String author);

    List<String> findBookInformationForGivenTitle(String title);
}
