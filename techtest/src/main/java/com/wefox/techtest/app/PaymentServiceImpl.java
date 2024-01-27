package com.wefox.techtest.app;

import com.wefox.techtest.adapter.outbound.LogErrorAdapter;
import com.wefox.techtest.adapter.outbound.PaymentApiAdapter;
import com.wefox.techtest.mapper.AccountMapper;
import com.wefox.techtest.mapper.PaymentMapper;
import com.wefox.techtest.model.ErrorType;
import com.wefox.techtest.model.Error;
import com.wefox.techtest.model.Payment;
import com.wefox.techtest.store.AccountRepository;
import com.wefox.techtest.store.PaymentRepository;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentApiAdapter paymentApiAdapter;
    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final LogErrorAdapter logErrorAdapter;
    private final PaymentMapper paymentMapper;
    private final AccountMapper accountMapper;

    public void processOffline(final Payment payment) {
        try {
            final var account = accountRepository.findById(payment.getAccountId()).orElseThrow();
            paymentRepository.save(paymentMapper.toModel(payment, account));
        } catch (NoSuchElementException | PersistenceException e) {
            log.error("Error processing offline payment {}", payment);
            logErrorAdapter.execute(Error.builder()
                    .paymentId(payment.getAccountId())
                    .errorType(ErrorType.DATABASE)
                    .description(e.getMessage())
                    .build());
        } catch (Exception e) {
            log.error("Error processing offline payment {}", payment);
            logErrorAdapter.execute(Error.builder()
                    .paymentId(payment.getAccountId())
                    .errorType(ErrorType.OTHER)
                    .description(e.getMessage())
                    .build());
        }
    }

    public void processOnline(final Payment payment) {
        try {
            validatePayment(payment);
            final var account = accountRepository.findById(payment.getAccountId()).orElseThrow();
            paymentRepository.save(paymentMapper.toModel(payment, account));
        } catch (NoSuchElementException | PersistenceException e) {
            log.error("Error processing online payment {}", payment);
            logErrorAdapter.execute(Error.builder()
                    .paymentId(payment.getAccountId())
                    .errorType(ErrorType.DATABASE)
                    .description(e.getMessage())
                    .build());
        } catch (Exception e) {
            log.error("Error processing online payment {}", payment);
            logErrorAdapter.execute(Error.builder()
                    .paymentId(payment.getAccountId())
                    .errorType(ErrorType.OTHER)
                    .description(e.getMessage())
                    .build());
        }
    }

    private void validatePayment(final Payment payment) {
        final var httpStatusCode = paymentApiAdapter.execute(payment);
        if (httpStatusCode.isError()) {
            logErrorAdapter.execute(Error.builder()
                    .paymentId(payment.getAccountId())
                    .errorType(ErrorType.NETWORK)
                    .description(String.valueOf(httpStatusCode.value()))
                    .build());
        }
    }
}
