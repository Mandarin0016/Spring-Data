package com.example.advquerying.repository;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Collection<Ingredient> getAllByNameStartingWith(String namePrefix);

    Collection<Ingredient> getAllByNameIn(Collection<String> name);

    int deleteByName(String name);

    @Modifying
    @Query("UPDATE Ingredient i SET i.price = i.price * 1.10")
    void increasePriceByTenPercentForAll();

    @Modifying
    @Query("UPDATE Ingredient i SET i.price = i.price * 1.10 WHERE i.name IN :listOfIngredients")
    void increasePriceByTenPercentForGiven(@Param(value = "listOfIngredients") Collection<String> listOfIngredients);
}
