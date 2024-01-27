package com.wefox.techtest.adapter.outbound;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@AllArgsConstructor
public class LogErrorAdapter {
    private final WebClient logWebClient;

    public void logError(final Error error) {
        logWebClient.post()
                .bodyValue(error)
                .retrieve()
                .toBodilessEntity()
                .subscribe(response -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        System.out.println("Tudo bem, tudo joia");
                    } else {
                        System.out.println("PANIC");
                    }
                });
    }
}
