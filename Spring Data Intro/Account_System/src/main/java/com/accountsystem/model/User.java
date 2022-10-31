package com.accountsystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    private Long id;
    @Column(unique = true)
    private String username;
    private int age;
    @OneToMany(mappedBy = "user", targetEntity = Account.class)
    private List<Account> accounts = new ArrayList<>();
}
