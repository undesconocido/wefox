package com.wefox.techtest.domain.service;

import com.wefox.techtest.adapter.outbound.PaymentApiAdapter;
import com.wefox.techtest.domain.exception.NoValidResponseCodeException;
import com.wefox.techtest.domain.model.Payment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class PaymentValidationService {
    private final PaymentApiAdapter paymentApiAdapter;

    public void validate(final Payment payment) throws NoValidResponseCodeException {
        log.debug("Validating payment {} ", payment);
        final var httpStatusCode = paymentApiAdapter.execute(payment);
        if (!httpStatusCode.is2xxSuccessful()) {
            throw new NoValidResponseCodeException(httpStatusCode.value());
        }
    }
}
