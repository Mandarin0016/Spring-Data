package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    Scanner scanner = new Scanner(System.in);
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();
        pritnALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

        findByAgeRestriction();
        findGoldenBooks();
        findBookByPrice();
        findBookByReleasedYearNot();
        findBooksBeforeReleaseDate();
        findAuthorNamesThatEndsWithGivenSuffix();
        findBooksTitlesWithContainsGivenString();
        findBookTitlesByAuthorLastNameStartsWith();
        findBookCountForTitleLengthGreaterThan();
        findTotalCopiesPerAuthor();
        findBookInfo();
    }

    private void findBookInfo() {
        this.bookService.findBookInformationForGivenTitle(scanner.nextLine()).forEach(System.out::println);
    }

    private void findTotalCopiesPerAuthor() {
        String authorName = scanner.nextLine();
        System.out.println(this.bookService.findTotalBookCopies(authorName));
    }

    private void findBookCountForTitleLengthGreaterThan() {
        int length = Integer.parseInt(scanner.nextLine());
        System.out.println(this.bookService.booksCountWithTitleLengthGreaterThan(length));
    }

    private void findBookTitlesByAuthorLastNameStartsWith() {
        String prefix = scanner.nextLine();
        this.bookService.findAllByAuthorLastNameStartsWithPrefix(prefix).forEach(System.out::println);
    }

    private void findBooksTitlesWithContainsGivenString() {
        String text = scanner.nextLine();
        this.bookService.findAllByNameContains(text).forEach(System.out::println);
    }

    private void findAuthorNamesThatEndsWithGivenSuffix() {
        String suffix = scanner.nextLine();
        this.authorService.findNamesThatEndsWithSuffix(suffix).forEach(System.out::println);
    }

    private void findBooksBeforeReleaseDate() {
        LocalDate before = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.bookService.findAllByReleaseDateBefore(before).forEach(System.out::println);
    }

    private void findBookByReleasedYearNot() {
        int year = Integer.parseInt(scanner.nextLine());
        this.bookService.findAllByNotReleasedYear(year).forEach(System.out::println);
    }

    private void findBookByPrice() {
        this.bookService.findAllByPrice().forEach(System.out::println);
    }

    private void findGoldenBooks() {
        this.bookService.findAllTitlesForGoldenEdition().forEach(System.out::println);
    }

    private void findByAgeRestriction() {
        String ageRestriction = scanner.nextLine();
        this.bookService.findAllTitlesByAgeRestriction(ageRestriction).forEach(System.out::println);
    }

    private void pritnALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
