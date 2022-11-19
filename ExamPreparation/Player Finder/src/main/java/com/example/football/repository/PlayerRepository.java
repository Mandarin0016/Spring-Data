package com.example.football.repository;

import com.example.football.models.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsByEmail(String email);

    @Query("""
            SELECT p
            FROM Player p
            WHERE p.birthDate > :afterDate AND p.birthDate < :beforeDate
            ORDER BY p.stat.shooting DESC, p.stat.passing DESC, p.stat.endurance DESC, p.lastName
            """)
    Collection<Player> findTheBestPlayers(@Param("afterDate") LocalDate afterDate, @Param("beforeDate") LocalDate beforeDate);
}
