package org.example._04_hospital.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "diagnoses")
public class Diagnose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String comments;
    @ManyToOne
    private GeneralPractitioner doctor;
    @ManyToMany(mappedBy = "diagnoses")
    private Set<Patient> patients;
}
