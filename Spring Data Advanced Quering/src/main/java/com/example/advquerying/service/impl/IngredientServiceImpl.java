package com.example.advquerying.service.impl;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repository.IngredientRepository;
import com.example.advquerying.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Collection<Ingredient> getAllByNameStartsWith(String prefix) {
        return ingredientRepository.getAllByNameStartingWith(prefix);
    }

    @Override
    public Collection<Ingredient> getAllByGivenList(Collection<String> data) {
        return ingredientRepository.getAllByNameIn(data);
    }

    @Override
    @Transactional
    public int deleteByName(String name) {
        return ingredientRepository.deleteByName(name);
    }

    @Override
    @Transactional
    public void increasePriceByTenPercentForAll() {
        ingredientRepository.increasePriceByTenPercentForAll();
    }

    @Override
    @Transactional
    public void increasePriceByTenPercentForGiven(Collection<String> names) {
        ingredientRepository.increasePriceByTenPercentForGiven(names);
    }
}
