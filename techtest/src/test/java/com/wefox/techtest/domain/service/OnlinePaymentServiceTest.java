package com.wefox.techtest.domain.service;

import com.wefox.techtest.domain.exception.NoValidResponseCodeException;
import com.wefox.techtest.domain.model.ErrorType;
import com.wefox.techtest.domain.model.Payment;
import com.wefox.techtest.store.PaymentRepository;
import com.wefox.techtest.store.entity.Account;
import com.wefox.techtest.store.entity.PaymentEntity;
import com.wefox.techtest.store.mapper.PaymentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class OnlinePaymentServiceTest {
    private AccountService accountService;
    private PaymentValidationService paymentValidationService;
    private PaymentRepository paymentRepository;
    private PaymentMapper paymentMapper;
    private LogErrorService logErrorService;
    private OnlinePaymentService onlinePaymentService;

    @BeforeEach
    void setUp() {
        accountService = Mockito.mock(AccountService.class);
        paymentValidationService = Mockito.mock(PaymentValidationService.class);
        paymentRepository = Mockito.mock(PaymentRepository.class);
        paymentMapper = Mockito.mock(PaymentMapper.class);
        logErrorService = Mockito.mock(LogErrorService.class);
        onlinePaymentService = new OnlinePaymentService(accountService, paymentValidationService, paymentRepository, paymentMapper, logErrorService);
    }

    @Test
    @DisplayName("Should process payment successfully")
    void shouldProcessPaymentSuccessfully() throws NoValidResponseCodeException {
        final var payment = Payment.aTestDefault().build();
        final var account = Account.aTestDefault().build();
        final var paymentEntity = PaymentEntity.aTestDefault().build();

        when(accountService.findById(payment.getAccountId())).thenReturn(account);
        when(paymentMapper.toEntity(payment, account)).thenReturn(paymentEntity);

        onlinePaymentService.process(payment);

        verify(paymentValidationService).validate(payment);
        verify(paymentRepository).save(paymentEntity);
    }

    @Test
    @DisplayName("Should handle network error while processing payment")
    void shouldHandleNetworkErrorWhileProcessingPayment() throws NoValidResponseCodeException {
        final var payment = Payment.aTestDefault().build();

        doThrow(WebClientResponseException.class).when(paymentValidationService).validate(payment);

        onlinePaymentService.process(payment);

        verify(logErrorService).log(payment.getPaymentId(), ErrorType.NETWORK, null);
        verifyNoInteractions(accountService, paymentRepository, paymentMapper);
    }

    @Test
    @DisplayName("Should handle database error while processing payment")
    void shouldHandleDatabaseErrorWhileProcessingPayment() {
        final var payment = Payment.aTestDefault().build();
        final var account = Account.aTestDefault().build();
        final var paymentEntity = PaymentEntity.aTestDefault().build();

        when(accountService.findById(payment.getAccountId())).thenReturn(account);
        when(paymentMapper.toEntity(payment, account)).thenReturn(paymentEntity);
        doThrow(NoSuchElementException.class).when(paymentRepository).save(paymentEntity);

        onlinePaymentService.process(payment);

        verify(logErrorService).log(payment.getPaymentId(), ErrorType.DATABASE, null);
    }

    @Test
    @DisplayName("Should handle unexpected error while processing payment")
    void shouldHandleUnexpectedErrorWhileProcessingPayment() throws NoValidResponseCodeException {
        final var payment = Payment.aTestDefault().build();
        final var account = Account.aTestDefault().build();
        final var paymentEntity = PaymentEntity.aTestDefault().build();

        doThrow(RuntimeException.class).when(paymentValidationService).validate(payment);

        onlinePaymentService.process(payment);

        verify(logErrorService).log(payment.getPaymentId(), ErrorType.OTHER, null);
        verifyNoInteractions(accountService, paymentRepository, paymentMapper);
    }
}