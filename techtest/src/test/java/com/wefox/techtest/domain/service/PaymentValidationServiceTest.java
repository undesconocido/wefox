package com.wefox.techtest.domain.service;

import com.wefox.techtest.adapter.outbound.PaymentApiAdapter;
import com.wefox.techtest.domain.exception.NoValidResponseCodeException;
import com.wefox.techtest.domain.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PaymentValidationServiceTest {
    private PaymentApiAdapter paymentApiAdapter;
    private PaymentValidationService paymentValidationService;
    private Payment payment;

    @BeforeEach
    void setUp() {
        paymentApiAdapter = Mockito.mock(PaymentApiAdapter.class);
        paymentValidationService = new PaymentValidationService(paymentApiAdapter);
        payment = Payment.aTestDefault().build();
    }

    @Test
    @DisplayName("Should validate payment successfully")
    void shouldValidatePaymentSuccessfully() throws NoValidResponseCodeException {
        when(paymentApiAdapter.execute(payment)).thenReturn(HttpStatus.ACCEPTED);

        paymentValidationService.validate(payment);

        verify(paymentApiAdapter).execute(payment);
    }

    @Test
    @DisplayName("Should handle invalid payment")
    void shouldHandleInvalidPayment() throws NoValidResponseCodeException {
        when(paymentApiAdapter.execute(payment)).thenReturn(HttpStatus.GATEWAY_TIMEOUT);

        assertThrows(
                NoValidResponseCodeException.class,
                () -> {
                    paymentValidationService.validate(payment);
                }
        );

        verify(paymentApiAdapter).execute(payment);
    }
}