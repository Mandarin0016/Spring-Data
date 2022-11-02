package com.bookshop.init;

import com.bookshop.model.Book;
import com.bookshop.service.AuthorService;
import com.bookshop.service.BookService;
import com.bookshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public ConsoleRunner(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        getAllBooksWithReleasedDateAfter(2000);
        getAllAuthorNamesWithBooksReleasedDateBeforeYear(1990);
        getAllAuthorNamesWithBookCount();
        getAllBooksByGeorgePowell();
    }

    private void getAllBooksByGeorgePowell() {
        this.bookService.getAllBooksByGeorgePowell().forEach(System.out::println);
    }

    private void getAllAuthorNamesWithBookCount() {
        var authors = this.authorService.getAllAuthorsOrderedByBookCount();
        authors.forEach(System.out::println);
    }

    public void getAllAuthorNamesWithBooksReleasedDateBeforeYear(int yearBefore) {
        var books = this.bookService.findAllBooksWithReleasedDateBeforeYear(yearBefore);
        var authorNames = books.stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(), book.getAuthor().getLastName()))
                .distinct().toList();
        authorNames.forEach(System.out::println);
    }

    public void getAllBooksWithReleasedDateAfter(int yearAfter) {
        this.bookService.getAllBooksAfterYear(yearAfter).stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        this.categoryService.seedCategories();
        this.authorService.seedAuthors();
        this.bookService.seedBooks();
    }
}
