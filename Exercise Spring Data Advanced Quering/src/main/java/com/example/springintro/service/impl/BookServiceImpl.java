package com.example.springintro.service.impl;

import com.example.springintro.model.entity.*;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public List<String> findAllTitlesByAgeRestriction(String ageRestriction) {
        AgeRestriction restriction = AgeRestriction.valueOf(ageRestriction.toUpperCase());
        return bookRepository.findByAgeRestriction(restriction).stream().map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public List<String> findAllTitlesForGoldenEdition() {
        return bookRepository.findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000).stream().map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByPrice() {
        return bookRepository.findAllByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(5), BigDecimal.valueOf(40)).stream().map(Book::toString).collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByNotReleasedYear(int year) {
        LocalDate before = LocalDate.of(year, 1, 1);
        LocalDate after = LocalDate.of(year, 12, 31);
        return this.bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(before, after).stream().map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByReleaseDateBefore(LocalDate date) {
        return bookRepository.findAllByReleaseDateBefore(date).stream().map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByNameContains(String text) {
        return bookRepository.findAllByTitleContainingIgnoreCase(text).stream().map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByAuthorLastNameStartsWithPrefix(String prefix) {
        return bookRepository.findByAuthorLastNameStartingWith(prefix).stream().map(book -> String.format("%s (%s %s)", book.getTitle(), book.getAuthor().getFirstName(), book.getAuthor().getLastName())).collect(Collectors.toList());
    }

    @Override
    public int booksCountWithTitleLengthGreaterThan(int length) {
        return bookRepository.findBooksForTitleGreaterThan(length).size();
    }

    @Override
    public int findTotalBookCopies(String author) {
        return bookRepository.findTotalCopiesPerAuthor(author.split("\\s+")[0], author.split("\\s+")[1]).stream().mapToInt(Integer::intValue).sum();
    }

    @Override
    public List<String> findBookInformationForGivenTitle(String title) {
        return bookRepository.findBookInformationByTitle(title).stream()
                .map(o -> String.format("%s %s %s %s", o[0], o[1], o[2], o[3]))
                .collect(Collectors.toList());
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
        return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }
}
