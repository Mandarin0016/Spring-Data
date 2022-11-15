package com.example.productshop.service;

import com.example.productshop.model.dto.CategorySeedDTO;

import java.util.List;

public interface CategoryService {
    void seedCollection(List<CategorySeedDTO> categories);
}
