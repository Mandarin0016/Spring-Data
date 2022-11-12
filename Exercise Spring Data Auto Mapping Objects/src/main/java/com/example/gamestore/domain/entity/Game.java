package com.example.gamestore.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "games")
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String title;
    private String trailer;
    private String thumbnailUrl;
    private float size;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(title).append(System.lineSeparator());
        sb.append("Price: ").append(price).append(System.lineSeparator());
        sb.append("Description: ").append(description).append(System.lineSeparator());
        sb.append("ReleaseDate: ").append(releaseDate);
        return sb.toString();
    }
}
