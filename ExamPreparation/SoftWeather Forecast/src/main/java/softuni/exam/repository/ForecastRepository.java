package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Forecast;
import softuni.exam.models.enums.DayOfWeek;

import java.util.Collection;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {
    boolean existsForecastByDayOfWeekIsLikeAndCity_Id(DayOfWeek dayOfWeek, Long city_id);

    @Query("""
            SELECT f
            FROM Forecast f
            WHERE f.dayOfWeek = softuni.exam.models.enums.DayOfWeek.SUNDAY AND f.city.population < :populationCount
            ORDER BY f.maxTemperature DESC, f.id ASC
            """)
    Collection<Forecast> findSundayForecastForCitiesWithPopulationLessThan(@Param("populationCount") Integer populationCount);
}
