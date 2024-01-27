package com.wefox.techtest.model;

import java.time.LocalDateTime;

public record Payment(
        String paymentId,
        String account_id,
        PaymentType paymentType,
        String creditCard,
        Integer amount,
        LocalDateTime createdAt
) {
}
