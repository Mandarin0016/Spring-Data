package org.example._03_university.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "teachers")
public class Teacher extends Person {
    private String email;
    @Column(name = "salary_per_hour")
    private BigDecimal salaryPerHour;
    @OneToMany(mappedBy = "teacher", targetEntity = Course.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Course> courses;
}
