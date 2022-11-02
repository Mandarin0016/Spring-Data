package com.bookshop.service;

import com.bookshop.model.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> getRandomSet();
}
