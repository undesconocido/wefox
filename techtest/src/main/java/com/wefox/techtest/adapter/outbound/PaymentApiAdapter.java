package com.wefox.techtest.adapter.outbound;

import com.wefox.techtest.model.Payment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@AllArgsConstructor
public class PaymentApiAdapter {
    private final WebClient paymentWebClient;

    public void validate(final Payment payment) {
        paymentWebClient.post()
                .bodyValue(payment)
                .retrieve()
                .toBodilessEntity()
                .subscribe(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        System.out.println("Tudo bem, tudo joia");
                    } else {
                        System.out.println("to LOG");
                    }
                });
    }
}
