package com.bookshop.model;

import com.bookshop.model.enums.AgeRestriction;
import com.bookshop.model.enums.EditionType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Length(min = 1, max = 50)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(name = "edition_type", nullable = false)
    @Enumerated
    private EditionType editionType;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer copies;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    @Column(name = "age_restriction", nullable = false)
    @Enumerated
    private AgeRestriction ageRestriction;
    @JoinColumn(nullable = false)
    @ManyToOne(targetEntity = Author.class)
    private Author author;
    @ManyToMany
    private Set<Category> categories = new HashSet<>();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
