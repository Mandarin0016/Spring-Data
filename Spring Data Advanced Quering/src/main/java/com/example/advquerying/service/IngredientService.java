package com.example.advquerying.service;

import com.example.advquerying.entities.Ingredient;

import java.util.Collection;

public interface IngredientService {
    Collection<Ingredient> getAllByNameStartsWith(String prefix);

    Collection<Ingredient> getAllByGivenList(Collection<String> data);

    int deleteByName(String name);

    void increasePriceByTenPercentForAll();

    void increasePriceByTenPercentForGiven(Collection<String> names);
}
