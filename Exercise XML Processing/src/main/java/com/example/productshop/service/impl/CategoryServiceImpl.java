package com.example.productshop.service.impl;

import com.example.productshop.model.dto.CategorySeedDTO;
import com.example.productshop.model.entity.Category;
import com.example.productshop.repository.CategoryRepository;
import com.example.productshop.service.CategoryService;
import com.example.productshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedDtoCollection(List<CategorySeedDTO> categories) {
        if (categoryRepository.count() == 0) {
            categories
                    .stream()
                    .filter(validationUtil::isValid)
                    .map(c -> modelMapper.map(c, Category.class))
                    .forEach(categoryRepository::save);
        }
    }

    @Override
    public Set<Category> getRandomSet() {
        Set<Category> categories = new HashSet<>();
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(1, 3); i++) {
            long randomId = ThreadLocalRandom
                    .current()
                    .nextLong(1, categoryRepository.count() + 1);
            categories.add(categoryRepository.findById(randomId).orElse(null));
        }
        return categories;
    }
}
