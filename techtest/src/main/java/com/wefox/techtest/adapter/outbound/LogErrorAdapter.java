package com.wefox.techtest.adapter.outbound;

import com.wefox.techtest.model.Error;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class LogErrorAdapter {
    private final WebClient webClient;

    public LogErrorAdapter(@Qualifier("logWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public void execute(final Error error) {
        webClient.post()
                .bodyValue(error)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .retrieve()
                .toBodilessEntity()
                .subscribe();
    }
}
