package com.productsapp.productsinform.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

@Entity
@Table(name = "money_entity")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MoneyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private Currency currency;


    public MoneyEntity(BigDecimal amount) {
        this.setAmount(amount);
        currency=Currency.getInstance("JOD");
    }



    public void setAmount(BigDecimal amount) {
        if(amount.doubleValue()<0)
            throw new IllegalArgumentException("Amount cant be nagative.");
        this.amount = amount;
    }



    @Override
    public String toString() {
        return "[Value: " + amount.toPlainString()
                + " | Currency: " + currency.getCurrencyCode() + "]";
    }
}
