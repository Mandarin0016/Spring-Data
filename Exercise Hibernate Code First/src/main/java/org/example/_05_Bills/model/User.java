package org.example._05_Bills.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Email
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "owner", targetEntity = BankAccount.class)
    private Set<BankAccount> bankAccounts;
    @OneToMany(mappedBy = "owner", targetEntity = CreditCard.class)
    private Set<CreditCard> creditCards;


}
