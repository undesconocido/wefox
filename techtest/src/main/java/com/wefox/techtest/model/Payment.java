package com.wefox.techtest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
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
}
