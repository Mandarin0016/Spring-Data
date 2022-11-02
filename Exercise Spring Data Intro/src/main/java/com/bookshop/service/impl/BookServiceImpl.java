package com.bookshop.service.impl;

import com.bookshop.dao.BookRepository;
import com.bookshop.model.Book;
import com.bookshop.model.enums.AgeRestriction;
import com.bookshop.model.enums.EditionType;
import com.bookshop.service.AuthorService;
import com.bookshop.service.BookService;
import com.bookshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private static final String BOOK_FILE_PATH = "src/main/resources/files/books.txt";

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() == 0) {
            var rows = Files.readAllLines(Path.of(BOOK_FILE_PATH));
            rows.removeIf(String::isEmpty);
            rows.forEach(row -> {
                String[] rowData = row.split("\\s+");
                var book = createBookFromInfo(rowData);
                bookRepository.save(book);
            });
        }
    }

    @Override
    public List<Book> getAllBooksAfterYear(int yearAfter) {
        return bookRepository.findAllByReleaseDateAfter(LocalDate.of(yearAfter, 12, 31));
    }

    @Override
    public List<Book> findAllBooksWithReleasedDateBeforeYear(int yearBefore) {
        return bookRepository.findAllByReleaseDateBefore(LocalDate.of(yearBefore, 1, 1));
    }

    @Override
    public List<String> getAllBooksByGeorgePowell() {
        return bookRepository.findAllByAuthorGeorgePowell();
    }

    private Book createBookFromInfo(String[] data) {
        Book book = new Book();

        book.setEditionType(EditionType.values()[Integer.parseInt(data[0])]);
        book.setReleaseDate(LocalDate.parse(data[1], DateTimeFormatter.ofPattern("d/M/yyyy")));
        book.setCopies(Integer.parseInt(data[2]));
        book.setPrice(new BigDecimal(data[3]));
        book.setAgeRestriction(AgeRestriction.values()[Integer.parseInt(data[4])]);
        book.setTitle(Arrays.stream(data).skip(5).collect(Collectors.joining(" ")));

        var author = authorService.getRandom();
        var categories = categoryService.getRandomSet();
        book.setAuthor(author);
        book.setCategories(categories);

        return book;
    }
}
