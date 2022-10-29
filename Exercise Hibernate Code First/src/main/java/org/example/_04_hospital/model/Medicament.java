package org.example._04_hospital.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "medicaments")
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "prescribed_by")
    private GeneralPractitioner prescribedBy;
    @ManyToOne
    @JoinColumn(name = "prescribed_for")
    private Patient prescribedFor;
}
