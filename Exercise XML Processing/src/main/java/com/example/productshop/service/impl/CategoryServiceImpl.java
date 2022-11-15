package com.example.productshop.service.impl;

import com.example.productshop.model.dto.CategorySeedDTO;
import com.example.productshop.model.entity.Category;
import com.example.productshop.repository.CategoryRepository;
import com.example.productshop.service.CategoryService;
import com.example.productshop.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void seedCollection(List<CategorySeedDTO> categories) {
        categories
                .stream()
                .filter(validationUtil::isValid)
                .map(c -> modelMapper.map(c, Category.class))
                .forEach(categoryRepository::save);
    }
}
