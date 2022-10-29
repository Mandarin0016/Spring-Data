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
@Table(name = "doctors")
public class GeneralPractitioner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    @OneToMany(mappedBy = "doctor", targetEntity = Patient.class)
    private Set<Patient> patients;
    @OneToMany(mappedBy = "doctor", targetEntity = Diagnose.class)
    private Set<Diagnose> diagnoses;
    @OneToMany(mappedBy = "prescribedBy", targetEntity = Medicament.class)
    private Set<Medicament> medicaments;
    @OneToMany(mappedBy = "doctor", targetEntity = Visitation.class)
    private Set<Visitation> visitations;
}
