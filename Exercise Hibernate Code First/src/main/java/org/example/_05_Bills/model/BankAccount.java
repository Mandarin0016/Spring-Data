package org.example._05_Bills.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "bank_accounts")
public class BankAccount extends BillingDetail {
    @Enumerated(EnumType.STRING)
    @Column(name = "billing_type")
    private final BillingType billingType = BillingType.BANK_ACCOUNT;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "swift_code")
    private String SWIFT;
}
