package com.wefox.techtest.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wefox.techtest.store.entity.PaymentType;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payment {
    @JsonProperty("payment_id")
    String paymentId;
    @JsonProperty("account_id")
    String accountId;
    @JsonProperty("payment_type")
    PaymentType paymentType;
    @JsonProperty("credit_card")
    String creditCard;
    BigDecimal amount;
    @JsonIgnore
    LocalDateTime createdAt;

    public static PaymentBuilder aTestDefault() {
        return Payment.builder()
                .paymentId("1")
                .accountId("1")
                .paymentType(PaymentType.ONLINE)
                .creditCard("1234567890123456")
                .amount(BigDecimal.valueOf(100.0))
                .createdAt(LocalDateTime.now());
    }
}
