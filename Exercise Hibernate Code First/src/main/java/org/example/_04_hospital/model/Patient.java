package org.example._04_hospital.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String address;
    @Email(message = "Invalid email format!")
    private String email;
    @PastOrPresent(message = "Invalid date of birth!")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @URL(message = "Invalid picture URL format!")
    @Column(name = "picture_url")
    private String pictureUrl;
    @Column(name = "is_med_ins_included")
    private boolean isMedicalInsuranceIncluded;
    @ManyToOne(optional = false)
    private GeneralPractitioner doctor;
    @ManyToMany
    @JoinTable(
            name = "patients_diagnoses",
            joinColumns = {@JoinColumn(name = "patient_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "diagnose_id", referencedColumnName = "id")}
    )
    private Set<Diagnose> diagnoses;
    @OneToMany(mappedBy = "prescribedFor")
    private Set<Medicament> medicaments;
}
