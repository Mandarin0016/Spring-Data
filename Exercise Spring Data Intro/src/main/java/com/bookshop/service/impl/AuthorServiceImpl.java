package com.bookshop.service.impl;

import com.bookshop.dao.AuthorRepository;
import com.bookshop.model.Author;
import com.bookshop.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final String AUTHOR_FILE_PATH = "src/main/resources/files/authors.txt";

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public void seedAuthors() throws IOException {
        if (authorRepository.count() == 0) {
            var rows = Files.readAllLines(Path.of(AUTHOR_FILE_PATH));
            rows.removeIf(String::isEmpty);
            rows.forEach(row -> {
                var author = new Author();
                author.setFirstName(row.split("\\s+")[0]);
                author.setLastName(row.split("\\s+")[1]);
                authorRepository.save(author);
            });
        }
    }

    @Override
    public Author getRandom() {
        return authorRepository.findById(ThreadLocalRandom.current().nextLong(1, authorRepository.count() + 1)).orElse(null);
    }

    @Override
    public List<String> getAllAuthorsOrderedByBookCount() {
        return authorRepository.findAllByBooksSizeDescending();
    }
}
