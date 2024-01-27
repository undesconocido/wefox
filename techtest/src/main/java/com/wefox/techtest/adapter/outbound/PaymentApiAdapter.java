package com.wefox.techtest.adapter.outbound;

import com.wefox.techtest.model.Payment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PaymentApiAdapter {
    private final WebClient webClient;

    public PaymentApiAdapter(@Qualifier("paymentWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public HttpStatusCode execute(final Payment payment) {
        return webClient.post()
                .bodyValue(payment)
                .retrieve()
                .toBodilessEntity()
                .blockOptional()
                .map(ResponseEntity::getStatusCode)
                .orElseThrow();
    }
}
