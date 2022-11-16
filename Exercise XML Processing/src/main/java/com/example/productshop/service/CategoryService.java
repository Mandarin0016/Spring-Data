package com.example.productshop.service;

import com.example.productshop.model.dto.CategoryRootDto;
import com.example.productshop.model.dto.CategorySeedDTO;
import com.example.productshop.model.entity.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    void seedDtoCollection(List<CategorySeedDTO> categories);

    Set<Category> getRandomSet();
}
