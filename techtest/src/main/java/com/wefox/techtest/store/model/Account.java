package com.wefox.techtest.store.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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

    @PrePersist
    @PreUpdate
    void pre() {
        lastPaymentDate = LocalDateTime.now();
    }
}
