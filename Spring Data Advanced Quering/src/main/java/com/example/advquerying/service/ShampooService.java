package com.example.advquerying.service;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface ShampooService {
    Collection<Shampoo> getAllShampoosBySize(Size size);

    Collection<Shampoo> getAllShampoosBySizeOrLabelIdOrderByDesc(Size size, Long id);

    Collection<Shampoo> getAllShampoosGreaterThanGiverPriceDescOrdered(BigDecimal price);

    Long getShampooCountWithLowerPrice(BigDecimal price);

    Collection<Shampoo> getAllShampoosThatContainsIngredientNames(List<String> ingredientNames);

    Collection<Shampoo> getAllShampoosWithIngredientsCountLess(Integer count);
}
