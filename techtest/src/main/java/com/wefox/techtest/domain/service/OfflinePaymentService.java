package com.wefox.techtest.domain.service;

import com.wefox.techtest.domain.model.ErrorType;
import com.wefox.techtest.domain.model.Payment;
import com.wefox.techtest.store.PaymentRepository;
import com.wefox.techtest.store.mapper.PaymentMapper;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Slf4j
@AllArgsConstructor
public class OfflinePaymentService implements PaymentService {
    private final AccountService accountService;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final LogErrorService logErrorService;

    @Override
    @Transactional
    public void process(final Payment payment) {
        log.debug("Processing offline payment {} ", payment);
        try {
            final var account = accountService.findById(payment.getAccountId());
            final var newPayment = paymentMapper.toEntity(payment, account);
            paymentRepository.save(newPayment);
        } catch (final NoSuchElementException | PersistenceException e) {
            log.error("Database error processing online payment {}, {}", payment, e.getMessage());
            logErrorService.log(payment.getPaymentId(), ErrorType.DATABASE, e.getMessage());
            if (e instanceof PersistenceException) {
                throw e;
            }
        } catch (final Exception e) {
            log.error("Unexpected error processing online payment {}, {}", payment, e.getMessage());
            logErrorService.log(payment.getPaymentId(), ErrorType.OTHER, e.getMessage());
        }
    }
}
