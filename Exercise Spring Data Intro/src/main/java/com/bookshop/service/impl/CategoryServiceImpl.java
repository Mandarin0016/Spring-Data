package com.bookshop.service.impl;

import com.bookshop.dao.CategoryRepository;
import com.bookshop.model.Category;
import com.bookshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final String CATEGORY_FILE_PATH = "src/main/resources/files/categories.txt";

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        if (categoryRepository.count() == 0) {
            var rows = Files.readAllLines(Path.of(CATEGORY_FILE_PATH));
            rows.removeIf(String::isEmpty);
            rows.forEach(row -> {
                var category = new Category(row);
                categoryRepository.save(category);
            });
        }
    }

    @Override
    public Set<Category> getRandomSet() {
        Set<Category> categorySet = new HashSet<>();
        int categoryCount = ThreadLocalRandom.current().nextInt(1, 3);
        for (int currentCategory = 0; currentCategory < categoryCount; currentCategory++) {
            categorySet.add(categoryRepository.findById(ThreadLocalRandom.current().nextLong(1, categoryRepository.count() + 1)).orElse(null));
        }
        return categorySet;
    }
}
