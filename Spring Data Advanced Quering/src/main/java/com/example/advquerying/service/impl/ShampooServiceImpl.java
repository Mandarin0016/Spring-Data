package com.example.advquerying.service.impl;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repository.ShampooRepository;
import com.example.advquerying.service.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Service
public class ShampooServiceImpl implements ShampooService {
    private final ShampooRepository shampooRepository;

    @Autowired
    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public Collection<Shampoo> getAllShampoosBySize(Size size) {
        return shampooRepository.findAllBySize(size);
    }

    @Override
    public Collection<Shampoo> getAllShampoosBySizeOrLabelIdOrderByDesc(Size size, Long id) {
        return shampooRepository.findAllBySizeOrLabel_IdOrderByPriceAsc(size, id);
    }

    @Override
    public Collection<Shampoo> getAllShampoosGreaterThanGiverPriceDescOrdered(BigDecimal price) {
        return shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public Long getShampooCountWithLowerPrice(BigDecimal price) {
        return shampooRepository.countByPriceLessThan(price);
    }

    @Override
    public Collection<Shampoo> getAllShampoosThatContainsIngredientNames(List<String> ingredientNames) {
        return shampooRepository.findAllContainingIngredients(ingredientNames);
    }

    @Override
    public Collection<Shampoo> getAllShampoosWithIngredientsCountLess(Integer count) {
        return shampooRepository.findAllWithIngredientsCountLessThan(count);
    }
}
