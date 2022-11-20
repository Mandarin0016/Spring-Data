package softuni.exam.models.entity;

import softuni.exam.models.enums.DayOfWeek;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "forecasts")
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "day_of_week", nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    @Column(name = "max_temperature", nullable = false)
    private Double maxTemperature;
    @Column(name = "min_temperature", nullable = false)
    private Double minTemperature;
    @Column(nullable = false)
    private Time sunrise;
    @Column(nullable = false)
    private Time sunset;
    @ManyToOne
    private City city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Time getSunrise() {
        return sunrise;
    }

    public void setSunrise(Time sunrise) {
        this.sunrise = sunrise;
    }

    public Time getSunset() {
        return sunset;
    }

    public void setSunset(Time sunset) {
        this.sunset = sunset;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("City: %s: ", getCity().getCityName())).append(System.lineSeparator());
        sb.append("\t-min temperature: ").append(String.format("%.2f", getMinTemperature())).append(System.lineSeparator());
        sb.append("\t--max temperature: ").append(String.format("%.2f", getMaxTemperature())).append(System.lineSeparator());
        sb.append("\t---sunrise: ").append(getSunrise().toString()).append(System.lineSeparator());
        sb.append("\t----sunset: ").append(getSunset().toString()).append(System.lineSeparator());
        return sb.toString().trim();
    }
}
