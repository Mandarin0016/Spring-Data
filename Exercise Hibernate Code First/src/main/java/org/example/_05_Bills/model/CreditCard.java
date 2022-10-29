package org.example._05_Bills.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.time.Month;
import java.time.Year;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "credit_cards")
public class CreditCard extends BillingDetail {

    @Enumerated(EnumType.STRING)
    @Column(name = "billing_type")
    private final BillingType billingType = BillingType.CREDIT_CARD;
    @Column(name = "card_type")
    private String cardType;
    @Column(name = "expiration_month")
    private Month expirationMonth;
    @Column(name = "expiration_year")
    @Future
    private Year expirationYear;

}