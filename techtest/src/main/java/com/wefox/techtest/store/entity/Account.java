package com.wefox.techtest.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @Column(name = "account_id")
    Integer id;
    @Column(name = "email")
    String email;
    @Column(name = "birthdate")
    LocalDate birthDate;
    @Column(name = "last_payment_date")
    LocalDateTime lastPaymentDate;
    @Column(name = "created_on")
    LocalDateTime createdAt;

    public static AccountBuilder aTestDefault() {
        return Account.builder()
                .id(1)
                .email("test@test.com")
                .birthDate(LocalDate.of(1980, 1, 1))
                .lastPaymentDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now().minusDays(1));
    }
}
