package com.example.gamestore.repository;

import com.example.gamestore.domain.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query("""
            SELECT CONCAT(g.title, ' ', g.price)
            FROM Game g
            """)
    Collection<String> getAllTitlesAndPrices();

    Optional<Game> findByTitle(String title);
}
