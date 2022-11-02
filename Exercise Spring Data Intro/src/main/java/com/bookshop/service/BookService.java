package com.bookshop.service;

import com.bookshop.model.Book;

import java.io.IOException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> getAllBooksAfterYear(int yearAfter);

    List<Book> findAllBooksWithReleasedDateBeforeYear(int yearBefore);

    List<String> getAllBooksByGeorgePowell();
}
