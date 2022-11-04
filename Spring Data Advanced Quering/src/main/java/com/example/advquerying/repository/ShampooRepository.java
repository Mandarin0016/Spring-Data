package com.example.advquerying.repository;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    Collection<Shampoo> findAllBySize(Size size);

    Collection<Shampoo> findAllBySizeOrLabel_IdOrderByPriceAsc(Size size, Long label_id);

    Collection<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    Long countByPriceLessThan(BigDecimal price);

    @Query("""
            SELECT s
            FROM Shampoo s
            JOIN s.ingredients i
            WHERE i.name IN :ingredients
            GROUP BY s.brand
            """)
    Collection<Shampoo> findAllContainingIngredients(@Param(value = "ingredients") Collection<String> ingredients);

    @Query("""
            SELECT s
            FROM Shampoo s
            WHERE s.ingredients.size < :givenCount
            """)
    Collection<Shampoo> findAllWithIngredientsCountLessThan(@Param(value = "givenCount") Integer givenCount);
}
