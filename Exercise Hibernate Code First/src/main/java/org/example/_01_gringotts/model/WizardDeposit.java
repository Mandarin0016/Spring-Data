package org.example._01_gringotts.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "wizard_deposits")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class WizardDeposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "first_name", length = 50)
    private String firstName;
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;
    @Column(columnDefinition = "TEXT")
    private String notes;
    private int age;
    @Column(name = "magic_wand_creator", length = 100)
    private String magicWantCreator;
    private short magicWantSize;
    @Column(name = "deposit_group", length = 20)
    private String depositGroup;
    @Column(name = "deposit_start_date")
    private LocalDateTime depositStartDate;
    @Column(name = "deposit_amount")
    private Double depositAmount;
    @Column(name = "deposit_interest")
    private Double depositInterest;
    @Column(name = "deposit_charge")
    private Double depositCharge;
    @Column(name = "deposit_expiration_date")
    private LocalDateTime depositExpirationDate;
    @Column(name = "is_deposit_expired")
    private boolean isDepositExpired;
}
