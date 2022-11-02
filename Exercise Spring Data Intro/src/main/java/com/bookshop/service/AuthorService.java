package com.bookshop.service;

import com.bookshop.model.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandom();

    List<String> getAllAuthorsOrderedByBookCount();
}
