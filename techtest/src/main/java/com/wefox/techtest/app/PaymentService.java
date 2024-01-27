package com.wefox.techtest.app;

import com.wefox.techtest.adapter.outbound.PaymentApiAdapter;
import com.wefox.techtest.model.Payment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentApiAdapter adapter;

    public void validatePayment(final Payment payment) {
        adapter.validate(payment);
        /// final var response - adapter.validate(payment);
        /// if (response.getStatusCode().is2xxSuccessful()) {
        ///     System.out.println("Tudo bem, tudo joia");
        /// } else {
        ///     System.out.println("to LOG");
    }

}
