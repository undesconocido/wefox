package com.wefox.techtest.store.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "payments")
public final class Payment {
    @Id
    @Column(name = "payment_id")
    private String paymentId;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private String creditCard;

    private BigDecimal amount;

    @Column(name = "created_on")
    private LocalDateTime createdAt;

    @PrePersist
    void pre() {
        createdAt = LocalDateTime.now();
    }
}
