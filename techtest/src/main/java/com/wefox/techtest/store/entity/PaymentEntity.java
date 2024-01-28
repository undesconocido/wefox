package com.wefox.techtest.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public final class PaymentEntity {
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
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    void prePersist() {
        account.setLastPaymentDate(createdAt);
    }

    public static PaymentEntityBuilder aTestDefault() {
        return PaymentEntity.builder()
                .paymentId("1")
                .account(Account.aTestDefault().build())
                .paymentType(PaymentType.ONLINE)
                .creditCard("1234567890123456")
                .amount(BigDecimal.valueOf(100.0))
                .createdAt(LocalDateTime.now());
    }
}
